package com.example.arch10_bitmappool.pool;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import java.util.TreeMap;

import static com.example.arch10_bitmappool.Cons.TAG;

public class BitmapPoolImpl extends LruCache<Integer, Bitmap> implements BitmapPool {
    // 为了筛选出 合适的 Bitmap 容器
    private TreeMap<Integer, String> treeMap = new TreeMap<>();

    public BitmapPoolImpl(int maxSize) {
        super(maxSize);
    }

    @Override
    public void put(Bitmap bitmap) {
        // todo 条件一 bitmap.isMutable() == true;
        if (!bitmap.isMutable()) {
            Log.d(TAG, "put: 条件一 bitmap.isMutable() == true 不满足，不能存入复用池..");
            return;
        }
        // todo 条件二
        // 计算Bitmap的大小
        int bitmapSize = getBitmapSize(bitmap);
        if (bitmapSize >= maxSize()) {
            Log.d(TAG, "put: 条件二 大于了maxSize 不满足，不能存入复用池..");
            return;
        }
        put(bitmapSize, bitmap);
        // 存入 筛选 容器
        treeMap.put(bitmapSize, null); // 10000

        Log.d(TAG, "put: 添加到复用池...");
    }

    // 获取可用复用的Bitmap
    @Override
    public Bitmap get(int width, int height, Bitmap.Config config) {

        /**
         * 每个像素点 8位=1字节
         * ALPHA_8  实际上Android自动做处理的 只有透明度 8位  1个字节
         * w*h*1 占用内存=像素点数*每个像素点占内存大小
         *
         * RGB_565  理论上 实际上Android自动做处理的  R red红色 5， G绿色 6， B蓝色 5   16位 2个字节 没有透明度
         * w*h*2 像素点数*每个像素点占内存大小
         *
         * ARGB_4444 理论上 实际上Android自动做处理 A透明度 4位  R red红色4位   16位 2个字节
         *
         * 质量最高的：
         * ARGB_8888 理论上 实际上Android自动做处理  A 8位 1个字节  ，R 8位 1个字节， G 8位 1个字节， B 8位 1个字节
         *
         * 常用的 ARGB_8888  RGB_565
         */
        // 常用的 4==ARGB_8888  2==RGB_565

//        if (config== Bitmap.Config.ARGB_8888) {
//
//        }
        int getSize = width * height * (config == Bitmap.Config.ARGB_8888 ? 4 : 2);
        Integer key = treeMap.ceilingKey(getSize); // 可以查找到容器里面 和getSize一样大的，也可以 比getSize还要大的
        // 如果treeMap 还没有put，那么一定是 null
        if (key == null) {
            return null; // 没有找到合适的 可以复用的 key
        }

        // key == 10000     getSize==12000

        // 查找容器取出来的key，必须小于 计算出来的 (getSize * 2 ： )
        if (key <= (getSize * 2)) {//！！！！如果找到的可复用内存key，比getSize本身的两倍还大就没必要复用了
            Bitmap remove = remove(key);// 复用池 如果要取出来，肯定要移除，不想给其他地方用了
            Log.d(TAG, "get: 从复用池 里面获取了Bitmap...");
            return remove;
        }
        return null;
    }

    /**
     * 计算Bitmap的大小
     *
     * @param bitmap
     * @return
     */
    private int getBitmapSize(Bitmap bitmap) {
//        return bitmap.getRowBytes()*bitmap.getHeight();//最早
//        return bitmap.getByteCount();//3.0
        return bitmap.getAllocationByteCount();//4.4
    }

    // 元素大小
    @Override
    protected int sizeOf(Integer key, Bitmap value) {
        return getBitmapSize(value);
    }

    // 元素被移除
    @Override
    protected void entryRemoved(boolean evicted, Integer key, Bitmap oldValue, Bitmap newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
    }
}
