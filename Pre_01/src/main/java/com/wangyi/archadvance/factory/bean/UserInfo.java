package com.wangyi.archadvance.factory.bean;

import androidx.annotation.NonNull;

public class UserInfo {
    private String name;
    private int age;

    public UserInfo(String name) {
        this.name = name;
    }

    public UserInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public UserInfo() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @NonNull
    @Override
    public String toString() {
        return name+" :"+age;
    }
}
