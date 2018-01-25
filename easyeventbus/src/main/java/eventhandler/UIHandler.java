package eventhandler;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Created by kevin on 2018/1/25.
 * https://github.com/yinkaiwen
 */

public class UIHandler {

    static <T> void post(final Method method, final T obj, final Object arg) {
        Handler handler = new Handler(Looper.getMainLooper());

        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    method.invoke(obj, arg);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        };

        handler.post(task);
    }
}
