package eventhandler;

import android.os.Handler;
import android.os.HandlerThread;

import wrap.Subscription;

/**
 * Created by kevin on 2018/1/25.
 * https://github.com/yinkaiwen
 */

public class AsyncHandler implements TaskHandler {

    private TaskHandler mTaskHandler = new PostHandler();
    private AsyncTask mAsyncTask = new AsyncTask(AsyncHandler.class.getSimpleName());

    public AsyncHandler() {
        mAsyncTask.start();
    }

    @Override
    public void post(final Subscription subscription, final Object arg) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                mTaskHandler.post(subscription,arg);
            }
        };
        mAsyncTask.post(task);
    }

    class AsyncTask extends HandlerThread{

        private Handler mHandler;

        public AsyncTask(String name) {
            super(name);
        }

        public void post(Runnable task){
            if(mHandler == null){
                mHandler = new Handler(getLooper());
            }
            mHandler.post(task);
        }

    }
}
