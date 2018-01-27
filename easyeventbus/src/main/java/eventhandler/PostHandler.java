package eventhandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import core.Print;
import wrap.Subscription;

/**
 * Created by kevin on 2018/1/25.
 * https://github.com/yinkaiwen
 */

public class PostHandler implements TaskHandler {
    private static final String tag = PostHandler.class.getSimpleName();

    @Override
    public void post(Subscription subscription, Object arg) {
        try {
            subscription.method.setAccessible(true);
//            Print.w(tag, String.format("subscription : %s,arg : %s", subscription.toString(), arg.toString()));
            Print.w(tag, String.format("%s(%s),%s",
                    subscription.method.getName(), subscription.event.parameterClass.getSimpleName(), arg.getClass().getSimpleName()));
            subscription.method.invoke(subscription.subscriber.get(), arg);
        } catch (IllegalAccessException e) {
            Print.e(tag, e.getMessage());
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            Print.e(tag, e.getMessage());
            e.printStackTrace();
        }
    }
}
