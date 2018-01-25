package core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import wrap.EventType;
import wrap.Subscriber;

/**
 * Created by kevin on 2018/1/25.
 * https://github.com/yinkaiwen
 */

public class EasyEventBus {

    private static final String tag = EasyEventBus.class.getSimpleName();

    private static EasyEventBus instance = null;
    private HashMap<Object, List<EventType>> map;

    private EasyEventBus() {
        map = new HashMap<>();
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

    public void register(Object object) {
        if (map.containsKey(object))
            throw new RuntimeException("This object has been registered.");

        List<EventType> subs = new ArrayList<>();
        Class<?> cls = object.getClass();
        Method[] methods = cls.getDeclaredMethods();
        if (methods == null || methods.length == 0)
            throw new IllegalArgumentException("This object has no any method");
        for (Method m : methods) {
            Subscriber s = m.getAnnotation(Subscriber.class);
            if (s != null) {
                EventType type = new EventType();
                type.model = s.model();
                type.tag = s.tag();
                type.method = m;
                Class<?>[] parameterTypes = m.getParameterTypes();
                if(parameterTypes != null && parameterTypes.length == 1){
                    type.parameterClass = m.getParameterTypes()[0];
                }else {
                    throw new IllegalArgumentException("Use @Subscriber should use only one type.");
                }
                subs.add(type);
            }
        }

        map.put(object, subs);
    }

    public void unRegister(Object object) {
        if (!map.containsKey(object))
            throw new RuntimeException("This object has not been registered.");
        List<EventType> remove = map.remove(object);
        for (EventType m : remove) {
            String name = m.method.getName();
            Print.i(tag, String.format("method_name --> %s", name));
        }
    }

    public <T> void post(T obj) {
        post(obj, Subscriber.DEFAULT_TAG);
    }

    public <T> void post(T obj, String tag) {
        SubsciberMethodHunter.post(obj, tag, map);
    }
}
