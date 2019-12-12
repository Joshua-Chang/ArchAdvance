package com.wangyi.pre_01_adapter;

public class Retrofit {
    private CallAdapter.Factory factory;

    private Retrofit(CallAdapter.Factory factory) {
        this.factory=factory;
    }
    public CallAdapter<?,?>callAdapter(){
        return factory.get();
    }

    @SuppressWarnings("unchecked")
    public <T>T create(){
        ServicesMethod servicesMethod=new ServicesMethod.Builder<>(this).build();
        OkHttpCall<Object>okHttpCall=new OkHttpCall<>();
        return (T) servicesMethod.adpter(okHttpCall);
    }
    public static final class Builder{
        CallAdapter.Factory factory;
        public Builder addCallAdapterFactory(CallAdapter.Factory factory){
            this.factory=factory;
            return this;
        }
        public Retrofit build(){
            if (factory == null) {
                factory=new ExecutorCallAdapterFactory();
            }
            return new Retrofit(factory);
        }
    }
}
