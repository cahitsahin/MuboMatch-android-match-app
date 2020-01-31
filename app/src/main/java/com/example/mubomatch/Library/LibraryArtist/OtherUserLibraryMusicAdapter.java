package com.example.mubomatch.Library.LibraryArtist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mubomatch.R;

import java.util.List;

public class OtherUserLibraryMusicAdapter extends RecyclerView.Adapter<OtherUserLibraryMusicViewHolders> {

    private List<LibraryMusicObject> libraryMusicList;
    private Context context;


    public OtherUserLibraryMusicAdapter(List<LibraryMusicObject> libraryMusicList, Context context){
        this.libraryMusicList=libraryMusicList;
        this.context=context;
    }

    @NonNull
    @Override
    public OtherUserLibraryMusicViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_library_music,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        OtherUserLibraryMusicViewHolders rcv = new OtherUserLibraryMusicViewHolders((layoutView));



        return rcv;
    }

    @Override
    public void onBindViewHolder(OtherUserLibraryMusicViewHolders holder, int position) {

        holder.mArtistTitle.setText(libraryMusicList.get(position).getTitle());
        holder.mArtistGenre.setText(libraryMusicList.get(position).getGenre());
        holder.mArtistId.setText(libraryMusicList.get(position).getId());
        holder.mArtistUrl.setText(libraryMusicList.get(position).getImage());

        if (!libraryMusicList.get(position).getImage().equals("NoImage")){
            Glide.with(context).load(libraryMusicList.get(position).getImage()).into(holder.mArtistPoster);
        }
    }




    @Override
    public int getItemCount() {
        return this.libraryMusicList.size();
    }
}
