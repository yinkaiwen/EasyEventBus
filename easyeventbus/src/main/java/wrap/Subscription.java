package wrap;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

/**
 * Created by kevin on 2018/1/26.
 * https://github.com/yinkaiwen
 */

public class Subscription {

    public Method method;

    public EasyEventBusModel model;

    public Reference<Object> subscriber;

    public EventTagAndParameter event;


    public Subscription(Method method, EasyEventBusModel model, Object subscriber, EventTagAndParameter event) {
        this.method = method;
        this.model = model;
        this.subscriber = new WeakReference<Object>(subscriber);
        this.event = event;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof Subscription) {
            Subscription other = (Subscription) obj;
            if (other.subscriber.get() == subscriber.get()
                    && other.method.equals(method)
                    && other.model.equals(model)
                    && other.event.equals(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int p = 37;
        int rs = 17;
        rs = rs * p + method.hashCode();
        rs = rs * p + model.hashCode();
        rs = rs * p + subscriber.hashCode();
        rs = rs * p + event.hashCode();
        return rs;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "method=" + method +
                ", model=" + model +
                ", subscriber=" + subscriber +
                ", event=" + event +
                '}';
    }
}
