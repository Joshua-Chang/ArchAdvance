package com.wangyi.arch05_plugin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class PluginManager {
    private static PluginManager pluginManager;

    private Context context;

    public static PluginManager getInstance(Context context) {
        if (pluginManager == null) {
            synchronized (PluginManager.class) {
                if (pluginManager == null) {
                    pluginManager = new PluginManager(context);
                }
            }
        }
        return pluginManager;
    }

    public PluginManager(Context context) {
        this.context = context;
    }

    private DexClassLoader dexClassLoader;
    private Resources resources;
    /**
     * （2.1 Activity class，  2.2layout）
     * 加载插件
     */
    public void loadPlugin() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "p.apk");
        if (!file.exists()) return;
        String pluginPath = file.getAbsolutePath();

        /**
         *   下面是加载插件里面的 class
         */

        // dexClassLoader需要一个缓存目录   /data/data/当前应用的包名/pDir
        File fileDir = context.getDir("pDir", Context.MODE_PRIVATE);

        // Activity class
        dexClassLoader = new DexClassLoader(pluginPath, fileDir.getAbsolutePath(), null, context.getClassLoader());

        try {
        /**
         *   下面是加载插件里面的layout
         */
        // 加载资源
        AssetManager assetManager = AssetManager.class.newInstance();

        // 我们要执行此方法，为了把插件包的路径 添加进去
        // public final int addAssetPath(String path)
        Method addAssetPathMethod = assetManager.getClass().getMethod("addAssetPath", String.class); // 他是类类型 Class
        addAssetPathMethod.invoke(assetManager, pluginPath); // 插件包的路径   pluginPaht

        Resources r = context.getResources(); // 宿主的资源配置信息

        // 特殊的 Resources，加载插件里面的资源的 Resources
        resources = new Resources(assetManager, r.getDisplayMetrics(), r.getConfiguration()); // 参数2 3  资源配置信息
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public Resources getResources() {
        return resources;
    }
}
