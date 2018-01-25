package core;

import java.util.HashMap;
import java.util.List;

import eventhandler.EventHandler;
import wrap.EventType;

/**
 * Created by kevin on 2018/1/25.
 * https://github.com/yinkaiwen
 */

public class SubsciberMethodHunter {

    static <T> void post(T obj, String tag, HashMap<Object, List<EventType>> map) {
        if (map == null || map.size() == 0) {
            throw new RuntimeException("You should register some object before use post()");
        }

        for (HashMap.Entry<Object, List<EventType>> e : map.entrySet()) {
            Object key = e.getKey();
            List<EventType> methods = e.getValue();

            for (EventType t : methods) {
                if (tag.equals(t.tag)) {
                    Class<?> targetClass = obj.getClass();
                    if (targetClass.equals(t.parameterClass)) {
                        EventHandler.post(key, obj, t.model, t.method);
                    }
                }
            }
        }
    }
}
