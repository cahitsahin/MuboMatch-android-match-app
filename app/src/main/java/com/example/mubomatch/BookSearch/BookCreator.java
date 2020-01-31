package com.example.mubomatch.BookSearch;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookCreator {
    private BookObject book;
    private List<String> booksList;

    public BookObject createBook(String book) {
            try {
                this.book = new BookObject();
                this.book.setId(JsonPath.parse(book).read("$.id").toString());
                this.book.setTitle(JsonPath.parse(book).read("$.volumeInfo.title").toString());
                JSONArray categories = JsonPath.parse(book).read("$.volumeInfo.categories");
                this.book.setCategories(categories);
            }catch (PathNotFoundException e){
            }
            try{
                JSONArray authors = JsonPath.parse(book).read("$.volumeInfo.authors");
                this.book.setAuthors(authors);
                this.book.setImage(JsonPath.parse(book).read("$.volumeInfo.imageLinks.thumbnail").toString());
                this.book.setPublishedDate(JsonPath.parse(book).read("$.volumeInfo.publishedDate").toString());
                this.book.setPublisher(JsonPath.parse(book).read("$.volumeInfo.publisher").toString());
                this.book.setISBN(JsonPath.parse(book).read("$.volumeInfo.industryIdentifiers[1].identifier").toString());

            } catch (PathNotFoundException e) {
            }
            return this.book;
    }

    public List<String> seperateBooks(String booksInfo){
        booksList = new ArrayList<String>();
        for (int i = 0; i<10;i++){

            String bkk = new JSONObject(JsonPath.<Map<String, ?>>read(booksInfo,"$.items.[" + i +"]")).toString();
            booksList.add(bkk);


        }

        return this.booksList;
    }

}
