package com.example.mubomatch.MoviesSearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.mubomatch.R;
import com.example.mubomatch.UserProfile.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity {



    private RecyclerView mSearchMovieRecyclerView;
    private RecyclerView.Adapter mSearchMoviesAdapter;
    private RecyclerView.LayoutManager mSearchMoviesLayoutManager;
    private SearchView mSearchView;
    private MovieProcesses proc;
    private List<MovieObject> movList;
    private Button addMovie;
    private DatabaseReference MovieDb;
    private DatabaseReference UserDb;
    private String CurrentUserId;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public TextView mMovieId;
    public TextView mMovieUrl,nothingText;
    private ImageView mBackButton,nothingImage;
    public String movieId;
    public String movieUrl;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		CurrentUserId = mAuth.getInstance().getUid();




        mSearchMovieRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mSearchMovieRecyclerView.setNestedScrollingEnabled(false);
        mSearchMovieRecyclerView.setHasFixedSize(true);
        mSearchMoviesLayoutManager = new LinearLayoutManager(MoviesActivity.this);
        mSearchMovieRecyclerView.setLayoutManager(mSearchMoviesLayoutManager);
        mSearchMoviesAdapter = new SearchMoviesAdapter(getDataSetMatches(),MoviesActivity.this);
        mSearchMovieRecyclerView.setAdapter(mSearchMoviesAdapter);

        mSearchView = (SearchView) findViewById(R.id.movie_search);
        mBackButton = (ImageView) findViewById(R.id.back_button);
        addMovie = (Button) findViewById(R.id.add_library_button);
        mMovieId= (TextView) findViewById(R.id.imdbId);
        mMovieUrl=(TextView) findViewById(R.id.movieUrl);

        nothingText = (TextView) findViewById(R.id.noMovieText);
        nothingImage = (ImageView) findViewById(R.id.noMovieImage);



        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateScore();
                finish();
                return;
            }
        });




        proc = new MovieProcesses();




        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                resultsSearchMovies.clear();

                movList = proc.searchMovie(s);

                if (movList.size()<1){
                    nothingText.setVisibility(View.VISIBLE);
                    nothingImage.setVisibility(View.VISIBLE);
                }
                else {
                    nothingText.setVisibility(View.GONE);
                    nothingImage.setVisibility(View.GONE);
                }

                for (int i=0 ; i< movList.size();i++){

                    MovieObject movieeee = movList.get(i);


                    resultsSearchMovies.add(movieeee);
                    mSearchMoviesAdapter.notifyDataSetChanged();
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
        String movieScoreUrl = "https://us-central1-second-project-258608.cloudfunctions.net/calculateMovieScore?id=ID";
        try {
            URL url = new URL(movieScoreUrl.replaceAll("ID", CurrentUserId));
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

    private ArrayList<MovieObject> resultsSearchMovies = new ArrayList<MovieObject>() ;

    private List<MovieObject> getDataSetMatches() {
        return resultsSearchMovies;
    }

    }
