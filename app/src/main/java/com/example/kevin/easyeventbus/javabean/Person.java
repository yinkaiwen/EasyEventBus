package com.example.kevin.easyeventbus.javabean;

/**
 * Created by kevin on 2018/1/25.
 * https://github.com/yinkaiwen
 */

public class Person {

    public String name;
    public int age;
    public String postThreadName;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", postThreadName='" + postThreadName + '\'' +
                '}';
    }

    public Person(String name, int age, String postThreadName) {
        this.name = name;
        this.age = age;
        this.postThreadName = postThreadName;
    }
}
