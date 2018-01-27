package com.example.kevin.easyeventbus.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.kevin.easyeventbus.javabean.Person;

import core.EasyEventBus;

/**
 * Created by kevin on 2018/1/25.
 * https://github.com/yinkaiwen
 */

public class BtnFirstService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        EasyEventBus.getDefault().post(new Person("Hello", 10,Thread.currentThread().getName()), "BtnFirstService");
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
