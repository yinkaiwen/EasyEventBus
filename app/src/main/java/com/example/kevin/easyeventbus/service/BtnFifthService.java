package com.example.kevin.easyeventbus.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.kevin.easyeventbus.javabean.Auto;

import core.EasyEventBus;

/**
 * Created by kevin on 2018/1/26.
 * https://github.com/yinkaiwen
 */

public class BtnFifthService extends IntentService {

    public BtnFifthService() {
        super(BtnFourthService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        EasyEventBus.getDefault().post(new Auto());
    }
}
