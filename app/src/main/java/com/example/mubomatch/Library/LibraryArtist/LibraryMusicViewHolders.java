package com.example.mubomatch.Library.LibraryArtist;

import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mubomatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class LibraryMusicViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView mArtistPoster;
    public TextView mArtistTitle,mArtistGenre,mArtistId,mArtistUrl;
    public TextView mMatchText;
    public String CurrentUserId;
    public FirebaseAuth mAuth;
    public String artistId,artistUrl,artistTitle,artistGenre;
    public Button mAddArtist,mDeleteArtist;


    DatabaseReference mDatabaseUser,mDatabaseMovies;



    public LibraryMusicViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        CurrentUserId = mAuth.getInstance().getUid();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUserId).child("Artist");


        mArtistPoster = (ImageView) itemView.findViewById(R.id.artist_library_poster);
        mArtistTitle = (TextView) itemView.findViewById(R.id.artist_library_name);
        mArtistId= (TextView) itemView.findViewById(R.id.artist_library_id);
        mArtistUrl=(TextView) itemView.findViewById(R.id.artistLibraryUrl);
        mAddArtist=(Button) itemView.findViewById(R.id.add_artist_library_button);
        mDeleteArtist=(Button) itemView.findViewById(R.id.delete_artist_library_button);
        mArtistGenre=(TextView)itemView.findViewById(R.id.artist_library_genre);

        mDeleteArtist.setVisibility(View.VISIBLE);





        mDeleteArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                artistId = mArtistId.getText().toString();



                mDatabaseUser.child(artistId).removeValue();
                mDeleteArtist.setVisibility(View.GONE);
                mAddArtist.setVisibility(View.VISIBLE);

                String id = CurrentUserId;

                String movieScoreUrl = "https://us-central1-second-project-258608.cloudfunctions.net/calculateMusicScore?id=ID";
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




        mAddArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                artistId = mArtistId.getText().toString();
                artistUrl=mArtistUrl.getText().toString();
                artistTitle = mArtistTitle.getText().toString();
                artistGenre = mArtistGenre.getText().toString();


                Map movieInfo = new HashMap();
                movieInfo.put("Title", artistTitle);
                movieInfo.put("PosterUrl",artistUrl);
                movieInfo.put("Genre",artistGenre);
                movieInfo.put("ArtistID",artistId);



                mDatabaseUser.child(artistId).updateChildren(movieInfo);
                mAddArtist.setVisibility(View.GONE);
                mDeleteArtist.setVisibility(View.VISIBLE);

                String id = CurrentUserId;
                String movieScoreUrl = "https://us-central1-second-project-258608.cloudfunctions.net/calculateMusicScore?id=ID";
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
