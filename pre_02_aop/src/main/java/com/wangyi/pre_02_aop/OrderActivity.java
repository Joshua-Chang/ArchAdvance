package com.wangyi.pre_02_aop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wangyi.pre_02_aop.aspectJ.ClickBehavior;
import com.wangyi.pre_02_aop.aspectJ.LoginBehavior;

public class OrderActivity extends AppCompatActivity {

    private static final String TAG = "xxx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        /**
         * AOP方式二 预编译期 aspectJ切面
         */
    }

    //都需要做用户行为统计
    @ClickBehavior("登录")
    public void login(View view) {
        Log.e(TAG, "login: ");

    }

    @ClickBehavior("我的积分")
    @LoginBehavior
    public void score(View view) {
        Log.e(TAG, "score: ");
        startActivity(new Intent(this,LoginedActivity.class));
    }

    @ClickBehavior("我的优惠券")
    @LoginBehavior
    public void coupon(View view) {
        Log.e(TAG, "coupon: ");
        startActivity(new Intent(this,LoginedActivity.class));
    }

    @ClickBehavior("我的专区")
    @LoginBehavior
    public void area(View view) {
        Log.e(TAG, "area: ");
        startActivity(new Intent(this,LoginedActivity.class));
    }
}
