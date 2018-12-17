package com.matao.server;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.matao.Book;

import java.util.List;

/**
 * Created by matao on 2018/12/17
 */
public class Proxy implements IBookManager {

    private IBinder remote;

    public Proxy(IBinder remote) {
        this.remote = remote;
    }

    @Override
    public List<Book> getBooks() throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        List<Book> result;

        try {
            data.writeInterfaceToken(DESCRIPTOR);
            remote.transact(Stub.TRANSACTION_GET_BOOKS, data, reply, 0);
            reply.readException();
            result = reply.createTypedArrayList(Book.CREATOR);
        } finally {
            data.recycle();
            reply.recycle();
        }
        return result;
    }

    @Override
    public void addBook(Book book) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();

        try {
            data.writeInterfaceToken(DESCRIPTOR);
            if (book != null) {
                data.writeInt(1);
                book.writeToParcel(data, 0);
            } else {
                data.writeInt(0);
            }
            remote.transact(Stub.TRANSACTION_ADD_BOOK, data, reply, 0);
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
