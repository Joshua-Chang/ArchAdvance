package com.wangyi.pre_01_adapter;

public class ExecutorCallAdapterFactory extends CallAdapter.Factory{
    @Override
    public CallAdapter<?, ?> get() {
        return new CallAdapter<Object, Call<?>>() {
            @Override
            public Call<?> adpter(Call<Object> call) {
                System.out.println("default>>");
                return new ExecutorCallbackCall<>();
            }
        };
    }
    static final class ExecutorCallbackCall<T>implements Call<T>{

        @Override
        public void enqueue() {

        }
    }
}
