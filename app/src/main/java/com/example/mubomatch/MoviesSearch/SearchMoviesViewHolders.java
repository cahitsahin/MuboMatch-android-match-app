package com.example.mubomatch.MoviesSearch;

import android.content.Intent;
import android.provider.ContactsContract;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mubomatch.Chat.ChatActivity;
import com.example.mubomatch.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class SearchMoviesViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView mMoviePoster;
    public TextView mMovieTitle,mMovieGenre,mMovieYear;
    public TextView mMovieId;
    public TextView mMovieUrl;
    public TextView mMovieDirector;
    public TextView mMovieWriter;
    public TextView mMovieActor;
    public String CurrentUserId;
    public FirebaseAuth mAuth;
    public String movieId,movieUrl,movieTitle,movieYear,movieActor,movieDirector,movieWriter,movieGenre;
    public Button mAddMovie,mDeleteMovie;


    DatabaseReference mDatabaseUser,mDatabaseMovies;



    public SearchMoviesViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        CurrentUserId = mAuth.getInstance().getUid();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUserId).child("Movies");


        mMoviePoster = (ImageView) itemView.findViewById(R.id.movie_poster);
        mMovieTitle = (TextView) itemView.findViewById(R.id.movie_name);
        mMovieDirector = (TextView) itemView.findViewById(R.id.movie_director);
        mMovieWriter = (TextView) itemView.findViewById(R.id.movie_writer);
        mMovieActor = (TextView) itemView.findViewById(R.id.movie_actor) ;
        mMovieId= (TextView) itemView.findViewById(R.id.imdbId);
        mMovieYear=(TextView) itemView.findViewById(R.id.movie_year);
        mMovieUrl=(TextView) itemView.findViewById(R.id.movieUrl);
        mAddMovie=(Button) itemView.findViewById(R.id.add_library_button);
        mDeleteMovie=(Button) itemView.findViewById(R.id.delete_library_button);
        mMovieGenre=(TextView)itemView.findViewById(R.id.movie_genre);


        mDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                movieId = mMovieId.getText().toString();

                if (dataSnapshot.hasChild(movieId)){
                    mAddMovie.setVisibility(View.GONE);
                    mDeleteMovie.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUserId).child("Movies");


                movieId= mMovieId.getText().toString();
                movieUrl=mMovieUrl.getText().toString();
                movieTitle = mMovieTitle.getText().toString();
                movieDirector = mMovieDirector.getText().toString();
                movieWriter = mMovieWriter.getText().toString();
                movieActor = mMovieActor.getText().toString();
                movieGenre = mMovieGenre.getText().toString();
                movieYear = mMovieYear.getText().toString();


                Map movieInfo = new HashMap();
                movieInfo.put("Title", movieTitle);
                movieInfo.put("Writer", movieWriter);
                movieInfo.put("PosterUrl",movieUrl);
                movieInfo.put("Director",movieDirector);
                movieInfo.put("Actors",movieActor);
                movieInfo.put("Genre",movieGenre);
                movieInfo.put("Year",movieYear);
                movieInfo.put("MovieID",movieId);



                mDatabaseUser.child(movieId).updateChildren(movieInfo);
                mAddMovie.setVisibility(View.GONE);
                mDeleteMovie.setVisibility(View.VISIBLE);




            }
        });

        mDeleteMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUserId).child("Movies");


                movieId= mMovieId.getText().toString();

                mDatabaseUser.child(movieId).removeValue();
                mAddMovie.setVisibility(View.VISIBLE);
                mDeleteMovie.setVisibility(View.GONE);


            }
        });


    }
    @Override
    public void onClick(View view){




    }


}
