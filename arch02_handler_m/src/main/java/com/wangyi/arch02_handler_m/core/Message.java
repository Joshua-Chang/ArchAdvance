package com.wangyi.arch02_handler_m.core;

public class Message {
    public int what;
    public Handler target;
    public Object obj;

    public Message() {
    }

    public Message(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "Message{" +
                "obj=" + obj.toString() +
                '}';
    }
}
