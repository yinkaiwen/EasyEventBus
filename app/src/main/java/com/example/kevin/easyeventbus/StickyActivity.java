package com.example.kevin.easyeventbus;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kevin.easyeventbus.javabean.Auto;
import com.example.kevin.easyeventbus.javabean.Car;
import com.example.kevin.easyeventbus.javabean.Machine;
import com.example.kevin.easyeventbus.javabean.Truck;

import core.EasyEventBus;
import core.Print;
import wrap.Subscriber;

/**
 * Created by kevin on 2018/1/28.
 * https://github.com/yinkaiwen
 */

public class StickyActivity extends Activity implements View.OnClickListener {

    private static final String tag = StickyActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky);

        bindViews();
        addOnclick();
    }

    private Button mBtn_post_sticky;
    private Button mBtn_get_sticky;
    private TextView mTv_info;

    // End Of Content View Elements

    private void bindViews() {

        mBtn_post_sticky = (Button) findViewById(R.id.btn_post_sticky);
        mBtn_get_sticky = (Button) findViewById(R.id.btn_get_sticky);
        mTv_info = (TextView) findViewById(R.id.tv_info);
    }


    private void addOnclick() {
        mBtn_get_sticky.setOnClickListener(this);
        mBtn_post_sticky.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_sticky:
                EasyEventBus.getDefault().registerSticky(this);
                break;
            case R.id.btn_post_sticky:
                EasyEventBus.getDefault().postSticky(new Truck(), "StickyActivity");
                break;
        }
    }

    @Subscriber
    private void getMainActivityStickyInfo(Auto auto) {
        if (auto != null){
            mTv_info.setText(auto.toString());
            Print.i(tag,auto.toString());
        }else{
            Print.i(tag,"auto -- null");
        }
        EasyEventBus.getDefault().removeSticky(Auto.class);
    }

    @Override
    protected void onStop() {
        EasyEventBus.getDefault().unregister(this);
        super.onStop();
    }
}
