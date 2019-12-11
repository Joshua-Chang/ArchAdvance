package com.wangyi.archadvance.adapter.retrofit;


import com.wangyi.pre_01_adapter.Call;
import com.wangyi.pre_01_adapter.CallAdapter;

import rx.Observable;


public class RxjavaCallAdapterFactory extends CallAdapter.Factory {
    @Override
    public CallAdapter<?, ?> get() {
        return new CallAdapter<Object, Observable<?>>() {
            @Override
            public Observable<?> adpter(Call<Object> call) {
                System.out.println("rxJava>>");
                Observable.OnSubscribe func=new Observable.OnSubscribe() {
                    @Override
                    public void call(Object o) {

                    }
                };
                return Observable.create(func);
            }
        };
    }
}
