package com.example.kevin.easyeventbus;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import core.EasyEventBus;
import core.Print;

/**
 * Created by kevin on 2018/1/25.
 * https://github.com/yinkaiwen
 */

public class BtnSecondService extends IntentService {


    public BtnSecondService() {
        super("BtnSecondService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        EasyEventBus.getDefault().post(new Person("World", 11,Thread.currentThread().getName()), "BtnSecondService");
    }
}
