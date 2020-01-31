package com.example.mubomatch.Library.LibraryMovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mubomatch.R;

import java.util.List;

public class LibraryMovieAdapter extends RecyclerView.Adapter<LibraryMovieViewHolders> {

    private List<LibraryMovieObject> libraryMoviesList;
    private Context context;


    public LibraryMovieAdapter(List<LibraryMovieObject> libraryMoviesList, Context context){
        this.libraryMoviesList=libraryMoviesList;
        this.context=context;
    }

    @NonNull
    @Override
    public LibraryMovieViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_library_movie,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        LibraryMovieViewHolders rcv = new LibraryMovieViewHolders((layoutView));



        return rcv;
    }

    @Override
    public void onBindViewHolder(LibraryMovieViewHolders holder, int position) {

        holder.mMovieTitle.setText(libraryMoviesList.get(position).getTitle());
        holder.mMovieDirector.setText(libraryMoviesList.get(position).getDirector());
        holder.mMovieWriter.setText(libraryMoviesList.get(position).getWriter());
        holder.mMovieActor.setText(libraryMoviesList.get(position).getActors());
        holder.mMovieGenre.setText(libraryMoviesList.get(position).getGenre());
        holder.mMovieYear.setText(libraryMoviesList.get(position).getYear());
        holder.mMovieId.setText(libraryMoviesList.get(position).getMovieId());

        holder.mMovieUrl.setText(libraryMoviesList.get(position).getPoster());


        if (!libraryMoviesList.get(position).getPoster().equals("NoImage")){
            Glide.with(context).load(libraryMoviesList.get(position).getPoster()).into(holder.mMoviePoster);

        }
    }




    @Override
    public int getItemCount() {
        return this.libraryMoviesList.size();
    }
}
