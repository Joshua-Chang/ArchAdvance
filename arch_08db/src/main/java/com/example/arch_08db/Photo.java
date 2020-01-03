package com.example.arch_08db;

import com.example.arch_08db.annotation.DbTable;

@DbTable("tb_photo")
public class Photo {


    private String time;
    private String path;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
