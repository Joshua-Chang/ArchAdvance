package com.wangyi.arch09_okhttp.build.com;

/**
 * 具体的房子，和图纸一样
 */
public class House {
    private int floor;
    private int area;
    private String color;

    public House() {
    }

    public House(int floor, int area, String color) {
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
