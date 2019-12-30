package com.wangyi.eventbusannotation.mode;

/**
 * 所有事件集合
 * 相当于源码SimpleSubscriberInfo
 */
public class EventBeans implements SubscriberInfo {

    // 订阅者对象Class，如：MainActivity.class
    private final Class subscriberClass;
    // 订阅方法数组，参考SimpleSubscriberInfo.java 25行
    private final SubscriberMethod[] methodInfos;

    public EventBeans(Class subscriberClass, SubscriberMethod[] methodInfos) {
        this.subscriberClass = subscriberClass;
        this.methodInfos = methodInfos;
    }

    @Override
    public Class<?> getSubscriberClass() {
        return subscriberClass;
    }

    @Override
    public synchronized SubscriberMethod[] getSubscriberMethods() {
        return methodInfos;
    }
}
