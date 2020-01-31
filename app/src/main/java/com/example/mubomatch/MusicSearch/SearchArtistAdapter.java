package com.example.mubomatch.MusicSearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mubomatch.R;

import java.util.List;

public class SearchArtistAdapter extends RecyclerView.Adapter<SearchArtistViewHolders> {

    private List<ArtistObject> artistList;
    private Context context;


    public SearchArtistAdapter(List<ArtistObject> artistList, Context context){
        this.artistList=artistList;
        this.context=context;
    }

    @NonNull
    @Override
    public SearchArtistViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_music,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        SearchArtistViewHolders rcv = new SearchArtistViewHolders((layoutView));



        return rcv;
    }

    @Override
    public void onBindViewHolder(SearchArtistViewHolders holder, int position) {

        if (artistList.get(position).getImage() != null){
            Glide.with(context).load(artistList.get(position).getImage()).into(holder.mArtistPoster);
            holder.mArtistUrl.setText(artistList.get(position).getImage());
        }
        holder.mArtistTitle.setText(artistList.get(position).getName());
        holder.mArtistGenre.setText(artistList.get(position).getGenres());

        holder.mArtistId.setText(artistList.get(position).getId());



    }




    @Override
    public int getItemCount() {
        return this.artistList.size();
    }
}
