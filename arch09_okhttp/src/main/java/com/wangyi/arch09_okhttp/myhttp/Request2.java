package com.wangyi.arch09_okhttp.myhttp;

import java.util.HashMap;
import java.util.Map;

public class Request2 {
    private static final String GET="GET";
    private static final String POST="POST";

    private String url;
    private String requestMethod=GET;
    private Map<String,String>mHeaderList=new HashMap<>();

    public String getUrl() {
        return url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public Map<String, String> getmHeaderList() {
        return mHeaderList;
    }

    public Request2() {
        this(new Builder());
    }

    public Request2(Builder builder) {
        this.url=builder.url;
        this.requestMethod=builder.requestMethod;
        this.mHeaderList=builder.mHeaderList;
    }

    public static final class Builder{
        private String url;
        private String requestMethod=GET;
        private Map<String,String>mHeaderList=new HashMap<>();

        public Builder url(String url) {
            this.url = url;
            return this;
        }
        public Builder get(){
            requestMethod=GET;
            return this;
        }
        public Builder post(){
            requestMethod=POST;
            return this;
        }

        public Builder addRequestHeader(String k, String v) {
            mHeaderList.put(k, v);
            return this;
        }

        public Request2 build(){
            return new Request2(this);
        }
    }
}
