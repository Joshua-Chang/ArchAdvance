package com.wangyi.arch03_pms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.wangyi.arch03_pms.pms.MyContext;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context=this;
        try {
            context.getPackageManager().getPackageInfo("",0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        MyContext myContext = new MyContext();
        myContext.getPackageManager().getPackageInfo(null,0);

    }
}
