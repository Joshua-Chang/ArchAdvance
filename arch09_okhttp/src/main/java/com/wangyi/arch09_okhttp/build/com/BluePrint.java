package com.wangyi.arch09_okhttp.build.com;

/**
 * 具有房子参数的图纸
 */
public class BluePrint {
    private int floor=2;
    private int area=100;
    private String color="Red";

    public BluePrint() {
    }

    public BluePrint(int floor, int area, String color) {
        this.floor = floor;
        this.area = area;
        this.color = color;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "房子{" +
                "楼层=" + floor +
                ", 面积=" + area +
                ", 颜色='" + color + '\'' +
                '}';
    }
}
