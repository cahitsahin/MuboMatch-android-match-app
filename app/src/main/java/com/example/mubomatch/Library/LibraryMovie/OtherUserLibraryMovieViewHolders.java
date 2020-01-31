package com.example.mubomatch.Library.LibraryMovie;

import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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



public class OtherUserLibraryMovieViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView mMoviePoster;
    public TextView mMovieTitle,mMovieGenre,mMovieYear;
    public TextView mMovieId,mMatchText;
    public TextView mMovieUrl;
    public TextView mMovieDirector;
    public TextView mMovieWriter;
    public TextView mMovieActor;
    public String CurrentUserId;
    public FirebaseAuth mAuth;
    public String movieId;
    public Button mAddMovie,mDeleteMovie;
    public LinearLayout mMovieLayout;


    DatabaseReference mDatabaseUser;



    public OtherUserLibraryMovieViewHolders(final View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        CurrentUserId = mAuth.getInstance().getUid();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUserId).child("Movies");


        mMoviePoster = (ImageView) itemView.findViewById(R.id.movie_poster);
        mMovieTitle = (TextView) itemView.findViewById(R.id.movie_name);
        mMovieDirector = (TextView) itemView.findViewById(R.id.movie_director);
        mMovieWriter = (TextView) itemView.findViewById(R.id.movie_writer);
        mMovieActor = (TextView) itemView.findViewById(R.id.movie_actor) ;
        mMovieId= (TextView) itemView.findViewById(R.id.imdbId);
        mMovieUrl=(TextView) itemView.findViewById(R.id.movieUrl);
        mMovieYear=(TextView) itemView.findViewById(R.id.movie_year);
        mAddMovie=(Button) itemView.findViewById(R.id.add_library_button);
        mDeleteMovie=(Button) itemView.findViewById(R.id.delete_library_button);
        mMovieGenre=(TextView)itemView.findViewById(R.id.movie_genre);
        mMovieLayout = (LinearLayout) itemView.findViewById(R.id.Lmovie);
        mMatchText = (TextView) itemView.findViewById(R.id.match_text);




        mDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                movieId = mMovieId.getText().toString();

                if (dataSnapshot.hasChild(movieId)){
                    mMovieLayout.setBackgroundResource(R.drawable.match_item_gradient);
                    mMatchText.setVisibility(View.VISIBLE);
                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
    @Override
    public void onClick(View view){




    }


}
