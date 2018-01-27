package eventhandler;

import android.os.Handler;
import android.os.Looper;

import wrap.Subscription;


/**
 * Created by kevin on 2018/1/25.
 * https://github.com/yinkaiwen
 */

public class UIHandler implements TaskHandler {

    private Handler mHandler;
    private TaskHandler mTaskHandler = new PostHandler();

    @Override
    public void post(final Subscription subscription, final Object arg) {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        Runnable task = new Runnable() {
            @Override
            public void run() {
                mTaskHandler.post(subscription, arg);
            }
        };
        mHandler.post(task);
    }
}
