package seekmethods;

import java.util.concurrent.CopyOnWriteArrayList;

import wrap.EventTagAndParameter;

/**
 * Created by kevin on 2018/1/27.
 * https://github.com/yinkaiwen
 */

public interface SeekMethodsModel {
    CopyOnWriteArrayList<EventTagAndParameter> getMethodEvent(EventTagAndParameter event);
}
