package com.example.mubomatch.MoviesSearch;

import java.util.ArrayList;
import java.util.List;

public class MovieProcesses {

    private MovieCreator creator;
    private MovieObject movie;
    private List<MovieObject> moviesList;
    private List<String> jsonMovies;

    public List<MovieObject> searchMovie(String query){
        moviesList = new ArrayList<MovieObject>();
        jsonMovies = new ArrayList<String>();
        creator = new MovieCreator();
        query = query.trim();
        query = query.replaceAll(" ", "+");
        jsonMovies = creator.seperateMovies(MovieSearch.searchMovieByTitle(query));

        for (int i = 0; i < jsonMovies.size(); i++) {
            this.moviesList.add(creator.createMovie(jsonMovies.get(i)));
        }
        return this.moviesList;
    }

    public List<Double> calculateMovieScore(MovieObject movie, int numOfMovies, List<Double> score){

        for (int i = 0; i<score.size();i++){
            score.set(i, score.get(i)*numOfMovies);
        }

        for (String genre : movie.getGenre().split(", ")) {
            if (genre.equals("Comedy")){
                score.set(0, score.get(0)+1);
            } else if (genre.equals("Western")){
                score.set(1, score.get(1)+1);
            } else if (genre.equals("Action")){
                score.set(2, score.get(2)+1);
            }else if (genre.equals("Sci-Fi")){
                score.set(3, score.get(3)+1);
            }else if (genre.equals("Crime")){
                score.set(4, score.get(4)+1);
            }else if (genre.equals("Drama")){
                score.set(5, score.get(5)+1);
            }else if (genre.equals("Musical")){
                score.set(6, score.get(6)+1);
            }else if (genre.equals("Romance")){
                score.set(7, score.get(7)+1);
            }else if (genre.equals("Adventure")){
                score.set(8, score.get(8)+1);
            }else if (genre.equals("Fantasy")){
                score.set(9, score.get(9)+1);
            }else if (genre.equals("Mystery")){
                score.set(10, score.get(10)+1);
            }else if (genre.equals("Thriller")){
                score.set(11, score.get(11)+1);
            }else if (genre.equals("Music")){
                score.set(12, score.get(12)+1);
            }else if (genre.equals("Family")){
                score.set(13, score.get(13)+1);
            }else if (genre.equals("Short")){
                score.set(14, score.get(14)+1);
            }else if (genre.equals("Animation")){
                score.set(15, score.get(15)+1);
            }else if (genre.equals("War")){
                score.set(16, score.get(16)+1);
            }else if (genre.equals("Biography")){
                score.set(17, score.get(17)+1);
            }else if (genre.equals("History")){
                score.set(18, score.get(18)+1);
            }else if (genre.equals("Documentary")){
                score.set(19, score.get(19)+1);
            }else if (genre.equals("Sport")){
                score.set(20, score.get(20)+1);
            }else if (genre.equals("Horror")){
                score.set(21, score.get(21)+1);
            }else if (genre.equals("Film-Noir")){
                score.set(22, score.get(22)+1);
            } else if (genre.equals("Reality-TV")){
                score.set(23, score.get(23)+1);
            }
        }

        for (int i = 0; i<score.size();i++){
            score.set(i, score.get(i)/(numOfMovies+1));
        }

        return score;
    }

}
