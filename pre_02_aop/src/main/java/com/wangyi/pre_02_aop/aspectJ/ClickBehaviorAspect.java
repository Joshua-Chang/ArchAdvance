package com.wangyi.pre_02_aop.aspectJ;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class ClickBehaviorAspect {
    //找到切入点
    //execution,以方法执行时为切入点,触发Aspect类
    //通配符* *(..)) 所有
    @Pointcut("execution(@com.wangyi.pre_02_aop.aspectJ.ClickBehavior * *(..))")
    public void clickPointCut(){}
    //处理切入点 Advice通知有Before After Around
    @Around("clickPointCut()")
    public Object clickPoint(ProceedingJoinPoint joinPoint)throws Throwable{//遵循1.返回值2.参数3.异常规则
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();//类名
        String methodName = signature.getName();//方法名
        String funName = signature.getMethod().getAnnotation(ClickBehavior.class).value();//注解值
        long l = System.currentTimeMillis();
        Log.e("xxx","start:"+l);
        Object proceed = joinPoint.proceed();//执行切面中的方法
        long m = System.currentTimeMillis();
        Log.e("xxx","end:"+l);
        //存储本地，隔多少天上传
        Log.e("xxx",String.format("用户点击了： %s,耗时:%d ms,通过%s类%s方法",funName,m-l,className,methodName));
        return proceed;
    }
}
