package com.example.mubomatch.Library.LibraryMovie;

import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mubomatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class LibraryMovieViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
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



    public LibraryMovieViewHolders(View itemView) {
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

        mDeleteMovie.setVisibility(View.VISIBLE);





        mDeleteMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieId = mMovieId.getText().toString();



                mDatabaseUser.child(movieId).removeValue();
                mDeleteMovie.setVisibility(View.GONE);
                mAddMovie.setVisibility(View.VISIBLE);

                String id = CurrentUserId;
                String movieScoreUrl = "https://us-central1-second-project-258608.cloudfunctions.net/calculateMovieScore?id=ID";
                try {
                    URL url = new URL(movieScoreUrl.replaceAll("ID", id));
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.getInputStream();
                    connection.disconnect();
                }catch(MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });




        mAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieId = mMovieId.getText().toString();
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

                String id = CurrentUserId;
                String movieScoreUrl = "https://us-central1-second-project-258608.cloudfunctions.net/calculateMovieScore?id=ID";
                try {
                    URL url = new URL(movieScoreUrl.replaceAll("ID", id));
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.getInputStream();
                    connection.disconnect();
                }catch(MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });





    }
    @Override
    public void onClick(View view){




    }


}
