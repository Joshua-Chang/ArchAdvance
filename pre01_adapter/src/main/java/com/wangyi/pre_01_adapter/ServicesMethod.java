package com.wangyi.pre_01_adapter;

public class ServicesMethod<R, T> {
    private CallAdapter<R, T> callAdapter;

    private ServicesMethod(Builder<R, T> builder) {
        callAdapter = builder.callAdapter;
    }

    T adpter(Call<R> call) {
        return callAdapter.adpter(call);
    }

    static final class Builder<R, T> {
        Retrofit retrofit;
        CallAdapter<R, T> callAdapter;

        public Builder(Retrofit retrofit) {
            this.retrofit = retrofit;
        }

        public ServicesMethod build() {
            callAdapter = createCallAdapter();
            return new ServicesMethod<>(this);
        }

        @SuppressWarnings("unchecked")
        private CallAdapter<R, T> createCallAdapter() {
            return (CallAdapter<R, T>) retrofit.callAdapter();
        }
    }
}
