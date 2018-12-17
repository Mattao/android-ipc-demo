package com.matao.server;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;

/**
 * Created by matao on 2018/12/17
 */
public abstract class Stub extends Binder implements IComputeManager {

    public static final int TRANSACTION_ADD = IBinder.FIRST_CALL_TRANSACTION;

    public Stub() {
        this.attachInterface(this, DESCRIPTOR);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
                return true;
            case TRANSACTION_ADD:
                data.enforceInterface(DESCRIPTOR);
                int a = data.readInt();
                int b = data.readInt();
                int result = add(a, b);
                reply.writeNoException();
                reply.writeInt(result);
                return true;
            default:
        }
        return super.onTransact(code, data, reply, flags);
    }

    public static IComputeManager asInterface(IBinder binder) {
        if (binder == null) return null;

        IInterface computeInterface = binder.queryLocalInterface(DESCRIPTOR);
        if (computeInterface instanceof IComputeManager) {
            return (IComputeManager) computeInterface;
        }
        return new Proxy(binder);
    }
}
