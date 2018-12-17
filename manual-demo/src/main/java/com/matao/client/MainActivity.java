package com.matao.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.matao.Book;
import com.matao.R;
import com.matao.server.BookManagerStub;
import com.matao.server.IBookManager;
import com.matao.server.OnNewBookArrivedListener;
import com.matao.server.OnNewBookArrivedListenerStub;
import com.matao.server.RemoteService;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean isConnected = false;
    private IBookManager bookManager;
    private OnNewBookArrivedListener listener = new OnNewBookArrivedListenerStub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            Log.d(TAG, "new book arrived callback: " + newBook.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.get_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookManager != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                List<Book> books = bookManager.getBooks();
                                Log.d(TAG, Arrays.toString(books.toArray()));
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
        findViewById(R.id.add_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Book book = new Book("Android In Action", 45.1);
                        try {
                            bookManager.addBook(book);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        findViewById(R.id.add_listener_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    bookManager.registerListener(listener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.remove_listener_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    bookManager.unregisterListener(listener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        bindRemoteService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isConnected) {
            unbindService(serviceConnection);
        }
    }

    private void bindRemoteService() {
        Intent intent = new Intent(this, RemoteService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isConnected = true;
            bookManager = BookManagerStub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnected = false;
        }
    };
}
