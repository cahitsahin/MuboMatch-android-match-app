package com.example.mubomatch.BookSearch;
import net.minidev.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class BookObject {

    private String title;
    private String id;
    private String ISBN;
    private String authors;
    private String image;
    private String publishedDate;
    private String categories;
    private String publisher;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(JSONArray authorsList) {
        this.authors = "";
        if (authorsList != null){
            for (int i =0;i<authorsList.size();i++){
                this.authors = this.authors + authorsList.get(i) + ", ";
            }
        }
        this.authors = this.authors.substring(0, this.authors.length()-2);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(JSONArray categories) {
        this.categories = "";
        if (categories != null){
            for (int i = 0;i<categories.size();i++){
                this.categories = this.categories + categories.get(i).toString() + ", ";
            }
            if (this.categories != ""){
                this.categories = this.categories.substring(0, this.categories.length() - 2);
            }
            else this.categories = null;
        }
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
