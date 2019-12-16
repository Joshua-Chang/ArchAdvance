package com.wangyi.arch03_pms.pms;

public class MyContext {
    public MyPackageManager getPackageManager(){
        return new MyApplicationPackageManager();
    }
}
