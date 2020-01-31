package com.example.mubomatch.UserProfile.Books;

public class ProfileBooksObject {

    private String bookImageUrl;


    public ProfileBooksObject(String bookImageUrl){
        this.bookImageUrl=bookImageUrl;

    }




    public String getBookImageUrl(){
        return bookImageUrl;
    }
    public void setBookImageUrl(String bookImageUrl){
        this.bookImageUrl=bookImageUrl;
    }
}
