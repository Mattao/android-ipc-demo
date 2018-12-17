package com.matao.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
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
    private RemoteCallbackList<OnNewBookArrivedListener> listeners = new RemoteCallbackList<>();

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

    private final BookManagerStub bookManager = new BookManagerStub() {
        @Override
        public List<Book> getBooks() throws RemoteException {
            SystemClock.sleep(1500);    // mock long-running operation
            return books;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            if (book == null) return;

            books.add(book);
            SystemClock.sleep(1500);    // mock long-running operation
            Log.d(TAG, "books: " + book.toString());

            int size = listeners.beginBroadcast();
            for (int i = 0; i < size; i++) {
                OnNewBookArrivedListener listener = listeners.getBroadcastItem(i);
                if (listener != null) {
                    Log.d(TAG, "onNewBookArrived,notify listener: " + listener);
                    listener.onNewBookArrived(book);
                }
            }
            listeners.finishBroadcast();
        }

        @Override
        public void registerListener(OnNewBookArrivedListener listener) throws RemoteException {
            listeners.register(listener);
            Log.d(TAG, "listeners size: " + listeners.getRegisteredCallbackCount());
        }

        @Override
        public void unregisterListener(OnNewBookArrivedListener listener) throws RemoteException {
            listeners.unregister(listener);
            Log.d(TAG, "listeners size: " + listeners.getRegisteredCallbackCount());
        }
    };
}
