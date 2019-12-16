package com.wangyi.client;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.wangyi.arch03_binder.ILoginInterface;

public class MainActivity extends Activity {

    private boolean isStartRemote;
    private ILoginInterface iLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initBindService();
    }

    private void initBindService() {
        Intent intent=new Intent();
        intent.setAction("BinderServer_Action");
        intent.setPackage("com.wangyi.arch03_binder_server");
        bindService(intent,conn,BIND_AUTO_CREATE);
        isStartRemote=true;
    }
    private ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iLogin=ILoginInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isStartRemote) {
            unbindService(conn);
        }
    }

    public void startQQLoginAction(View view) {
        if (iLogin != null) {
            try {
                iLogin.login();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(this, "请先安装QQ", Toast.LENGTH_SHORT).show();
        }
    }
}
