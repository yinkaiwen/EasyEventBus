package wrap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import easyeventbusenum.EasyEventBusModel;

/**
 * Created by kevin on 2018/1/25.
 * https://github.com/yinkaiwen
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscriber {
    String DEFAULT_TAG = "subscriber";

    String tag() default DEFAULT_TAG;

    EasyEventBusModel model() default EasyEventBusModel.UI;
}
