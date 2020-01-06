package com.wangyi.arch09_okhttp.build.com;

/**
 * 具体的房子，和图纸一样
 */
public class Building {
    private int floor;
    private int area;
    private String color;

    public Building(Builder builder) {
        this.floor = builder.floor;
        this.area = builder.area;
        this.color = builder.color;
    }


    @Override
    public String toString() {
        return "建筑{" +
                "楼层=" + floor +
                ", 面积=" + area +
                ", 颜色='" + color + '\'' +
                '}';
    }

    public static final class Builder {
        private int floor;
        private int area;
        private String color;

        public Builder() {
        }

        public Builder(int floor, int area, String color) {
            this.floor = floor;
            this.area = area;
            this.color = color;
        }


        public Builder setFloor(int floor) {
            this.floor = floor;
            return this;
        }


        public Builder setArea(int area) {
            this.area = area;
            return this;
        }


        public Builder setColor(String color) {
            this.color = color;
            return this;
        }

        public Building build() {
            return new Building(this);
        }
    }
}
