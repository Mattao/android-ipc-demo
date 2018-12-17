package com.matao.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by matao on 2018/12/17
 */
public class RemoteService extends Service {

    private final Stub computeImpl = new Stub() {
        @Override
        public int add(int a, int b) {
            return a + b;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return computeImpl;
    }
}
