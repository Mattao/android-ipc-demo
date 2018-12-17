package com.matao.server;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * Created by matao on 2018/12/17
 */
public class Proxy implements IComputeManager {

    private IBinder remote;

    public Proxy(IBinder remote) {
        this.remote = remote;
    }

    @Override
    public int add(int a, int b) {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        int result = 0;

        try {
            data.writeInterfaceToken(DESCRIPTOR);
            data.writeInt(a);
            data.writeInt(b);
            remote.transact(Stub.TRANSACTION_ADD, data, reply, 0);
            reply.readException();
            result = reply.readInt();
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            reply.recycle();
            data.recycle();
        }

        return result;
    }

    @Override
    public IBinder asBinder() {
        return remote;
    }
}
