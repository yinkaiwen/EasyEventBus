package com.example.kevin.easyeventbus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kevin.easyeventbus.javabean.Auto;
import com.example.kevin.easyeventbus.javabean.Car;
import com.example.kevin.easyeventbus.javabean.Machine;
import com.example.kevin.easyeventbus.javabean.Person;
import com.example.kevin.easyeventbus.javabean.Truck;
import com.example.kevin.easyeventbus.javabean.Yard;
import com.example.kevin.easyeventbus.service.BtnFifthService;
import com.example.kevin.easyeventbus.service.BtnFirstService;
import com.example.kevin.easyeventbus.service.BtnFourthService;
import com.example.kevin.easyeventbus.service.BtnSecondService;
import com.example.kevin.easyeventbus.service.BtnThirdService;

import core.EasyEventBus;
import core.Print;
import wrap.EasyEventBusModel;
import wrap.Subscriber;

public class MainActivity extends Activity implements View.OnClickListener {
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
    private Button mBtn4;
    private Button mBtn5;
    private Button mBtn6;
    private Button mBtn7;
    private TextView mTv_info;

    // End Of Content View Elements

    private void bindViews() {

        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn3 = (Button) findViewById(R.id.btn3);
        mBtn4 = (Button) findViewById(R.id.btn4);
        mBtn5 = (Button) findViewById(R.id.btn5);
        mBtn6 = (Button) findViewById(R.id.btn6);
        mBtn7 = (Button) findViewById(R.id.btn7);
        mTv_info = (TextView) findViewById(R.id.tv_info);
    }


    private void setOnClickListener() {
        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);
        mBtn4.setOnClickListener(this);
        mBtn5.setOnClickListener(this);
        mBtn6.setOnClickListener(this);
        mBtn7.setOnClickListener(this);
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
            case R.id.btn4:
                startService(new Intent(this, BtnFourthService.class));
                break;
            case R.id.btn5:
                startService(new Intent(this, BtnFifthService.class));
                break;
            case R.id.btn6:
                EasyEventBus.getDefault().postSticky(new Auto());
                startActivity(new Intent(this, StickyActivity.class));
                break;
            case R.id.btn7:
                EasyEventBus.getDefault().registerSticky(this);
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

    @Subscriber
    private void fourthInfo(Car car) {
        String rs = String.format("Car : Price : %s,Color : %s", car.getPrice(), car.getColor());
        Print.i(tag, rs);
    }

    @Subscriber
    private void fourthInfo(Machine machine) {
        String rs = String.format("Machine : Weight : %s", machine.getWeight());
        Print.i(tag, rs);
    }

    @Subscriber
    private void fourthInfo(Truck truck) {
        String rs = String.format("Truck : Price : %s,Color : %s,Weight : %s",
                truck.getPrice(), truck.getColor(), truck.getWeight());
        Print.i(tag, rs);
    }

    @Subscriber
    private void fourthInfo(Yard yard) {
        String rs = String.format("Yard : Price : %s,Color : %s,Weight : %s",
                yard.getPrice(), yard.getColor(), yard.getWeight());
        Print.i(tag, rs);
    }


    @Subscriber
    private void fifthInfo(Auto auto) {
        String rs = String.format("Auto : Price : %s,Color : %s", auto.getPrice(), auto.getColor());
        Print.i(tag, rs);
        mTv_info.setText(rs);
    }

    private Handler mHandler;

    private void setText(Person p) {
        final String rs = String.format("setText_thread_name : %s \r\n s : %s",
                Thread.currentThread().getName(), p.toString());
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        Runnable task = new Runnable() {
            @Override
            public void run() {
                mTv_info.setText(rs);
            }
        };
        mHandler.post(task);
        Print.i(tag, rs);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EasyEventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EasyEventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscriber(tag = "StickyActivity")
    private void getStickyEventInfo(Machine machine){
        String rs = machine.getWeight()+"";
        mTv_info.setText(rs);
        EasyEventBus.getDefault().unregisterSticky(this);
    }
}
