package com.example.mubomatch.Matches.MatchesMessage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mubomatch.R;

import java.util.List;

public class MatchesMessageAdapter extends RecyclerView.Adapter<MatchesMessageViewHolders> {

    private List<MatchesMessageObject> matchesList;
    private Context context;


    public MatchesMessageAdapter(List<MatchesMessageObject> matchesList, Context context){
        this.matchesList=matchesList;
        this.context=context;
    }

    @NonNull
    @Override
    public MatchesMessageViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matches_message,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        layoutView.setLayoutParams(lp);
        MatchesMessageViewHolders rcv = new MatchesMessageViewHolders((layoutView));



        return rcv;
    }

    @Override
    public void onBindViewHolder(MatchesMessageViewHolders holder, int position) {
        holder.mMatchMId.setText(matchesList.get(position).getUserId());
        holder.mMatchMName.setText(matchesList.get(position).getName());
        holder.mMatchMPpUrl.setText(matchesList.get(position).getProfileImageUrl());
        holder.mMatchLastM.setText(matchesList.get(position).getLastMessage());
        if(!matchesList.get(position).getProfileImageUrl().equals("Default")) {
            Glide.with(context).load(matchesList.get(position).getProfileImageUrl()).into(holder.mMatchMImage);
        }

    }

    @Override
    public int getItemCount() {
        return matchesList.size();
    }
}
