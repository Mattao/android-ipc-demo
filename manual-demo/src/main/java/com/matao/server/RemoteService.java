package com.matao.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.matao.Book;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by matao on 2018/12/17
 */
public class RemoteService extends Service {

    private static final String TAG = RemoteService.class.getSimpleName();
    private List<Book> books = new CopyOnWriteArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Book book = new Book("Thinking in Java", 78);
        books.add(book);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return bookManager;
    }

    private final Stub bookManager = new Stub() {
        @Override
        public List<Book> getBooks() throws RemoteException {
            return books;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            if (book == null) return;

            books.add(book);
            Log.d(TAG, "books: " + book.toString());
        }
    };
}
