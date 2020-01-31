package com.example.mubomatch.Library.LibraryBook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mubomatch.R;

import java.util.List;

public class LibraryBookAdapter extends RecyclerView.Adapter<LibraryBookViewHolders> {

    private List<LibraryBookObject> libraryBookList;
    private Context context;


    public LibraryBookAdapter(List<LibraryBookObject> libraryBookList, Context context){
        this.libraryBookList=libraryBookList;
        this.context=context;
    }

    @NonNull
    @Override
    public LibraryBookViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_library_book,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        LibraryBookViewHolders rcv = new LibraryBookViewHolders((layoutView));



        return rcv;
    }

    @Override
    public void onBindViewHolder(LibraryBookViewHolders holder, int position) {

        holder.mBookTitle.setText(libraryBookList.get(position).getTitle());
        holder.mBookPublisher.setText(libraryBookList.get(position).getPublisher());
        holder.mBookWriter.setText(libraryBookList.get(position).getAuthors());
        holder.mBookISBN.setText(libraryBookList.get(position).getISBN());
        holder.mBookGenre.setText(libraryBookList.get(position).getCategories());
        holder.mBookYear.setText(libraryBookList.get(position).getPublishedDate());
        holder.mBookId.setText(libraryBookList.get(position).getId());

        holder.mBookUrl.setText(libraryBookList.get(position).getImage());


        if (!libraryBookList.get(position).getImage().equals("No Image")){
            Glide.with(context).load(libraryBookList.get(position).getImage()).into(holder.mBookPoster);
        }
    }




    @Override
    public int getItemCount() {
        return this.libraryBookList.size();
    }
}
