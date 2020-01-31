package com.example.mubomatch.BookSearch;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookProcesses {



    HashMap<String, Double> bookScore;

    private List<BookObject> booksList;
    private BookCreator crtBook;
    List<String> bkList;





    public List<BookObject> searchBooks(String query){
        this.crtBook = new BookCreator();
        this.booksList = new ArrayList<BookObject>();
        this.bkList = new ArrayList<String>();

        query = query.trim();
        query = query.replaceAll(" ","+");

        String search = BookSearch.searchBookByTitle(query);
        bkList = crtBook.seperateBooks(search);
        for (String book:bkList){
            booksList.add(crtBook.createBook(book));
        }
        return booksList;
    }









}
