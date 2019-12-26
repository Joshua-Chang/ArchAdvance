package com.wangyi.arch05_plugin_std;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public interface ReceiverInterface {
    /**
     * 把宿主(app)的环境  给  插件
     * @param appActivity
     */
    void insertAppContext(Activity appActivity);

    public void onReceive(Context context, Intent intent);
}
