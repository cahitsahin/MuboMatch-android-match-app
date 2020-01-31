package com.example.mubomatch.Library.LibraryMovie;


import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

import java.util.List;


public class LibraryMovieObject {
    private String title;
    private String year;
    private String genre;
    private String director;
    private String writer;
    private String actors;
    private String poster;
    private String movieId;

    public LibraryMovieObject(String title,String year,String genre,String director,String writer,String actors,String poster,String movieId) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.director = director;
        this.writer = writer;
        this.actors = actors;
        this.poster = poster;
        this.movieId = movieId;

    }


        public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMovieId() {
        return this.movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return this.director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return this.writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return this.actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }


    public String getPoster() {
        if (this.poster.equals("N/A")) {
            return " --NO POSTER DATA FOUND-- ";
        }
        return this.poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
