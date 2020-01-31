package com.example.mubomatch.MusicSearch;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.List;

public class ArtistCreator {

    private ArtistObject artist;

    public ArtistObject createArtist(String rawData, String rawArtist){
        try {
            this.artist = new ArtistObject();
            this.artist.setName(JsonPath.parse(rawData).read(rawArtist + ".name").toString());
            this.artist.setId(JsonPath.parse(rawData).read(rawArtist + ".id").toString());
            JSONArray genres = JsonPath.parse(rawData).read(rawArtist + ".genres");
            this.artist.setGenres(genres);
            this.artist.setImage(JsonPath.parse(rawData).read(rawArtist + ".images[0].url").toString());
        }
        catch (PathNotFoundException e){
        }
        return this.artist;
    }
}

