package com.wangyi.arch04_pluginpackage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 开机：
 * linux内核启动--》init进程--》zygote进程孵化--》SystemServer--》启动其他系统服务如pms、ams等
 *
 * pms如何处理data.app 如何解析apk
 * main-》scanDirTracedLI扫描data/app目录下的apk文件-》解析AndroidManifest:
 * PackageParser.parsePackage()->解析apk的所有信息,返回Package（AndroidManifest内的所有信息）
 *
 *
 * 系统开机后/安装apk后 扫描data/app 解析AndroidManifest
 * 扫描解析AndroidManifest后，会将解析到的静态广播自动注册
 * data.app: 放置目录
 * data.data.包名: 应用所用目录
 * data.dalvik-cache: 虚拟机去加载执行指令
 */
public class StaticReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "插件内静态注册的广播接收者", Toast.LENGTH_SHORT).show();
    }
}
