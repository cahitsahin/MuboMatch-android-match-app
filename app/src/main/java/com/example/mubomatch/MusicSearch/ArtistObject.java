package com.example.mubomatch.MusicSearch;

import net.minidev.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

public class ArtistObject {

    private String name;
    private String id;
    private String genres;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(JSONArray genres) {
        this.genres = "";

        for (int i = 0;i<genres.size();i++){
            this.genres = this.genres + genres.get(i).toString() + ", ";
        }
        if (this.genres != ""){
            this.genres = this.genres.substring(0, this.genres.length() - 2);
        }
        else this.genres = null;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

