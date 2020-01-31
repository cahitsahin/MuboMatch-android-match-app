package com.example.mubomatch.Cards;

public class CardsObject {

    private String userID;
    private String userName;
    private String profileImageUrl;
    private Double movieSimilarity;
    private Double musicSimilarity;
    private Double bookSimilarity;
    private Integer age;

    public CardsObject(String userID, String userName,Integer age, String profileImageUrl,Double movieSimilarity,Double bookSimilarity,Double musicSimilarity){
        this.userID = userID;
        this.userName = userName;
        this.profileImageUrl= profileImageUrl;
        this.movieSimilarity=movieSimilarity;
        this.bookSimilarity=bookSimilarity;
        this.musicSimilarity=musicSimilarity;
        this.age=age;

    }

    public String getUserID(){
        return userID;
    }


    public void setUserID(String userID){
        this.userID=userID;
    }


    public String getUserName(){
        return userName;
    }


    public void setUserName(String userName){
        this.userName=userName;
    }

    public Double getMovieSimilarity(){return movieSimilarity;}

    public void setMovieSimilarity(Double movieSimilarity){this.movieSimilarity=movieSimilarity;}

    public Double getBookSimilarity(){return bookSimilarity;}

    public void setBookSimilarity(Double bookSimilarity){this.bookSimilarity=bookSimilarity;}

    public Double getMusicSimilarity(){return musicSimilarity;}

    public void setMusicSimilarity(Double musicSimilarity){this.musicSimilarity=musicSimilarity;}

    public String getProfileImageUrl(){
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl){
        this.profileImageUrl=profileImageUrl;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
