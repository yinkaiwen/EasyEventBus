package eventhandler;

import java.lang.reflect.Method;

import easyeventbusenum.EasyEventBusModel;

/**
 * Created by kevin on 2018/1/25.
 * https://github.com/yinkaiwen
 */

public class EventHandler {

    public static <T> void post(T obj, Object arg, EasyEventBusModel model, Method method) {
        method.setAccessible(true);
        if (EasyEventBusModel.POST.equals(model)) {
            PostHandler.post(method, obj, arg);
        }
        if (EasyEventBusModel.UI.equals(model)) {
            UIHandler.post(method, obj, arg);
        }
        if (EasyEventBusModel.ASYNC.equals(model)) {
            AsyncHandler.post(method, obj, arg);
        }
    }

}
