package com.wangyi.arch09_okhttp.myhttp;

public class OkHttpClient2 {
    Dispatcher2 dispatcher2;
    boolean isCanceled;
    int recount;
    public OkHttpClient2() {
        this(new Builder());
    }

    public OkHttpClient2(Builder builder) {
        this.dispatcher2=builder.dispatcher2;
        this.isCanceled=builder.isCanceled;
//        this.recount = builder.recount;
    }

    public Dispatcher2 dispatcher2() {
        return dispatcher2;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public int getRecount() {
        return recount;
    }

    public static final class Builder{
        Dispatcher2 dispatcher2;
        boolean isCanceled;
        int recount;

        public Builder() {
            this.dispatcher2=new Dispatcher2();
            this.recount = 3;
        }

        public OkHttpClient2 build(){
            return new OkHttpClient2(this);
        }

        public Builder dispatcher2(Dispatcher2 dispatcher2){
            this.dispatcher2=dispatcher2;
            return this;
        }

        public Builder setCanceled(boolean canceled) {
            isCanceled = canceled;
            return this;
        }

        public Builder setRecount(int recount) {
            this.recount = recount;
            return this;
        }
    }

    public Call2 newCall(Request2 request2){
        return new RealCall2(this, request2);
    }
}
