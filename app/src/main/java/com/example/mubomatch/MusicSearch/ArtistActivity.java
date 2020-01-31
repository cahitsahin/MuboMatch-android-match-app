package com.example.mubomatch.MusicSearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import com.example.mubomatch.R;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ArtistActivity extends AppCompatActivity {

    private ArtistProcesses procA;
    private List<ArtistObject> artist;
    private TextView Title,nothingText;
    private RecyclerView mSearchArtistRecyclerView;
    private RecyclerView.Adapter mSearchArtistAdapter;
    private RecyclerView.LayoutManager mSearchArtistLayoutManager;
    private SearchView mSearchView;
    private ImageView mBackButton,nothingImage;
    public FirebaseAuth mAuth;
    private String currentUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);




        currentUserId = mAuth.getInstance().getUid();


        mSearchArtistRecyclerView = (RecyclerView) findViewById(R.id.artistSearchRecyclerView);
        mSearchArtistRecyclerView.setNestedScrollingEnabled(false);
        mSearchArtistRecyclerView.setHasFixedSize(true);
        mSearchArtistLayoutManager = new LinearLayoutManager(ArtistActivity.this);
        mSearchArtistRecyclerView.setLayoutManager(mSearchArtistLayoutManager);
        mSearchArtistAdapter = new SearchArtistAdapter(getDataSetArtist(),ArtistActivity.this);
        mSearchArtistRecyclerView.setAdapter(mSearchArtistAdapter);

        mSearchView = (SearchView) findViewById(R.id.artist_search);


        mBackButton = (ImageView) findViewById(R.id.back_button);
        nothingText = (TextView) findViewById(R.id.noArtistText);
        nothingImage = (ImageView) findViewById(R.id.noArtistImage);




        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateScore();
                finish();
                return;
            }
        });




        procA = new ArtistProcesses();




        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                resultsSearchArtist.clear();

                artist = procA.searchArtists(s);

                if (artist.size()<1){
                    nothingText.setVisibility(View.VISIBLE);
                    nothingImage.setVisibility(View.VISIBLE);
                }
                else {
                    nothingText.setVisibility(View.GONE);
                    nothingImage.setVisibility(View.GONE);


                    for (int i = 0; i < artist.size(); i++) {

                        ArtistObject ar = artist.get(i);


                        resultsSearchArtist.add(ar);
                        mSearchArtistAdapter.notifyDataSetChanged();

                    }

                }



                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {


                return false;
            }
        });



    }

    private void calculateScore() {
        String id = currentUserId;
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

    private ArrayList<ArtistObject> resultsSearchArtist = new ArrayList<ArtistObject>() ;

    private List<ArtistObject> getDataSetArtist() {
        return resultsSearchArtist;
    }
}
