package com.example.mubomatch.UserProfile.Artist;

public class ProfileArtistsObject {

    private String artistId;
    private String artistImageUrl;


    public ProfileArtistsObject(String artistImageUrl){
        this.artistImageUrl=artistImageUrl;

    }

    public String getArtistImageUrl(){
        return artistImageUrl;
    }
    public void setArtistImageUrl(String artistImageUrl){
        this.artistImageUrl=artistImageUrl;
    }
}
