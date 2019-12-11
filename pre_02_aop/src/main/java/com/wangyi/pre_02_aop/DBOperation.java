package com.wangyi.pre_02_aop;

public interface DBOperation {
    void insert();
    void update();
    void delete();
    //每一次都要备份
    void save();
}
