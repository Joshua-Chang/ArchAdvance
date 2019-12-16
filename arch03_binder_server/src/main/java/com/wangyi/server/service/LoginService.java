package com.wangyi.server.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.wangyi.arch03_binder.ILoginInterface;
import com.wangyi.server.MainActivity;

public class LoginService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ILoginInterface.Stub() {
            @Override
            public void login() throws RemoteException {
                Log.e("xxx","server_LoginService");
                serviceStartActivity();
            }

            @Override
            public void loginCallback(boolean loginStatus, String loginUser) throws RemoteException {

            }
        };
    }

    private void serviceStartActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
