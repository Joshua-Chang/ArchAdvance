package com.wangyi.arch06_eventbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wangyi.arch06_eventbus.apt.EventBusIndex;
import com.wangyi.arch06_eventbus.model.UserInfo;
import com.wangyi.eventbusannotation.Subscribe;
import com.wangyi.eventbusannotation.mode.ThreadMode;
import com.wangyi.eventbuslibrary.EventBus;


public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 163) {
                UserInfo user = (UserInfo) msg.obj;
                tv.setText(user.toString());
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
        EventBus.getDefault().addIndex(new EventBusIndex());
        EventBus.getDefault().register(this);
    }

    // 跳转按钮
    public void jump(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }

    // 粘性按钮
    public void sticky(View view) {
        EventBus.getDefault().postSticky(new UserInfo("simon", 35));
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void abc(UserInfo user) {
//        tv.setText(user.toString());
//        Log.e("abc", user.toString());
        Log.e("abc", user.toString());
        Message msg = new Message();
        msg.obj = user;
        msg.what = 163;
        handler.sendMessage(msg);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC, priority = 5)
    public void abc2(UserInfo user) {
        //tv.setText(user.toString());
        Log.e("abc2", user.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        EventBus.clearCaches();
    }
}