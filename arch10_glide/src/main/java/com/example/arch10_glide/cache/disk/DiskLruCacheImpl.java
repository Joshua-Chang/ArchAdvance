package com.example.arch10_glide.cache.disk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.arch10_glide.resource.Value;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DiskLruCacheImpl {
    // SD/disk_lru_cache_dir/ac037ea49e34257dc5577d1796bb137dbaddc0e42a9dff051beee8ea457a4668
    private final String DISKLRU_CACHE_DIR = "disk_lru_cache_dir"; // 磁盘缓存的的目录

    private final int APP_VERSION = 1; // 我们的版本号，一旦修改这个版本号，之前的缓存失效
    private final int VALUE_COUNT = 1; // 通常情况下都是1
    private final long MAX_SIZE = 1024 * 1024 * 10; // 以后修改成 使用者可以设置的

    private DiskLruCache diskLruCache;

    public DiskLruCacheImpl() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + DISKLRU_CACHE_DIR);
        try {
            diskLruCache = DiskLruCache.open(file, APP_VERSION, VALUE_COUNT, MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void put(String k, Value v) {
        DiskLruCache.Editor edit = null;
        OutputStream outputStream = null;
        try {
            edit = diskLruCache.edit(k);
            outputStream = edit.newOutputStream(0);//必须小于VALUE_COUNT
            Bitmap bitmap = v.getBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                edit.abort();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                edit.commit();
                diskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Value get(String k) {
        InputStream inputStream = null;
        try {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(k);
            if (snapshot != null) {
                Value value = Value.getInstance();
                inputStream = snapshot.getInputStream(0);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                value.setBitmap(bitmap);
                value.setKey(k);
                return value;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
