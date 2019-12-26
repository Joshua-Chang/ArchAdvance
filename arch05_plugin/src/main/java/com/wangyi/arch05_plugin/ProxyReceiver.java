package com.wangyi.arch05_plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wangyi.arch05_plugin_std.ReceiverInterface;

public class ProxyReceiver extends BroadcastReceiver {
    private String  pluginReceiverClassName;//插件内的MyReceiver全类名
    public ProxyReceiver(String pluginReceiverClassName) {
        this.pluginReceiverClassName=pluginReceiverClassName;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
//        加载插件里的MyReceiver
        try {
            Class<?> myReceiverClass = PluginManager.getInstance(context).getDexClassLoader().loadClass(pluginReceiverClassName);
            Object myReceiver = myReceiverClass.newInstance();
            ReceiverInterface receiverInterface= (ReceiverInterface) myReceiver;
            receiverInterface.onReceive(context, intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
