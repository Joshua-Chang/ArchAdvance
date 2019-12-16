package com.wangyi.server;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.wangyi.arch03_binder.ILoginInterface;

public class MainActivity extends Activity {
    private final static String NAME = "Tom";
    private final static String PWD = "163";
    private EditText mNameEt;
    private EditText mPwdEt;
    private ILoginInterface iLogin;
    private boolean isStartRemote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mNameEt = findViewById(R.id.nameEt);
        mPwdEt = findViewById(R.id.pwdEt);
        initBindService();
    }

    private void initBindService() {
        Intent intent=new Intent();
        intent.setAction("BinderClient_Action");
        intent.setPackage("com.wangyi.client");
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

    public void startLogin(View view) {
        final String name = mNameEt.getText().toString();
        final String pwd = mPwdEt.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "账户或密码为空", Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Login");
        dialog.setMessage("Wait...");
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            boolean loginStatus = false;
                            if (NAME.equals(name) && PWD.equals(pwd)) {
                                loginStatus = true;
                                Toast.makeText(MainActivity.this, "QQ登录成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "QQ登录失败", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                            iLogin.loginCallback(loginStatus, name);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

}
