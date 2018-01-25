package eventhandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by kevin on 2018/1/25.
 * https://github.com/yinkaiwen
 */

public class PostHandler {
    static <T> void post(Method method, T obj, Object arg) {
        try {
            method.invoke(obj,arg);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
