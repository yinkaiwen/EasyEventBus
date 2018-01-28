package wrap;

/**
 * Created by kevin on 2018/1/25.
 * https://github.com/yinkaiwen
 */

public class EventTagAndParameter {
    public String tag;
    public Class<?> parameterClass;
    public Object arg;


    public EventTagAndParameter(String tag, Class<?> parameterClass) {
        this.tag = tag;
        this.parameterClass = parameterClass;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null)
            return false;

        if (other instanceof EventTagAndParameter) {
            EventTagAndParameter o = (EventTagAndParameter) other;
            if (o.tag.equals(tag) && o.parameterClass.equals(parameterClass)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        /*
        *  p and rs come from <Thinking in Java Fourth Edition> Author:Bruce Eckel
        */
        int p = 37;
        int rs = 17;
        rs = p * rs + tag.hashCode();
        rs = p * rs + parameterClass.hashCode();
        return rs;
    }
}
