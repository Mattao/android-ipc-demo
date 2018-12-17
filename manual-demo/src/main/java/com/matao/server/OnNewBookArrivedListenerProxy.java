package com.matao.server;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.matao.Book;

/**
 * Created by matao on 2018/12/17
 */
public class OnNewBookArrivedListenerProxy implements OnNewBookArrivedListener {

    private IBinder remote;

    public OnNewBookArrivedListenerProxy(IBinder remote) {
        this.remote = remote;
    }

    @Override
    public void onNewBookArrived(Book newBook) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();

        try {
            data.writeInterfaceToken(DESCRIPTOR);
            if (newBook != null) {
                data.writeInt(1);
                newBook.writeToParcel(data, 0);
            } else {
                data.writeInt(0);
            }
            remote.transact(OnNewBookArrivedListenerStub.TRANSACTION_ON_NEW_BOOK_ARRIVED, data, reply, 0);
            reply.readException();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    @Override
    public IBinder asBinder() {
        return remote;
    }
}
