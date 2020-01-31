package com.example.mubomatch.UserProfile.Movies;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mubomatch.Library.LibraryActivity;
import com.example.mubomatch.R;

public class ProfileMoviesViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView mMovieImage;
    public ProfileMoviesViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMovieImage = (ImageView) itemView.findViewById(R.id.Movies_Image);
    }
    @Override
    public void onClick(View view){

        Intent intent = new Intent(view.getContext(), LibraryActivity.class);
        intent.putExtra("UniId","From_Movie");
        view.getContext().startActivity(intent);


    }
}
