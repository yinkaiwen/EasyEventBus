package com.example.kevin.easyeventbus;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import core.EasyEventBus;
import core.Print;

/**
 * Created by kevin on 2018/1/25.
 * https://github.com/yinkaiwen
 */

public class BtnThirdService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        EasyEventBus.getDefault().post(new Person("THIRD",911,Thread.currentThread().getName()),"BtnThirdService");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
