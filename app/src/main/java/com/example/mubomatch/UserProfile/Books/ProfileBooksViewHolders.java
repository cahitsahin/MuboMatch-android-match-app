package com.example.mubomatch.UserProfile.Books;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mubomatch.Library.LibraryActivity;
import com.example.mubomatch.R;

public class ProfileBooksViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView mBookImage;
    public ProfileBooksViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mBookImage = (ImageView) itemView.findViewById(R.id.Book_Image);
    }
    @Override
    public void onClick(View view){

        Intent intent = new Intent(view.getContext(), LibraryActivity.class);
        intent.putExtra("UniId","From_Book");
        view.getContext().startActivity(intent);


    }
}
