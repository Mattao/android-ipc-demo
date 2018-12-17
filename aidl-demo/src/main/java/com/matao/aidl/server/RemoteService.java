package com.matao.aidl.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.matao.aidl.Book;
import com.matao.aidl.IBookManager;
import com.matao.aidl.OnNewBookArrivedListener;

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
        return binder;
    }

    private IBinder binder = new IBookManager.Stub() {
        @Override
        public List<Book> getBooks() throws RemoteException {
            return books;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            if (book == null) return;

            books.add(book);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
        }

        @Override
        public void unregisterListener(OnNewBookArrivedListener listener) throws RemoteException {
            listeners.unregister(listener);
        }
    };
}
