package com.wangyi.archadvance.factory.core;

import android.content.Context;

import com.wangyi.archadvance.factory.Api;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesFactory {
    public static Api createApi(Context context){
        //加载配置文件
        Properties properties=new Properties();
//        main/assets中
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("config.properties");
//        main/res/raw中
//        InputStream inputStream = context.getResources().openRawResource("config.properties");
//        java写法
            PropertiesFactory.class.getResourceAsStream("assets/config.properties");
            properties.load(inputStream);
            Class clazz = Class.forName(properties.getProperty("create_a"));
            return (Api) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
return null;
    }
}
