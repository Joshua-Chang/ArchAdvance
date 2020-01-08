package com.example.arch10_glide.cache;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import com.example.arch10_glide.resource.Value;

public class MemoryCache extends LruCache<String, Value> {
    private MemoryCacheCallback memoryCacheCallback;

    public void setMemoryCacheCallback(MemoryCacheCallback memoryCacheCallback) {
        this.memoryCacheCallback = memoryCacheCallback;
    }

    private boolean shoudonRemove;

    // TODO 手动移除
    public Value shoudonRemove(String key) {
        shoudonRemove = true;
        Value value = remove(key);
        shoudonRemove = false;  // !shoudonRemove == 被动的
        return value;
    }

    public MemoryCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, Value value) {
        Bitmap bitmap = value.getBitmap();

        // 最开始的时候
        // int result = bitmap.getRowBytes() * bitmap.getHeight();

        // API 12  3.0
        // result = bitmap.getByteCount(); // 在bitmap内存复用上有区别 （所属的）

        // API 19 4.4
        // result = bitmap.getAllocationByteCount(); // 在bitmap内存复用上有区别 （整个的）

        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        }

        return bitmap.getByteCount();
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, Value oldValue, Value newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
        if (memoryCacheCallback != null&&!shoudonRemove) {
            memoryCacheCallback.entryRemovedMemoryCache(key, oldValue);
        }
    }
}
