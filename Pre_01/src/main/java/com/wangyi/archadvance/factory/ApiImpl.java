package com.wangyi.archadvance.factory;

import android.util.Log;

import com.wangyi.archadvance.factory.bean.UserInfo;

public class ApiImpl implements Api {
    @Override
    public UserInfo create() {
        UserInfo info=new UserInfo();
        Log.e("xxx",info.toString());
        return info;
    }
}
