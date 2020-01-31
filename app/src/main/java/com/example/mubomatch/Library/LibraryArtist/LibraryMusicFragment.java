package com.example.mubomatch.Library.LibraryArtist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mubomatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class LibraryMusicFragment extends Fragment {

    private RecyclerView mLibraryArtistRecyclerView;
    private RecyclerView.Adapter mLibraryArtistAdapter;
    private RecyclerView.LayoutManager mLibraryArtistLayoutManager;
    private List<LibraryMusicObject> artistList;
    private DatabaseReference artistDb;
    private DatabaseReference UserDb;
    private String CurrentUserId;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public TextView mArtistId;
    public TextView mArtistUrl;
    public String artistId,userId;
    public String artistUrl;

    private String title;
    private String genre;
    private String poster;
    private String id;




    public LibraryMusicFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootV = inflater.inflate(R.layout.fragment_library_book, container, false);

        mLibraryArtistRecyclerView = (RecyclerView) rootV.findViewById(R.id.library_book_recyclerView);
        mLibraryArtistRecyclerView.setNestedScrollingEnabled(false);
        mLibraryArtistRecyclerView.setHasFixedSize(true);
        mLibraryArtistLayoutManager = new LinearLayoutManager(getContext());
        mLibraryArtistRecyclerView.setLayoutManager(mLibraryArtistLayoutManager);
        mLibraryArtistAdapter = new LibraryMusicAdapter(getDataSetMatches(),getContext());
        mLibraryArtistRecyclerView.setAdapter(mLibraryArtistAdapter);


        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        artistDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);




        artistDb.child("Artist").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                resultsLibraryArtist.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    poster = String.valueOf(dataSnapshot1.child("PosterUrl").getValue());
                    title = String.valueOf(dataSnapshot1.child("Title").getValue());
                    genre = String.valueOf(dataSnapshot1.child("Genre").getValue());
                    artistId = String.valueOf(dataSnapshot1.child("ArtistID").getValue());
                    LibraryMusicObject obj = new LibraryMusicObject(title,artistId,poster,genre);
                    resultsLibraryArtist.add(obj);

                    mLibraryArtistAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return rootV;
    }

    private ArrayList<LibraryMusicObject> resultsLibraryArtist = new ArrayList<LibraryMusicObject>() ;

    private List<LibraryMusicObject> getDataSetMatches() {
        return resultsLibraryArtist;
    }


}