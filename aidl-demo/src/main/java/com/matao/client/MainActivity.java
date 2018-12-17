package com.matao.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.matao.R;
import com.matao.server.ICompute;
import com.matao.server.RemoteService;
import com.matao.server.Stub;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean isConnected = false;
    private ICompute compute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        bindRemoteService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isConnected) {
            unbindService(serviceConnection);
        }
    }

    private void bindRemoteService() {
        Intent intent = new Intent(this, RemoteService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isConnected = true;
            compute = Stub.asInterface(service);
            if (compute != null) {
                int result = compute.add(1, 2);
                Log.d(TAG, "result: " + result);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnected = false;
        }
    };
}
