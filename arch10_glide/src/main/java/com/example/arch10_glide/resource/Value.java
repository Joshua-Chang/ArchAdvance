package com.example.arch10_glide.resource;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.arch10_glide.Cons;
import com.example.arch10_glide.Tool;

import static com.example.arch10_glide.Cons.TAG;

/**
 * 对Bitmap的封装
 */
public class Value {
    private Bitmap bitmap;
    private int count;
    private String key;

    private static Value value;

//    private Value() {
//    }

    public static Value getInstance() {
        if (null==value) {
            synchronized (Value.class){
                if (null==value) {
                    value=new Value();
                }
            }
        }
        return value;
    }

    public void useAction() {
        Tool.checkNotEmpty(bitmap);
        if (bitmap.isRecycled()) {
            Log.e(TAG, "useAction--isRecycled :" + count);
            return;
        }
        count++;
    }

    private ValueCallback callback;

    public void useFinishAction() {
        count--;
        if (count <= 0 && callback != null) {//不再使用，回调给外部
            callback.valueUseFinishListener(key, this);
        }
        Log.d(TAG, "useAction: 减一 count:" + count);
    }

    public void recyclerBitmap() {
        if (count > 0) {
            Log.e(TAG, "recyclerBitmap--还在使用中 ");
            return;
        }
        if (bitmap.isRecycled()) {
            Log.e(TAG, "recyclerBitmap--isRecycled ");
            return;
        }
        value=null;
        bitmap.recycle();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ValueCallback getCallback() {
        return callback;
    }

    public void setCallback(ValueCallback callback) {
        this.callback = callback;
    }

}
