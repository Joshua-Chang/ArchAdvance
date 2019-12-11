package com.wangyi.pre_02_aop.aspectJ;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.wangyi.pre_02_aop.LoginActivity;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoginBehaviorAspect {
    @Pointcut("execution(@com.wangyi.pre_02_aop.aspectJ.LoginBehavior * *(..))")
    public void clickPointCut() {
    }

    //处理切入点 Advice通知有Before After Around
    @Around("clickPointCut()")
    public Object clickPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Context context = (Context) joinPoint.getThis();
        if (true) {
            Log.e("xxx","has login");
            return joinPoint.proceed();//切入点继续执行
        } else {
            Log.e("xxx","no login");
            Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, LoginActivity.class));
            return null;//切入点 不执行
        }

    }
}
