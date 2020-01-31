package com.example.mubomatch;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IsLibraryEnoughFragment extends Fragment {

    FirebaseAuth mAuth;
    DatabaseReference userDb;
    private String userID;
    private int movieCount,bookCount,artistCount;
    private TextView mBookCountText,mMovieCountText,mArtistCountText;

    public IsLibraryEnoughFragment(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootV = inflater.inflate(R.layout.fragment_is_library_enough, container, false);

        mAuth=FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        GetLibraryItem();
        mMovieCountText = (TextView) rootV.findViewById(R.id.movie_count);
        mBookCountText = (TextView) rootV.findViewById(R.id.book_count);
        mArtistCountText = (TextView) rootV.findViewById(R.id.artist_count);










        return rootV;
    }


    public void GetLibraryItem(){
        userDb.child("Movies").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   int movieC = (int) dataSnapshot.getChildrenCount();
                   if (movieC<5){
                       int nMovie = 5-movieC;
                       mMovieCountText.setText(nMovie+" Movie!");
                       mMovieCountText.setVisibility(View.VISIBLE);
                   }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        userDb.child("Artist").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   int artistC = (int) dataSnapshot.getChildrenCount();
                if (artistC<5){
                    int nArtist = 5-artistC;
                    mArtistCountText.setText(nArtist+" Artist!");
                    mArtistCountText.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        userDb.child("Books").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int bookC = (int) dataSnapshot.getChildrenCount();
                if (bookC<5){
                    int nBook = 5-bookC;
                    mBookCountText.setText(nBook+" Book!");
                    mBookCountText.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }


}
