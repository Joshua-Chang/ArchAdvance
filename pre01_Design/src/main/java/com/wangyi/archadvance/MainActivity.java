package com.wangyi.archadvance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wangyi.archadvance.factory.Api;
import com.wangyi.archadvance.factory.ApiImpl;
import com.wangyi.archadvance.factory.SimpleFactory;
import com.wangyi.archadvance.factory.core.PropertiesFactory;
import com.wangyi.archadvance.factory.impl.ParameterFactory;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    /**
    * 工厂模式
    * 核心:提供一个创建对象的功能,不需要关系具体实现
     * 降低耦合
    */
//        //常规编码
//        ApiImpl api = new ApiImpl();
//        api.create();
//
//        //简单工厂
//        Api apiSimple = SimpleFactory.createApi();
//        apiSimple.create();
//
//
//        //拓展:根据参数,产生不同的实现
//        Api apiParam = ParameterFactory.createApi(1);
//        if (apiParam != null) api.create();
//
//        //根据配置文件产生不同的实现
//        Api apiProperties = PropertiesFactory.createApi(this);
//        if (apiProperties != null) apiProperties.create();


        /**
         * 外观模式(门面模式)
         * 隐藏了系统的复杂性,为系统中的一组接口,提供统一的访问接口
         * 高内聚\低耦合
         * 场景模拟:图片加载
         */


        /**
         * 适配器模式
         * 存在于不匹配的两者的连接,化不匹配为匹配
         */

    }
}
