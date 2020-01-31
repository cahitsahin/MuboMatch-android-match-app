package com.example.mubomatch.UserProfile.Books;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mubomatch.R;

import java.util.List;

public class ProfileBooksAdapter extends RecyclerView.Adapter<ProfileBooksViewHolders> {

    private List<ProfileBooksObject> bookList;
    private Context context;


    public ProfileBooksAdapter(List<ProfileBooksObject> bookList, Context context){
        this.bookList=bookList;
        this.context=context;
    }

    @NonNull
    @Override
    public ProfileBooksViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ProfileBooksViewHolders rcv = new ProfileBooksViewHolders((layoutView));



        return rcv;
    }

    @Override
    public void onBindViewHolder(ProfileBooksViewHolders holder, int position) {


        if (!bookList.get(position).getBookImageUrl().equals("NoImage")){
            Glide.with(context).load(bookList.get(position).getBookImageUrl()).into(holder.mBookImage);
        }
    }


    @Override
    public int getItemCount() {
        return this.bookList.size();
    }
}
