package com.example.kevin.easyeventbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import core.EasyEventBus;
import core.Print;
import easyeventbusenum.EasyEventBusModel;
import wrap.Subscriber;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String tag = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        setOnClickListener();
    }


    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private TextView mTv_info;

    // End Of Content View Elements

    private void bindViews() {

        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn3 = (Button) findViewById(R.id.btn3);
        mTv_info = (TextView) findViewById(R.id.tv_info);
    }


    private void setOnClickListener() {
        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                startService(new Intent(this, BtnFirstService.class));
                break;
            case R.id.btn2:
                startService(new Intent(this, BtnSecondService.class));
                break;
            case R.id.btn3:
                startService(new Intent(this, BtnThirdService.class));
                break;
        }
    }

    @Subscriber(tag = "BtnFirstService", model = EasyEventBusModel.POST)
    private void firstInfo(Person p) {
        setText(p);
    }

    @Subscriber(model = EasyEventBusModel.UI, tag = "BtnSecondService")
    private void secondInfo(Person p) {
        setText(p);
    }

    @Subscriber(model = EasyEventBusModel.ASYNC, tag = "BtnThirdService")
    private void thirdInfo(Person p) {
        setText(p);
    }


    private void setText(Person p) {
        String rs = String.format("setText_thread_name : %s \r\n s : %s",
                Thread.currentThread().getName(), p.toString());
        mTv_info.setText(rs);
        Print.i(tag, rs);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EasyEventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EasyEventBus.getDefault().unRegister(this);
        super.onStop();
    }
}
