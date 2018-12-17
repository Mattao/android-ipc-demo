package com.matao.aidl;

import com.matao.aidl.Book;
import com.matao.aidl.OnNewBookArrivedListener;

interface IBookManager {
    List<Book> getBooks();
    void addBook(in Book book);
    void registerListener(OnNewBookArrivedListener listener);
    void unregisterListener(OnNewBookArrivedListener listener);
}