package core;

import android.util.Log;

/**
 * Created by kevin on 2018/1/25.
 * https://github.com/yinkaiwen
 */

public class Print {
    private static boolean IS_DEBUG = true;
    private static final String tag = Print.class.getSimpleName();

    public static void i(String msg) {
        i(tag, msg);
    }

    public static void w(String msg) {
        w(tag, msg);
    }

    public static void d(String msg) {
        d(tag, msg);
    }

    public static void e(String msg) {
        e(tag, msg);
    }

    public static void v(String msg) {
        v(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (IS_DEBUG)
            Log.i(tag, msg);
    }


    public static void d(String tag, String msg) {
        if (IS_DEBUG)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (IS_DEBUG)
            Log.e(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (IS_DEBUG)
            Log.w(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (IS_DEBUG)
            Log.v(tag, msg);
    }

}
