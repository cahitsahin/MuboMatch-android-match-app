package com.example.mubomatch.MusicSearch;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mubomatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class SearchArtistViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView mArtistPoster;
    public TextView mArtistTitle,mArtistGenre;
    public TextView mArtistId;
    public TextView mArtistUrl;

    public String CurrentUserId;
    public FirebaseAuth mAuth;
    public String artistId,artistUrl,artistTitle,artistGenre;
    public Button mAddArtist,mDeleteArtist;


    DatabaseReference mDatabaseUser,mDatabaseMovies;



    public SearchArtistViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        CurrentUserId = mAuth.getInstance().getUid();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUserId).child("Artist");


        mArtistPoster = (ImageView) itemView.findViewById(R.id.artist_poster);
        mArtistTitle = (TextView) itemView.findViewById(R.id.artist_name);
        mArtistId = (TextView) itemView.findViewById(R.id.artist_id);
        mArtistUrl = (TextView) itemView.findViewById(R.id.artistUrl);
        mAddArtist = (Button) itemView.findViewById(R.id.add_artist_library_button);
        mDeleteArtist = (Button) itemView.findViewById(R.id.delete_artist_library_button);
        mArtistGenre = (TextView) itemView.findViewById(R.id.artist_genre);



        mDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                artistId = mArtistId.getText().toString();

                if (dataSnapshot.hasChild(artistId)){
                    mAddArtist.setVisibility(View.GONE);
                    mDeleteArtist.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mAddArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUserId).child("Artist");


                artistId = mArtistId.getText().toString();
                artistUrl = mArtistUrl.getText().toString();
                artistTitle = mArtistTitle.getText().toString();
                artistGenre = mArtistGenre.getText().toString();


                Map movieInfo = new HashMap();
                movieInfo.put("Title", artistTitle);
                movieInfo.put("PosterUrl", artistUrl);
                movieInfo.put("Genre", artistGenre);
                movieInfo.put("ArtistID", artistId);


                mDatabaseUser.child(artistId).updateChildren(movieInfo);
                mAddArtist.setVisibility(View.GONE);
                mDeleteArtist.setVisibility(View.VISIBLE);


            }
        });


        mDeleteArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUserId).child("Movies");


                artistId = mArtistId.getText().toString();

                mDatabaseUser.child(artistId).removeValue();
                mAddArtist.setVisibility(View.VISIBLE);
                mDeleteArtist.setVisibility(View.GONE);


            }
        });

    }

    @Override
    public void onClick(View view) {

    }
}

