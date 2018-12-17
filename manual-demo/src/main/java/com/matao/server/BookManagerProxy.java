package com.matao.server;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.matao.Book;

import java.util.List;

/**
 * Created by matao on 2018/12/17
 */
public class BookManagerProxy implements IBookManager {

    private IBinder remote;

    public BookManagerProxy(IBinder remote) {
        this.remote = remote;
    }

    @Override
    public List<Book> getBooks() throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        List<Book> result;

        try {
            data.writeInterfaceToken(DESCRIPTOR);
            remote.transact(BookManagerStub.TRANSACTION_GET_BOOKS, data, reply, 0);
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
            remote.transact(BookManagerStub.TRANSACTION_ADD_BOOK, data, reply, 0);
            reply.readException();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    @Override
    public void registerListener(OnNewBookArrivedListener listener) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();

        try {
            data.writeInterfaceToken(DESCRIPTOR);
            if (listener != null) {
                data.writeInt(1);
                data.writeStrongBinder(listener.asBinder());
            } else {
                data.writeInt(0);
            }
            remote.transact(BookManagerStub.TRANSACTION_REGISTER_LISTENER, data, reply, 0);
            reply.readException();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    @Override
    public void unregisterListener(OnNewBookArrivedListener listener) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();

        try {
            data.writeInterfaceToken(DESCRIPTOR);
            if (listener != null) {
                data.writeInt(1);
                data.writeStrongBinder(listener.asBinder());
            } else {
                data.writeInt(0);
            }
            remote.transact(BookManagerStub.TRANSACTION_UNREGISTER_LISTENER, data, reply, 0);
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
