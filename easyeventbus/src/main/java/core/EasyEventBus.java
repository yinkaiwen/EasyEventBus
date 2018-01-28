package core;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import wrap.EasyEventBusModel;
import eventhandler.AsyncHandler;
import eventhandler.PostHandler;
import eventhandler.TaskHandler;
import eventhandler.UIHandler;
import seekmethods.SeekMethodsIncludeInterface;
import seekmethods.SeekMethodsModel;
import wrap.EventTagAndParameter;
import wrap.Subscriber;
import wrap.Subscription;

/**
 * Created by kevin on 2018/1/25.
 * https://github.com/yinkaiwen
 */

public class EasyEventBus {

    private static final String tag = EasyEventBus.class.getSimpleName();

    private static EasyEventBus instance = null;
    private static Map<EventTagAndParameter, CopyOnWriteArrayList<Subscription>> mCache;
    private static SubsciberMethodFinder sMethodFinder;
    private static List<EventTagAndParameter> stickyCache = Collections.synchronizedList(new ArrayList<EventTagAndParameter>());
    private static ThreadLocal<Queue<EventTagAndParameter>> sLocal = new ThreadLocal<Queue<EventTagAndParameter>>() {
        @Override
        protected Queue<EventTagAndParameter> initialValue() {
            return new ConcurrentLinkedQueue<>();
        }
    };

    private Dispatcher mDispatcher = new Dispatcher();

    private EasyEventBus() {
        mCache = new HashMap<>();
        sMethodFinder = new SubsciberMethodFinder(mCache);
    }

    public static EasyEventBus getDefault() {
        if (instance == null) {
            synchronized (EasyEventBus.class) {
                if (instance == null) {
                    instance = new EasyEventBus();
                }
            }
        }
        return instance;
    }

    public void register(Object subscriber) {
        if (subscriber == null)
            return;

        synchronized (this) {
            sMethodFinder.subscribe(subscriber);
        }
    }

    public void unregister(Object subscriber) {
        if (subscriber == null)
            return;
        synchronized (this) {
            sMethodFinder.unregister(subscriber);
        }
    }

    public void registerSticky(Object subscriber) {
        register(subscriber);

        mDispatcher.dispatchSticky(subscriber);
    }

    public void unregisterSticky(Object subscriber) {
        unregister(subscriber);

        if (stickyCache.contains(subscriber)) {
            stickyCache.remove(subscriber);
        }
    }

    public <T> void post(T arg) {
        post(arg, Subscriber.DEFAULT_TAG);
    }

    public <T> void post(T arg, String tag) {
        if (arg != null && !TextUtils.isEmpty(tag)) {
            sLocal.get().offer(new EventTagAndParameter(tag, arg.getClass()));
        }
        mDispatcher.dispatch(arg);
    }

    public <T> void postSticky(T arg) {
        postSticky(arg, Subscriber.DEFAULT_TAG);
    }

    public <T> void postSticky(T arg, String tag) {
        if (arg == null)
            return;

        EventTagAndParameter event = new EventTagAndParameter(tag, arg.getClass());
        event.arg = arg;
        stickyCache.add(event);
    }

    public <T> void removeSticky(T arg, String tag) {
        EventTagAndParameter event = new EventTagAndParameter(tag, arg.getClass());
        stickyCache.remove(event);
    }

    public void removeAllSticky() {
        stickyCache.clear();
    }


    public void setSeekMethodModel(SeekMethodsModel methodModel) {
        mDispatcher.setMethodsModel(methodModel);
    }

    public SeekMethodsModel getSeekMethodModel() {
        return mDispatcher.getMethodsModel();
    }


    private class Dispatcher {
        private AsyncHandler mAsyncHandler = new AsyncHandler();
        private PostHandler mPostHandler = new PostHandler();
        private UIHandler mUIHandler = new UIHandler();
        private SeekMethodsModel mMethodsModel = new SeekMethodsIncludeInterface();
        private Map<EventTagAndParameter, CopyOnWriteArrayList<EventTagAndParameter>> mCacheList = new HashMap<>();

        void dispatch(Object arg) {
            Queue<EventTagAndParameter> queue = sLocal.get();

            while (!queue.isEmpty()) {
                EventTagAndParameter event = queue.poll();
                handle(event, arg);
            }
        }

        private void handle(EventTagAndParameter event, Object arg) {
            CopyOnWriteArrayList<EventTagAndParameter> list = mMethodsModel.getMethodEvent(event);
            if (list.isEmpty())
                return;
            mCacheList.put(event, list);
            TaskHandler handler = null;
            for (EventTagAndParameter e : list) {
                CopyOnWriteArrayList<Subscription> subscriptions = mCache.get(e);
                if (subscriptions == null)
                    continue;
                for (Subscription subscription : subscriptions) {
                    handler = getHandler(subscription.model);
                    handler.post(subscription, arg);
                }
            }
        }


        public void dispatchSticky(Object subscriber) {
            for (EventTagAndParameter e : stickyCache) {
                handlerSticky(e, subscriber);
            }
        }

        public void handlerSticky(EventTagAndParameter event, Object subscriber) {
            //Find methods which matches event.
            CopyOnWriteArrayList<EventTagAndParameter> list = mMethodsModel.getMethodEvent(event);
            Object arg = event.arg;
            TaskHandler handler = null;
            for (EventTagAndParameter e : list) {
                CopyOnWriteArrayList<Subscription> subscriptions = mCache.get(e);
                if (subscriptions == null)
                    continue;
                for (Subscription s : subscriptions) {
                    handler = getHandler(s.model);
                    handler.post(s, arg);
                }
            }
        }

        private TaskHandler getHandler(EasyEventBusModel model) {
            TaskHandler handler = null;
            if (EasyEventBusModel.ASYNC.equals(model))
                handler = mAsyncHandler;
            if (EasyEventBusModel.POST.equals(model))
                handler = mPostHandler;
            if (EasyEventBusModel.UI.equals(model))
                handler = mUIHandler;
            return handler;
        }

        public SeekMethodsModel getMethodsModel() {
            return mMethodsModel;
        }

        public void setMethodsModel(SeekMethodsModel methodsModel) {
            mMethodsModel = methodsModel;
        }
    }
}
