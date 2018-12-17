package com.matao.aidl;

import com.matao.aidl.Book;

interface IBookManager {
    List<Book> getBooks();

    void addBook(in Book book);
}