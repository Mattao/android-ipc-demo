package com.matao.aidl.client;

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

import com.matao.aidl.Book;
import com.matao.aidl.IBookManager;
import com.matao.aidl.R;
import com.matao.aidl.server.RemoteService;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean isConnected = false;
    private IBookManager bookManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.get_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookManager != null) {
                    try {
                        List<Book> books = bookManager.getBooks();
                        Log.d(TAG, Arrays.toString(books.toArray()));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        findViewById(R.id.add_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book("Android In Action", 45.1);
                try {
                    bookManager.addBook(book);
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
            bookManager = IBookManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnected = false;
        }
    };
}
