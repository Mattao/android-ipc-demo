package com.matao.server;

import android.os.IInterface;
import android.os.RemoteException;

import com.matao.Book;

/**
 * Created by matao on 2018/12/17
 */
public interface OnNewBookArrivedListener extends IInterface {
    String DESCRIPTOR = "com.matao.server.OnNewBookArrivedListener";

    void onNewBookArrived(Book newBook) throws RemoteException;
}
