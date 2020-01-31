package com.example.mubomatch.Library.LibraryArtist;

public class LibraryMusicObject {

    private String title;
    private String id;
    private String genre;
    private String image;

    public LibraryMusicObject(String title, String id, String image, String genre) {
        this.title = title;
        this.id = id;
        this.genre = genre;
        this.image = image;

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

