package com.matao.aidl;

import com.matao.aidl.Book;

interface OnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}