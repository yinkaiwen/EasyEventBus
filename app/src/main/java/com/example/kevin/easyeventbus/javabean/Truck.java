package com.example.kevin.easyeventbus.javabean;

import android.graphics.Color;

/**
 * Created by kevin on 2018/1/26.
 * https://github.com/yinkaiwen
 */

public class Truck implements Car ,Machine{
    @Override
    public int getPrice() {
        return 30000;
    }

    @Override
    public int getColor() {
        return Color.BLACK;
    }

    @Override
    public int getWeight() {
        return 40000;
    }

    @Override
    public String toString() {
        return String.format("toString:getPrice()-%s,getColor-%s,getWeight-%s",getPrice(),getColor(),getWeight());
    }
}
