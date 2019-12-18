package com.wangyi.arch03_skin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.wangyi.arch03_skinlib.utils.PreferencesUtils;

public class NightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night);
//MODE_NIGHT_NO: 使用亮色(light)主题,不使用夜间模式
//MODE_NIGHT_YES:使用暗色(dark)主题,使用夜间模式
//MODE_NIGHT_AUTO:根据当前时间自动切换 亮色(light)/暗色(dark)主题
//MODE_NIGHT_FOLLOW_SYSTEM(默认选项):设置为跟随系统,通常为 MODE_NIGHT_NO
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        AppCompatDelegate.setDefaultNightMode(mode) 可以设置全局的夜间模式
//        AppCompatDelegate.setLocalNightMode(mode)可以设置局部的夜间模式

    }
    public void dayOrNight(View view) {
//        int uiMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//        switch (uiMode) {
//            case Configuration.UI_MODE_NIGHT_NO:
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                PreferencesUtils.putBoolean(this, "isNight", true);
//                break;
//            case Configuration.UI_MODE_NIGHT_YES:
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                PreferencesUtils.putBoolean(this, "isNight", false);
//                break;
//            default:
//                break;
//        }
        boolean isNight = PreferencesUtils.getBoolean(this, "isNight");
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            PreferencesUtils.putBoolean(this,"isNight",false);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            PreferencesUtils.putBoolean(this,"isNight",true);
        }
        recreate();
    }
}
