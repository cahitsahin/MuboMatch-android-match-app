package com.example.mubomatch.Matches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mubomatch.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolders> {

    private List<MessageObject> matchesList;
    private Context context;


    public MessageAdapter(List<MessageObject> matchesList, Context context){
        this.matchesList=matchesList;
        this.context=context;
    }

    @NonNull
    @Override
    public MessageViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matches,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        MessageViewHolders rcv = new MessageViewHolders((layoutView));



        return rcv;
    }

    @Override
    public void onBindViewHolder(MessageViewHolders holder, int position) {
        holder.mMatchId.setText(matchesList.get(position).getUserId());
        holder.mMatchName.setText(matchesList.get(position).getName());
        holder.mMatchPpUrl.setText(matchesList.get(position).getProfileImageUrl());
        if(!matchesList.get(position).getProfileImageUrl().equals("Default")) {
            Glide.with(context).load(matchesList.get(position).getProfileImageUrl()).into(holder.mMatchImage);
        }

    }

    @Override
    public int getItemCount() {
        return matchesList.size();
    }
}
