package com.wangyi.pre_01_adapter;

public interface CallAdapter <R,T>{
    T adpter(Call<R> call);
    abstract class Factory{
        public abstract CallAdapter<?,?>get();
    }
}
