package com.wangyi.arch06_eventbus;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import com.wangyi.arch06_eventbus.model.UserInfo;
import com.wangyi.eventbusannotation.Subscribe;
import com.wangyi.eventbusannotation.mode.ThreadMode;
import com.wangyi.eventbuslibrary.EventBus;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    // 发送事件按钮
    public void post(View view) {
        // 发送消息 / 事件
//        EventBus.getDefault().post(new UserInfo("simon", 35));
//        finish();

        new Thread(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new UserInfo("simon", 35));
                finish();
            }
        }).start();
    }

    // 激活粘性按钮
    public void sticky(View view) {
        EventBus.getDefault().register(this);
        EventBus.getDefault().removeStickyEvent(UserInfo.class);
    }

    // Sticky粘性，美 [ˈstɪki]
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void sticky(UserInfo user) {
        Log.e("sticky", user.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 示例代码
        UserInfo userInfo = EventBus.getDefault().getStickyEvent(UserInfo.class);
        if (userInfo != null) {
            UserInfo info = EventBus.getDefault().removeStickyEvent(UserInfo.class);
            if (info != null) {
                EventBus.getDefault().removeAllStickyEvents();
            }
        }
        EventBus.getDefault().unregister(this);
    }
}

