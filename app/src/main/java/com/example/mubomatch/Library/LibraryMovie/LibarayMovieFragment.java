package com.example.mubomatch.Library.LibraryMovie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mubomatch.MoviesSearch.MovieObject;
import com.example.mubomatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class LibarayMovieFragment extends Fragment {

    private RecyclerView mLibraryMovieRecyclerView;
    private RecyclerView.Adapter mLibraryMoviesAdapter;
    private RecyclerView.LayoutManager mLibraryMoviesLayoutManager;
    private List<MovieObject> movList;
    private DatabaseReference MovieDb;
    private DatabaseReference UserDb;
    private String CurrentUserId;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public TextView mMovieId;
    public TextView mMovieUrl;
    public String movieId,userId;
    public String movieUrl;

    private String title;
    private String year;
    private String genre;
    private String director;
    private String writer;
    private String actors;
    private String poster;
    private String imdbID;




    public LibarayMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootV = inflater.inflate(R.layout.fragment_libaray_movie, container, false);

        mLibraryMovieRecyclerView = (RecyclerView) rootV.findViewById(R.id.library_movie_recyclerView);
        mLibraryMovieRecyclerView.setNestedScrollingEnabled(false);
        mLibraryMovieRecyclerView.setHasFixedSize(true);
        mLibraryMoviesLayoutManager = new LinearLayoutManager(getContext());
        mLibraryMovieRecyclerView.setLayoutManager(mLibraryMoviesLayoutManager);
        mLibraryMoviesAdapter = new LibraryMovieAdapter(getDataSetMatches(),getContext());
        mLibraryMovieRecyclerView.setAdapter(mLibraryMoviesAdapter);


        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        MovieDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);




        MovieDb.child("Movies").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                resultsLibraryMovies.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    poster = String.valueOf(dataSnapshot1.child("PosterUrl").getValue());
                    title = String.valueOf(dataSnapshot1.child("Title").getValue());
                    writer = String.valueOf(dataSnapshot1.child("Writer").getValue());
                    director = String.valueOf(dataSnapshot1.child("Director").getValue());
                    actors = String.valueOf(dataSnapshot1.child("Actors").getValue());
                    genre = String.valueOf(dataSnapshot1.child("Genre").getValue());
                    year = String.valueOf(dataSnapshot1.child("Year").getValue());
                    movieId = String.valueOf(dataSnapshot1.child("MovieID").getValue());
                    LibraryMovieObject obj = new LibraryMovieObject(title,year,genre,director,writer,actors,poster,movieId);
                    resultsLibraryMovies.add(obj);

                    mLibraryMoviesAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return rootV;
    }

    private ArrayList<LibraryMovieObject> resultsLibraryMovies = new ArrayList<LibraryMovieObject>() ;

    private List<LibraryMovieObject> getDataSetMatches() {
        return resultsLibraryMovies;
    }


}