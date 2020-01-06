package com.wangyi.arch09_okhttp.build.com;

public class Worker {
    private BluePrint bluePrint;

    public void setBluePrint(BluePrint bluePrint) {
        this.bluePrint = bluePrint;
    }

    public House buildHouse(){
        House house = new House();
        house.setFloor(bluePrint.getFloor());
        house.setArea(bluePrint.getArea());
        house.setColor(bluePrint.getColor());
        return house;
    }
}
