package seekmethods;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import core.Print;
import wrap.EventTagAndParameter;

/**
 * Created by kevin on 2018/1/27.
 * https://github.com/yinkaiwen
 */

public class SeekMethodsIncludeInterface implements SeekMethodsModel {


    @Override
    public CopyOnWriteArrayList<EventTagAndParameter> getMethodEvent(EventTagAndParameter event) {
        CopyOnWriteArrayList<EventTagAndParameter> list = new CopyOnWriteArrayList<>();
        Class<?> cls = event.parameterClass;
        while (cls != null) {
            list.add(new EventTagAndParameter(event.tag,cls));
            addInterface(list, cls, event.tag);
            cls = cls.getSuperclass();
            if(cls == null){
                Print.d("null");
            }else{
                Print.d(cls.getSimpleName());
            }

        }
        return list;
    }


    private void addInterface(List<EventTagAndParameter> list, Class<?> cls, String tag) {
        if (cls == null || cls.isInterface())
            return;
        Class<?>[] interfaces = cls.getInterfaces();
        for (Class<?> i : interfaces) {
            EventTagAndParameter e = new EventTagAndParameter(tag, i);
            if (!list.contains(e)) {
                list.add(e);
            }
        }
    }
}
