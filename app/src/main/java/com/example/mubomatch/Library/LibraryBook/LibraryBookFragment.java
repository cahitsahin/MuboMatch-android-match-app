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

import com.example.mubomatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class LibraryBookFragment extends Fragment {

    private RecyclerView mLibraryBookRecyclerView;
    private RecyclerView.Adapter mLibraryBookAdapter;
    private RecyclerView.LayoutManager mLibraryBookLayoutManager;
    private List<LibraryBookObject> bookList;
    private DatabaseReference BookDb;
    private DatabaseReference UserDb;
    private String CurrentUserId;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public TextView mBookId;
    public TextView mBookUrl;
    public String bookId,userId;
    public String bookUrl;

    private String title;
    private String year;
    private String genre;
    private String publisher;
    private String writer;
    private String ISBN;
    private String poster;
    private String id;




    public LibraryBookFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootV = inflater.inflate(R.layout.fragment_library_book, container, false);

        mLibraryBookRecyclerView = (RecyclerView) rootV.findViewById(R.id.library_book_recyclerView);
        mLibraryBookRecyclerView.setNestedScrollingEnabled(false);
        mLibraryBookRecyclerView.setHasFixedSize(true);
        mLibraryBookLayoutManager = new LinearLayoutManager(getContext());
        mLibraryBookRecyclerView.setLayoutManager(mLibraryBookLayoutManager);
        mLibraryBookAdapter = new LibraryBookAdapter(getDataSetMatches(),getContext());
        mLibraryBookRecyclerView.setAdapter(mLibraryBookAdapter);


        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        BookDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);




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

                    mLibraryBookAdapter.notifyDataSetChanged();

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