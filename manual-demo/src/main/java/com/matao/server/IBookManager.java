package com.matao.server;

import android.os.IInterface;
import android.os.RemoteException;

import com.matao.Book;

import java.util.List;

/**
 * Created by matao on 2018/12/17
 */
public interface IBookManager extends IInterface {

    String DESCRIPTOR = "com.matao.server.IBookManager";

    List<Book> getBooks() throws RemoteException;

    void addBook(Book book) throws RemoteException;

    void registerListener(OnNewBookArrivedListener listener) throws RemoteException;

    void unregisterListener(OnNewBookArrivedListener listener) throws RemoteException;
}
