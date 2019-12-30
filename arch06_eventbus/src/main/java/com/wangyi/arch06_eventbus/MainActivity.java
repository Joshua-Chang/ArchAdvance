package com.wangyi.arch06_eventbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wangyi.arch06_eventbus.model.UserInfo;
import com.wangyi.eventbusannotation.Subscribe;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        EventBus.getDefault().register(this);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }
//    @Subscribe
//    public void event(String s){
//        Log.e(">>>",s);
//    }
//    @Subscribe(priority = 10,sticky = true)
//    public void event2(String s){
//        Log.e(">>>",s+"2");
//    }


    public void jump(View view) {
//        EventBus.getDefault().postSticky("sticky");
        startActivity(new Intent(this,SecondActivity.class));
    }





    //Ｍｙ订阅方法
    @Subscribe
    public void onMyEvent(UserInfo userInfo){
        Log.e(">>>",userInfo.toString());
    }
}
