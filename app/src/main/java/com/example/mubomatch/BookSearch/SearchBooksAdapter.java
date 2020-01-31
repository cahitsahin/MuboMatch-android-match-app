package com.example.mubomatch.BookSearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mubomatch.MoviesSearch.MovieObject;
import com.example.mubomatch.R;

import java.util.List;

public class SearchBooksAdapter extends RecyclerView.Adapter<SearchBooksViewHolders> {

    private List<BookObject> bookList;
    private Context context;


    public SearchBooksAdapter(List<BookObject> bookList, Context context){
        this.bookList=bookList;
        this.context=context;
    }

    @NonNull
    @Override
    public SearchBooksViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_book,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        SearchBooksViewHolders rcv = new SearchBooksViewHolders((layoutView));



        return rcv;
    }

    @Override
    public void onBindViewHolder(SearchBooksViewHolders holder, int position) {

        holder.mBookTitle.setText(bookList.get(position).getTitle());
        if (bookList.get(position).getPublisher() != null){
            holder.mBookPublisher.setText(bookList.get(position).getPublisher());
        }
        if (bookList.get(position).getISBN() !=null) {
            holder.mBookISBN.setText(bookList.get(position).getISBN());
        }
        if (bookList.get(position).getAuthors() != null) {
            holder.mBookAuthor.setText(bookList.get(position).getAuthors());
        }
        holder.mBookGenre.setText(bookList.get(position).getCategories());

        if (bookList.get(position).getPublishedDate() != null) {
            holder.mBookYear.setText(bookList.get(position).getPublishedDate());
        }
        if (bookList.get(position).getImage() != null) {
            holder.mBookUrl.setText(bookList.get(position).getImage());
        }
        holder.mBookId.setText(bookList.get(position).getId());



        if (bookList.get(position).getImage() != null){
            Glide.with(context).load(bookList.get(position).getImage()).into(holder.mBookPoster);

        }
    }




    @Override
    public int getItemCount() {
        return this.bookList.size();
    }
}
