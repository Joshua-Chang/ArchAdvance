package com.wangyi.arch05_plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

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

    //反射系统源码解析apk

    /**
     * 反射执行此方法，得到返回的package
     * public Package parsePackage(File packageFile, int flags, boolean useCaches)
     */
    public void parserApkAction() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "p.apk");
        if (!file.exists()) return;
        String pluginPath = file.getAbsolutePath();


        Class<?> mPackageParserClass = null;
        try {
            mPackageParserClass = Class.forName("android.content.pm.pa.PackageParser");
            Object mPackageParser = mPackageParserClass.newInstance();
            Method parserClassMethod = mPackageParserClass.getMethod("parsePackage", File.class, int.class);
            Object mPackage = parserClassMethod.invoke(mPackageParser, file, PackageManager.GET_ACTIVITIES);


            Field parserClassField = mPackageParserClass.getDeclaredField("receivers");
            ArrayList arrayList = (ArrayList) parserClassField.get(mPackageParser);
            // 此Activity 不是组件的Activity，是PackageParser里面的内部类
            for (Object mActivity : arrayList) {
                // mActivity --> <receiver android:name=".StaticReceiver">
                // 获取 <intent-filter>    intents== 很多 Intent-Filter
                // 通过反射拿到 intents

                // 获取 <intent-filter>    intents== 很多 Intent-Filter
                // 通过反射拿到 Component内部类里的intents
                Class mComponentClass = Class.forName("android.content.pm.PackageParser$Component");//内部类Component
                Field intentsField = mComponentClass.getDeclaredField("intents");
                ArrayList<IntentFilter> intents = (ArrayList) intentsField.get(mActivity); // intents 所属的对象是mActivity

                for (IntentFilter intentFilter : intents) {
//                    context.registerReceiver(,intentFilter);
                }
                // 我们还有一个任务，就是要拿到 android:name=".StaticReceiver"
                // activityInfo.name; == android:name=".StaticReceiver"
                // 分析源码 如何 拿到 ActivityInfo
                //generateActivityInfo返回ActivityInfo

                Class mPackageUserState = Class.forName("android.content.pm.PackageUserState");

                //UserHandle.getCallingUserId() -->获取userId
                Class mUserHandle = Class.forName("android.os.UserHandle");
                int userId = (int) mUserHandle.getMethod("getCallingUserId").invoke(null);


                /**
                 * 执行此方法，就能拿到 ActivityInfo
                 * public static final ActivityInfo generateActivityInfo(Activity a, int flags,
                 *             PackageUserState state, int userId)
                 */
                Method  generateActivityInfoMethod = mPackageParserClass.getDeclaredMethod("generateActivityInfo", mActivity.getClass()
                        , int.class, mPackageUserState, int.class);
                generateActivityInfoMethod.setAccessible(true);
                // 执行此方法，拿到ActivityInfo 静态方法不需要执行对象
                ActivityInfo mActivityInfo = (ActivityInfo) generateActivityInfoMethod.invoke(null,mActivity, 0, mPackageUserState.newInstance(), userId);
                String receiverClassName = mActivityInfo.name; // com.netease.plugin_package.StaticReceiver

                Class mStaticReceiverClass = getDexClassLoader().loadClass(receiverClassName);

                BroadcastReceiver broadcastReceiver = (BroadcastReceiver) mStaticReceiverClass.newInstance();

                for (IntentFilter intentFilter : intents) {

                    // 注册广播
                    context.registerReceiver(broadcastReceiver, intentFilter);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
