package com.example.mubomatch.UserProfile.Artist;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mubomatch.Library.LibraryActivity;
import com.example.mubomatch.R;

public class ProfileArtistsViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView mArtistImage;
    public ProfileArtistsViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mArtistImage = (ImageView) itemView.findViewById(R.id.Artist_Image);
    }
    @Override
    public void onClick(View view){

        Intent intent = new Intent(view.getContext(), LibraryActivity.class);
        intent.putExtra("UniId","From_Artist");
        view.getContext().startActivity(intent);


    }
}
