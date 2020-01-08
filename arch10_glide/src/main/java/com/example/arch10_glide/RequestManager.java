package com.example.arch10_glide;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.arch10_glide.fragment.ActivityFragmentManager;
import com.example.arch10_glide.fragment.FragmentActivityFragmentManager;

import static com.example.arch10_glide.Cons.TAG;

public class RequestManager {
    private final String FRAGMENT_ACTIVITY_NAME = "Fragment_Activity_NAME";
    private final String ACTIVITY_NAME = "activity_name";
    private final int NEXT_HANDLER_MSG = 995465;

    private Context requestManagerContext;
    private static RequestTargetEngine requestTargetEngine;
    private FragmentActivity fragmentActivity;

    // 构造代码块，不用再所有的构造方法里面去实例化了，统一的去写
    {
        if (null==requestTargetEngine ) {
            requestTargetEngine = new RequestTargetEngine();
        }
    }
    /**
     * 可以管理生命周期
     *
     * @param activity
     */
    public RequestManager(FragmentActivity activity) {
        requestManagerContext=activity;
        this.fragmentActivity = activity;
        FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_ACTIVITY_NAME);
        if (fragment == null) {
            fragment=new FragmentActivityFragmentManager(requestTargetEngine);
            supportFragmentManager.beginTransaction()
                    .add(fragment,FRAGMENT_ACTIVITY_NAME)
                    .commitAllowingStateLoss();
        }
        // 发送一次Handler
        mHandler.sendEmptyMessage(NEXT_HANDLER_MSG);

        Fragment fragment2 = supportFragmentManager.findFragmentByTag(FRAGMENT_ACTIVITY_NAME);
        Log.d(TAG, "RequestManager: fragment2" + fragment2); // null ： @3 还在排队中，还没有消费

    }

    /**
     * 可以管理生命周期--activity有生命周期方法
     *
     * @param activity
     */
    public RequestManager(Activity activity) {
        requestManagerContext=activity;
        android.app.FragmentManager fragmentManager = activity.getFragmentManager();
        android.app.Fragment fragment = fragmentManager.findFragmentByTag(ACTIVITY_NAME);
        if (fragment == null) {
            fragment=new ActivityFragmentManager(requestTargetEngine);
            fragmentManager.beginTransaction()
                    .add(fragment,ACTIVITY_NAME)
                    .commitAllowingStateLoss();
        }
        // 发送一次Handler
        mHandler.sendEmptyMessage(NEXT_HANDLER_MSG);

        android.app.Fragment fragment2 = fragmentManager.findFragmentByTag(ACTIVITY_NAME);
        Log.d(TAG, "RequestManager: fragment2" + fragment2); // null ： @3 还在排队中，还没有消费

    }

    /**
     * 无法管理生命周期
     *
     * @param context
     */
    public RequestManager(Context context) {
        requestManagerContext=context;

    }

    /**
     * load 拿到要显示的图片路径
     * @param path
     * @return
     */
    public RequestTargetEngine load(String path){
        // 移除Handler
        mHandler.removeMessages(NEXT_HANDLER_MSG);
        // 把值传递给 资源加载引擎
        requestTargetEngine.loadValueInitAction(path, requestManagerContext);
        return requestTargetEngine;
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
//            Fragment fragment2 = ((FragmentActivity)requestManagerContext).getSupportFragmentManager().findFragmentByTag(FRAGMENT_ACTIVITY_NAME);
            Fragment fragment2 = fragmentActivity.getSupportFragmentManager().findFragmentByTag(FRAGMENT_ACTIVITY_NAME);

            Log.d(TAG, "Handler: fragment2" + fragment2); // 有值 ： 不在排队中，所以有值
            return false;
        }
    });
}
