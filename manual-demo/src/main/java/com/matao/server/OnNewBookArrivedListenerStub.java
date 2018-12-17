package com.matao.server;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;

import com.matao.Book;

/**
 * Created by matao on 2018/12/17
 */
public abstract class OnNewBookArrivedListenerStub extends Binder implements OnNewBookArrivedListener {

    public static final int TRANSACTION_ON_NEW_BOOK_ARRIVED = IBinder.FIRST_CALL_TRANSACTION;

    public OnNewBookArrivedListenerStub() {
        this.attachInterface(this, DESCRIPTOR);
    }

    public static OnNewBookArrivedListener asInterface(IBinder binder) {
        if (binder == null) return null;

        IInterface iInterface = binder.queryLocalInterface(DESCRIPTOR);
        if (iInterface instanceof OnNewBookArrivedListener) {
            return (OnNewBookArrivedListener) iInterface;
        }
        return new OnNewBookArrivedListenerProxy(binder);
    }

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION:
                reply.writeInterfaceToken(DESCRIPTOR);
                return true;
            case TRANSACTION_ON_NEW_BOOK_ARRIVED:
                data.enforceInterface(DESCRIPTOR);
                Book book = null;
                if (data.readInt() != 0) {
                    book = Book.CREATOR.createFromParcel(data);
                }
                onNewBookArrived(book);
                reply.writeNoException();
                return true;
            default:
                return super.onTransact(code, data, reply, flags);
        }
    }

    @Override
    public IBinder asBinder() {
        return this;
    }
}
