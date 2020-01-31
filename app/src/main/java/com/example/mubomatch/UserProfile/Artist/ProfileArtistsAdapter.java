package com.example.mubomatch.UserProfile.Artist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mubomatch.R;

import java.util.List;

public class ProfileArtistsAdapter extends RecyclerView.Adapter<ProfileArtistsViewHolders> {

    private List<ProfileArtistsObject> artistList;
    private Context context;


    public ProfileArtistsAdapter(List<ProfileArtistsObject> artistList, Context context){
        this.artistList=artistList;
        this.context=context;
    }

    @NonNull
    @Override
    public ProfileArtistsViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ProfileArtistsViewHolders rcv = new ProfileArtistsViewHolders((layoutView));



        return rcv;
    }

    @Override
    public void onBindViewHolder(ProfileArtistsViewHolders holder, int position) {


        if (!artistList.get(position).getArtistImageUrl().equals("NoImage")) {
            Glide.with(context).load(artistList.get(position).getArtistImageUrl()).into(holder.mArtistImage);
        }
    }


    @Override
    public int getItemCount() {
        return this.artistList.size();
    }
}
