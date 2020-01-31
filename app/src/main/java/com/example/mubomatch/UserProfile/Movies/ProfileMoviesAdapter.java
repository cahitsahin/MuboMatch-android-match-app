package com.example.mubomatch.UserProfile.Movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mubomatch.R;

import java.util.List;

public class ProfileMoviesAdapter extends RecyclerView.Adapter<ProfileMoviesViewHolders> {

    private List<ProfileMoviesObject> moviesList;
    private Context context;


    public ProfileMoviesAdapter(List<ProfileMoviesObject> moviesList, Context context){
        this.moviesList=moviesList;
        this.context=context;
    }

    @NonNull
    @Override
    public ProfileMoviesViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ProfileMoviesViewHolders rcv = new ProfileMoviesViewHolders((layoutView));



        return rcv;
    }

    @Override
    public void onBindViewHolder(ProfileMoviesViewHolders holder, int position) {


        if (!moviesList.get(position).getMovieImageUrl().equals("NoImage")){
            Glide.with(context).load(moviesList.get(position).getMovieImageUrl()).into(holder.mMovieImage);
        }
    }


    @Override
    public int getItemCount() {
        return this.moviesList.size();
    }
}
