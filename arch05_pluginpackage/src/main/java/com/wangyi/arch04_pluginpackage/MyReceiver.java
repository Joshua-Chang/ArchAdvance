package com.wangyi.arch04_pluginpackage;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.wangyi.arch05_plugin_std.ReceiverInterface;

public class MyReceiver extends BroadcastReceiver implements ReceiverInterface {
    @Override
    public void insertAppContext(Activity appActivity) {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "插件内的广播接收者", Toast.LENGTH_SHORT).show();
    }
}
