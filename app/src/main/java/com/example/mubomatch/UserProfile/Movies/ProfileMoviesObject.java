package com.example.mubomatch.UserProfile.Movies;

public class ProfileMoviesObject {

    private String imdbId;
    private String movieImageUrl;


    public ProfileMoviesObject(String movieImageUrl){
        this.movieImageUrl=movieImageUrl;

    }

    public String getImdbId(){
        return imdbId;
    }
    public void setImdbId(String imdbId){
        this.imdbId=imdbId;
    }


    public String getMovieImageUrl(){
        return movieImageUrl;
    }
    public void setMovieImageUrl(String profileImageUrl){
        this.movieImageUrl=profileImageUrl;
    }
}
