package com.wangyi.arch09_okhttp.build;

import android.graphics.Color;

import com.wangyi.arch09_okhttp.build.com.Building;
import com.wangyi.arch09_okhttp.build.com.Designer;
import com.wangyi.arch09_okhttp.build.com.House;

public class UserClient {
    public static void main(String[] args) {
        Designer designer=new Designer().addFloor(3).addArea(600).addColor("Blue");
        designer.addColor("Red");
        House house = designer.build();
        System.out.println(house);

        Building building=new Building.Builder().setArea(100).setFloor(2).setColor("Grey").build();
        System.out.println(building);
    }
}
