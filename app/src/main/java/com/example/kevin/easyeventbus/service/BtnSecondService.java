package com.example.kevin.easyeventbus.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.kevin.easyeventbus.javabean.Person;

import core.EasyEventBus;

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
