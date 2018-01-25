package eventhandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kevin on 2018/1/25.
 * https://github.com/yinkaiwen
 */

public class AsyncHandler {
    static <T> void post(final Method method, final T obj, final Object arg) {
        Runnable asyncTask = new Runnable() {
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

        ExecutorService exe = Executors.newSingleThreadExecutor();
        exe.execute(asyncTask);
    }
}
