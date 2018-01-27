package com.example.kevin.easyeventbus.javabean;

import android.graphics.Color;

/**
 * Created by kevin on 2018/1/26.
 * https://github.com/yinkaiwen
 */

public class Auto implements Car,Machine {

    private int price = 10000;

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int getColor() {
        return Color.RED;
    }

    @Override
    public int getWeight() {
        return 1000;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "price=" + price +
                '}';
    }
}
