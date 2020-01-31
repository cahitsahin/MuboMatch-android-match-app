package com.example.mubomatch.MoviesSearch;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MovieCreator {
    private MovieObject movie;
    private List<String> moviesList;

    public MovieObject createMovie(String movie) {
        //Creating a movie object.
        this.movie = new MovieObject();
        try {
            this.movie.setTitle(JsonPath.parse(movie).read("$.Title").toString());
            this.movie.setYear(JsonPath.parse(movie).read("$.Year").toString());
            this.movie.setRated(JsonPath.parse(movie).read("$.Rated").toString());
            this.movie.setReleased(JsonPath.parse(movie).read("$.Released").toString());
            this.movie.setRunTime(JsonPath.parse(movie).read("$.Runtime").toString());
            this.movie.setGenre(JsonPath.parse(movie).read("$.Genre").toString());
            this.movie.setDirector(JsonPath.parse(movie).read("$.Director").toString());
            this.movie.setWriter(JsonPath.parse(movie).read("$.Writer").toString());
            this.movie.setActors(JsonPath.parse(movie).read("$.Actors").toString());
            this.movie.setPlot(JsonPath.parse(movie).read("$.Plot").toString());
            this.movie.setLanguage(JsonPath.parse(movie).read("$.Language").toString());
            this.movie.setCountry(JsonPath.parse(movie).read("$.Country").toString());
            this.movie.setAwards(JsonPath.parse(movie).read("$.Awards").toString());
            this.movie.setPoster(JsonPath.parse(movie).read("$.Poster").toString());
            JSONArray ratings = JsonPath.parse(movie).read("$..Ratings");
            this.movie.setRatings(ratings);
            this.movie.setMetascore(JsonPath.parse(movie).read("$.Metascore").toString());
            this.movie.setImdbRating(JsonPath.parse(movie).read("$.imdbRating").toString());
            this.movie.setImdbVotes(JsonPath.parse(movie).read("$.imdbVotes").toString());
            this.movie.setImdbID(JsonPath.parse(movie).read("$.imdbID").toString());
            this.movie.setType(JsonPath.parse(movie).read("$.Type").toString());
            this.movie.setDVD(JsonPath.parse(movie).read("$.DVD").toString());
            this.movie.setBoxOffice(JsonPath.parse(movie).read("$.BoxOffice").toString());
            this.movie.setProduction(JsonPath.parse(movie).read("$.Production").toString());
            this.movie.setWebsite(JsonPath.parse(movie).read("$.Website").toString());
        } catch (com.jayway.jsonpath.PathNotFoundException e) {
        }
        return this.movie;
    }
    //Seperate movies olmadan yapabilir miyiz uygulamayÃ„Â± yaparken tekrar bak.
    public List<String> seperateMovies(String moviesInfo) {
        moviesList = new ArrayList<String>();
        // Seperating every movie that coming from query by their ID.
        List<String> IDs = JsonPath.parse(moviesInfo).read("$..imdbID");

        for (int i = 0; i < IDs.size(); i++) {
            this.moviesList.add(MovieSearch.searchMovieByID(IDs.get(i)));
        }
        return this.moviesList;
    }
}

