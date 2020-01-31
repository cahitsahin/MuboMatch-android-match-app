package com.example.mubomatch.Library.LibraryBook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mubomatch.Library.LibraryMovie.OtherUserLibraryMovieAdapter;
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


public class OtherUserLibraryBookFragment extends Fragment {

    private RecyclerView mOtherUserLibraryBookRecyclerView;
    private RecyclerView.Adapter mOtherUserLibraryBookAdapter;
    private RecyclerView.LayoutManager mOtherUserLibraryBookLayoutManager;
    private String otherUserID;


    private DatabaseReference BookDb;
    public String bookId;
    public String bookUrl;

    private String title;
    private String year;
    private String genre;
    private String publisher;
    private String writer;
    private String ISBN;
    private String poster;
    private String id;




    public OtherUserLibraryBookFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootV = inflater.inflate(R.layout.fragment_other_user_library_book, container, false);

        OtherUserLibraryActivity activity = (OtherUserLibraryActivity) getActivity();
        otherUserID = activity.getMyData();


        mOtherUserLibraryBookRecyclerView = (RecyclerView) rootV.findViewById(R.id.other_user_library_book_recyclerView);
        mOtherUserLibraryBookRecyclerView.setNestedScrollingEnabled(false);
        mOtherUserLibraryBookRecyclerView.setHasFixedSize(true);
        mOtherUserLibraryBookLayoutManager = new LinearLayoutManager(getContext());
        mOtherUserLibraryBookRecyclerView.setLayoutManager(mOtherUserLibraryBookLayoutManager);
        mOtherUserLibraryBookAdapter = new OtherUserLibraryBookAdapter(getDataSetMatches(),getContext());
        mOtherUserLibraryBookRecyclerView.setAdapter(mOtherUserLibraryBookAdapter);





        BookDb = FirebaseDatabase.getInstance().getReference().child("Users").child(otherUserID);




        BookDb.child("Books").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                resultsLibraryBooks.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    poster = String.valueOf(dataSnapshot1.child("PosterUrl").getValue());
                    title = String.valueOf(dataSnapshot1.child("Title").getValue());
                    writer = String.valueOf(dataSnapshot1.child("Author").getValue());
                    publisher = String.valueOf(dataSnapshot1.child("Publisher").getValue());
                    ISBN = String.valueOf(dataSnapshot1.child("ISBN").getValue());
                    genre = String.valueOf(dataSnapshot1.child("Genre").getValue());
                    year = String.valueOf(dataSnapshot1.child("Year").getValue());
                    bookId = String.valueOf(dataSnapshot1.child("BookID").getValue());
                    LibraryBookObject obj = new LibraryBookObject(title,bookId,ISBN,writer,poster,year,genre,publisher);
                    resultsLibraryBooks.add(obj);

                    mOtherUserLibraryBookAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return rootV;
    }

    private ArrayList<LibraryBookObject> resultsLibraryBooks = new ArrayList<LibraryBookObject>() ;

    private List<LibraryBookObject> getDataSetMatches() {
        return resultsLibraryBooks;
    }


}