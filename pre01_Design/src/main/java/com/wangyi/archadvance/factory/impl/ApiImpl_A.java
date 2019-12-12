package com.wangyi.archadvance.factory.impl;

import android.util.Log;

import com.wangyi.archadvance.factory.Api;
import com.wangyi.archadvance.factory.bean.UserInfo;

public class ApiImpl_A implements Api {
    @Override
    public UserInfo create() {
        UserInfo info=new UserInfo("ApiImpl_A");
        Log.e("xxx",info.toString());
        return info;
    }
}
