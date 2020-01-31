package com.example.mubomatch.Library.LibraryArtist;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class OtherUserLibraryMusicFragment extends Fragment {

    private RecyclerView mOtherUserLibraryMusicRecyclerView;
    private RecyclerView.Adapter mOtherUserLibraryMusicAdapter;
    private RecyclerView.LayoutManager mOtherUserLibraryMusicLayoutManager;
    private String otherUserID;


    private DatabaseReference artistDB;
    public String artistID;
    public String artistUrl;
    private String title;
    private String genre;
    private String poster;
    private String id;




    public OtherUserLibraryMusicFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootV = inflater.inflate(R.layout.fragment_other_user_library_music, container, false);

        OtherUserLibraryActivity activity = (OtherUserLibraryActivity) getActivity();
        otherUserID = activity.getMyData();


        mOtherUserLibraryMusicRecyclerView = (RecyclerView) rootV.findViewById(R.id.other_user_library_artist_recyclerView);
        mOtherUserLibraryMusicRecyclerView.setNestedScrollingEnabled(false);
        mOtherUserLibraryMusicRecyclerView.setHasFixedSize(true);
        mOtherUserLibraryMusicLayoutManager = new LinearLayoutManager(getContext());
        mOtherUserLibraryMusicRecyclerView.setLayoutManager(mOtherUserLibraryMusicLayoutManager);
        mOtherUserLibraryMusicAdapter = new OtherUserLibraryMusicAdapter(getDataSetMatches(),getContext());
        mOtherUserLibraryMusicRecyclerView.setAdapter(mOtherUserLibraryMusicAdapter);





        artistDB = FirebaseDatabase.getInstance().getReference().child("Users").child(otherUserID);




        artistDB.child("Artist").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                resultsLibraryMusic.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    poster = String.valueOf(dataSnapshot1.child("PosterUrl").getValue());
                    title = String.valueOf(dataSnapshot1.child("Title").getValue());
                    genre = String.valueOf(dataSnapshot1.child("Genre").getValue());
                    artistID = String.valueOf(dataSnapshot1.child("ArtistID").getValue());
                    LibraryMusicObject obj = new LibraryMusicObject(title,artistID,poster,genre);
                    resultsLibraryMusic.add(obj);

                    mOtherUserLibraryMusicAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return rootV;
    }

    private ArrayList<LibraryMusicObject> resultsLibraryMusic = new ArrayList<LibraryMusicObject>() ;

    private List<LibraryMusicObject> getDataSetMatches() {
        return resultsLibraryMusic;
    }


}