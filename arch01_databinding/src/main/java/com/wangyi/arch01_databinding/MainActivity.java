package com.wangyi.arch01_databinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.wangyi.arch01_databinding.databinding.ActivityMainBinding;
import com.wangyi.arch01_databinding.model.UserInfo;

public class MainActivity extends AppCompatActivity {
    private UserInfo userInfo = new UserInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        // 单向绑定第一种方式：<Model -- View>
//        userInfo.setName("Tom");
//        userInfo.setPwd("ttt");
//        binding.setUser(userInfo);
//        Log.e("xxx",userInfo.toString());
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                userInfo.setName("JJJ");
//                userInfo.setPwd("JJJ");
//                Log.e("xxx",userInfo.toString());
//            }
//        },3000);

        // 单向绑定第二种方式：<Model -- View>
//        userInfo.name.set("Tom");
//        userInfo.pwd.set("ttt");
//        binding.setUser(userInfo);
//        Log.e("xxx", userInfo.toString());
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                userInfo.name.set("JJJ");
//                userInfo.pwd.set("JJJ");
//                Log.e("xxx", userInfo.name.get()+"\t"+userInfo.pwd.get());
//            }
//        }, 3000);

        // 双向绑定(Model --- View     View  --- Model)
//        首先model改变view
        userInfo.name.set("Tom");
        userInfo.pwd.set("ttt");
        binding.setUser(userInfo);
        Log.e("xxx",userInfo.toString());
                new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //view改变model
                //"@={user.name}" 加等号双向绑定
                //"@{user.pwd}"  不加等号单向绑定
                Log.e("xxx", userInfo.name.get()+"\t"+userInfo.pwd.get());
            }
        }, 20000);

        /**
         * 内存消耗过大原因:
         * 1.apt生成的代码impl类中:根据tag加快比,对每一个控件都添加tag,将tag存于数组中,object数组产生额外的内存开销
         * 2.针对每一个绑定的view(activity)都做全局监听
         * 3.产生监听事件,都要用handler刷新,looper管道一直存在
         */
    }
}
