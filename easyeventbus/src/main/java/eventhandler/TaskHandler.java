package eventhandler;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.lang.reflect.Method;

import wrap.Subscription;

/**
 * Created by kevin on 2018/1/26.
 * https://github.com/yinkaiwen
 */

public interface TaskHandler {
    void post(Subscription subscription, Object arg);
}
