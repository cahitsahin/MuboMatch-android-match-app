package com.example.mubomatch.MoviesSearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mubomatch.R;

import java.util.List;

public class SearchMoviesAdapter extends RecyclerView.Adapter<SearchMoviesViewHolders> {

    private List<MovieObject> movieList;
    private Context context;


    public SearchMoviesAdapter(List<MovieObject> moviesList, Context context){
        this.movieList=moviesList;
        this.context=context;
    }

    @NonNull
    @Override
    public SearchMoviesViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_movies,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        SearchMoviesViewHolders rcv = new SearchMoviesViewHolders((layoutView));



        return rcv;
    }

    @Override
    public void onBindViewHolder(SearchMoviesViewHolders holder, int position) {




        holder.mMovieTitle.setText(movieList.get(position).getTitle());

        if (movieList.get(position).getYear() != null){
            holder.mMovieYear.setText(movieList.get(position).getYear());
        }

        if (!movieList.get(position).getDirector().equals("N/A")){
            holder.mMovieDirector.setText(movieList.get(position).getDirector());
        }

        if (!movieList.get(position).getWriter().equals("N/A")){
            holder.mMovieWriter.setText(movieList.get(position).getWriter());
        }
        if (!movieList.get(position).getActors().equals("N/A")){
            holder.mMovieActor.setText(movieList.get(position).getActors());
        }


        if (!movieList.get(position).getPoster().equals("NoImage")){
            holder.mMovieUrl.setText(movieList.get(position).getPoster());
            Glide.with(context).load(movieList.get(position).getPoster()).into(holder.mMoviePoster);
        }

        holder.mMovieGenre.setText(movieList.get(position).getGenre());
        holder.mMovieId.setText(movieList.get(position).getImdbID());



    }




    @Override
    public int getItemCount() {
        return this.movieList.size();
    }
}
