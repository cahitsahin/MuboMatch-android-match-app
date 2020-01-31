package com.example.mubomatch.MusicSearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ArtistProcesses {



    private List<ArtistObject> artistList;
    private ArtistCreator createArtist;




    public List<ArtistObject> searchArtists(String query){
        this.createArtist = new ArtistCreator();
        this.artistList = new ArrayList<ArtistObject>();

        query = query.trim();
        query = query.replaceAll(" ","+");

        String search = ArtistSearch.searchArtist(query);

        for (int i=0;i<20;i++){
            ArtistObject artist = createArtist.createArtist(search, "$.artists.items["+ i +"]");
            if (artist.getName() == null) break;
            this.artistList.add(artist);
        }

        return artistList;
    }


}
