package seekmethods;

import java.util.concurrent.CopyOnWriteArrayList;

import wrap.EventTagAndParameter;

/**
 * Created by kevin on 2018/1/27.
 * https://github.com/yinkaiwen
 */

public class SeekMethodsMatchCase implements SeekMethodsModel {
    @Override
    public CopyOnWriteArrayList<EventTagAndParameter> getMethodEvent(EventTagAndParameter event) {
        CopyOnWriteArrayList<EventTagAndParameter> list = new CopyOnWriteArrayList<>();
        list.add(event);
        return list;
    }
}
