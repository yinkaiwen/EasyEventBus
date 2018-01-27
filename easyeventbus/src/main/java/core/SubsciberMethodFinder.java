package core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import easyeventbusenum.EasyEventBusModel;
import wrap.EventTagAndParameter;
import wrap.Subscriber;
import wrap.Subscription;

/**
 * Created by kevin on 2018/1/25.
 * https://github.com/yinkaiwen
 */

public class SubsciberMethodFinder {
    private static final String tag = SubsciberMethodFinder.class.getSimpleName();

    private Map<EventTagAndParameter, CopyOnWriteArrayList<Subscription>> mCache;

    public SubsciberMethodFinder(Map<EventTagAndParameter, CopyOnWriteArrayList<Subscription>> cache) {
        mCache = cache;
    }

    public void subscribe(Object subscriber) {
        sub(subscriber);
    }


    private void sub(Object subscriber) {
        Class<?> cls = subscriber.getClass();
        while (cls != null && !isSystemClass(cls)) {
            Print.i(String.format("class_name : %s", cls.getName()));
            Method[] methods = cls.getDeclaredMethods();
            for (Method m : methods) {
                Class<?>[] parameterTypes = m.getParameterTypes();
                if (parameterTypes != null && parameterTypes.length == 1) {
                    Subscriber s = m.getAnnotation(Subscriber.class);
                    if (s == null)
                        continue;

                    EasyEventBusModel model = s.model();
                    String tag = s.tag();
                    Class<?> parameter = parameterTypes[0];
                    EventTagAndParameter e = new EventTagAndParameter(tag, parameter);
                    CopyOnWriteArrayList<Subscription> cacheSubscriptions = mCache.get(e);
                    if (cacheSubscriptions == null) {
                        cacheSubscriptions = new CopyOnWriteArrayList<>();
                    }
                    Subscription subscription = new Subscription(m, model, subscriber, e);

                    if (!cacheSubscriptions.contains(subscription)) {
                        cacheSubscriptions.add(subscription);
                    }
                    mCache.put(e, cacheSubscriptions);
                }
            }
            cls = cls.getSuperclass();
        }
    }

    public void unregister(Object subscriber) {
        Class<?> cls = subscriber.getClass();
        Iterator<CopyOnWriteArrayList<Subscription>> iterator = mCache.values().iterator();
        while (iterator.hasNext()) {
            CopyOnWriteArrayList<Subscription> subscriptions = iterator.next();
            if (subscriptions != null) {
                List<Subscription> removeList = new LinkedList<>();
                Iterator<Subscription> subscription = subscriptions.iterator();
                while (subscription.hasNext()) {
                    Subscription next = subscription.next();
                    Object cacheObj = next.subscriber.get();
                    if (isObjectEquals(cacheObj, subscriber)) {
                        removeList.add(next);
                    }
                }
                subscriptions.removeAll(removeList);
            }

            if (subscriptions == null || subscriptions.size() == 0) {
                iterator.remove();
            }
        }

    }


    private boolean isSystemClass(Class<?> cls) {
        String className = cls.getName();
        return className.startsWith("java.") || className.startsWith("javax.") || className.startsWith("android.");
    }

    private boolean isObjectEquals(Object cacheObj, Object unregisterObj) {
        if (unregisterObj.equals(cacheObj))
            return true;

        return false;
    }

}
