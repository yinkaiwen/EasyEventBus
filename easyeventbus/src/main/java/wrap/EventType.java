package wrap;

import java.lang.reflect.Method;

import easyeventbusenum.EasyEventBusModel;

/**
 * Created by kevin on 2018/1/25.
 * https://github.com/yinkaiwen
 */

public class EventType {
    public Class<?> parameterClass;
    public String tag;
    public Method method;
    public EasyEventBusModel model;

}
