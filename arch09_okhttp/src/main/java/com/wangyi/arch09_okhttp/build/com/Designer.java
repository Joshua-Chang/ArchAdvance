package com.wangyi.arch09_okhttp.build.com;

public class Designer {

    private BluePrint bluePrint;
    private Worker worker;

    public Designer() {
        this.bluePrint = new BluePrint();
        this.worker=new Worker();
    }

    public Designer addFloor(int floor){
        bluePrint.setFloor(floor);
        return this;
    }
    public Designer addArea(int area){
        bluePrint.setArea(area);
        return this;
    }
    public Designer addColor(String color){
        bluePrint.setColor(color);
        return this;
    }

    public House build(){
        worker.setBluePrint(bluePrint);
        return worker.buildHouse();
    }

}
