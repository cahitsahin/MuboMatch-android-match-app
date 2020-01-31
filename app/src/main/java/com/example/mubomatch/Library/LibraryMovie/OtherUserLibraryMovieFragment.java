package com.example.mubomatch.Library.LibraryMovie;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mubomatch.Library.OtherUserLibraryActivity;
import com.example.mubomatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class OtherUserLibraryMovieFragment extends Fragment {

    private String otherUserID;
    private RecyclerView mOtherUserLibraryMovieRecyclerView;
    private RecyclerView.LayoutManager mOtherUserLibraryMoviesLayoutManager;
    private RecyclerView.Adapter mOtherUserLibraryMoviesAdapter;

    private DatabaseReference MovieDb;
    private String title;
    private String year;
    private String genre;
    private String director;
    private String writer;
    private String actors;
    private String poster;
    private String movieId;

    public OtherUserLibraryMovieFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rv = inflater.inflate(R.layout.fragment_other_user_library_movie, container, false);


        OtherUserLibraryActivity activity = (OtherUserLibraryActivity) getActivity();
        otherUserID = activity.getMyData();


        mOtherUserLibraryMovieRecyclerView = (RecyclerView) rv.findViewById(R.id.other_user_library_movie_recyclerView);
        mOtherUserLibraryMovieRecyclerView.setNestedScrollingEnabled(false);
        mOtherUserLibraryMovieRecyclerView.setHasFixedSize(true);
        mOtherUserLibraryMoviesLayoutManager = new LinearLayoutManager(getContext());
        mOtherUserLibraryMovieRecyclerView.setLayoutManager(mOtherUserLibraryMoviesLayoutManager);
        mOtherUserLibraryMoviesAdapter = new OtherUserLibraryMovieAdapter(getDataSetMatches(),getContext());
        mOtherUserLibraryMovieRecyclerView.setAdapter(mOtherUserLibraryMoviesAdapter);



        MovieDb = FirebaseDatabase.getInstance().getReference().child("Users").child(otherUserID);




        MovieDb.child("Movies").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                resultsOtherUserLibraryMovies.clear();
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
                    resultsOtherUserLibraryMovies.add(obj);

                    mOtherUserLibraryMoviesAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        return rv;
    }

    private ArrayList<LibraryMovieObject> resultsOtherUserLibraryMovies = new ArrayList<LibraryMovieObject>() ;

    private List<LibraryMovieObject> getDataSetMatches() {
        return resultsOtherUserLibraryMovies;
    }

}
