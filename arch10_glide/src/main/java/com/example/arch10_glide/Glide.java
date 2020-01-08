package com.example.arch10_glide;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;

public class Glide {
    RequestManagerRetriever retriever;

    public Glide(RequestManagerRetriever retriever) {
        this.retriever = retriever;
    }

    public static RequestManager with(FragmentActivity activity) {
        return getRetriever(activity).get(activity);
    }

    public static RequestManager with(Activity activity) {
        return getRetriever(activity).get(activity);
    }

    public static RequestManager with(Context context) {
        return getRetriever(context).get(context);
    }

    public RequestManagerRetriever getRetriever() {
        return retriever;
    }

    private static RequestManagerRetriever getRetriever(Context context) {
        return Glide.get(context).getRetriever();
    }

    public static Glide get(Context context){
        return new GlideBuilder().build();
    }


    private static class GlideBuilder {
        public Glide build() {
            return new Glide(new RequestManagerRetriever());
        }
    }
}
