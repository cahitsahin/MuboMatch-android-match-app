package com.example.mubomatch.BookSearch;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mubomatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class SearchBooksViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView mBookPoster;
    public TextView mBookTitle, mBookGenre, mBookYear;
    public TextView mBookId;
    public TextView mBookUrl;
    public TextView mBookAuthor;
    public TextView mBookISBN;
    public TextView mBookPublisher;
    public String CurrentUserId;
    public FirebaseAuth mAuth;
    public String bookId, bookUrl, bookTitle, bookYear, bookAuthor, bookPublisher, bookISBN, bookGenre;
    public Button mAddBook, mDeleteBook;


    DatabaseReference mDatabaseUser, mDatabaseMovies;


    public SearchBooksViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        CurrentUserId = mAuth.getInstance().getUid();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUserId).child("Books");


        mBookPoster = (ImageView) itemView.findViewById(R.id.book_poster);
        mBookTitle = (TextView) itemView.findViewById(R.id.book_name);
        mBookAuthor = (TextView) itemView.findViewById(R.id.book_author);
        mBookISBN = (TextView) itemView.findViewById(R.id.book_isbn);
        mBookPublisher = (TextView) itemView.findViewById(R.id.book_publisher);
        mBookId = (TextView) itemView.findViewById(R.id.bookId);
        mBookYear = (TextView) itemView.findViewById(R.id.book_year);
        mBookUrl = (TextView) itemView.findViewById(R.id.BookUrl);
        mAddBook = (Button) itemView.findViewById(R.id.add_book_library_button);
        mDeleteBook = (Button) itemView.findViewById(R.id.delete_book_library_button);
        mBookGenre = (TextView) itemView.findViewById(R.id.book_genre);


        mDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookId = mBookId.getText().toString();

                if (dataSnapshot.hasChild(bookId)) {
                    mAddBook.setVisibility(View.GONE);
                    mDeleteBook.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUserId).child("Books");


                bookId = mBookId.getText().toString();
                bookUrl = mBookUrl.getText().toString();
                bookTitle = mBookTitle.getText().toString();
                bookAuthor = mBookAuthor.getText().toString();
                bookPublisher = mBookPublisher.getText().toString();
                bookISBN = mBookISBN.getText().toString();
                bookGenre = mBookGenre.getText().toString();
                bookYear = mBookYear.getText().toString();


                Map bookInfo = new HashMap();
                bookInfo.put("Title", bookTitle);
                bookInfo.put("Author", bookAuthor);
                bookInfo.put("PosterUrl", bookUrl);
                bookInfo.put("Publisher", bookPublisher);
                bookInfo.put("ISBN", bookISBN);
                bookInfo.put("Genre", bookGenre);
                bookInfo.put("Year", bookYear);
                bookInfo.put("BookID", bookId);


                mDatabaseUser.child(bookId).updateChildren(bookInfo);
                mAddBook.setVisibility(View.GONE);
                mDeleteBook.setVisibility(View.VISIBLE);


            }
        });


        mDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUserId).child("Books");


                bookId = mBookId.getText().toString();

                mDatabaseUser.child(bookId).removeValue();
                mAddBook.setVisibility(View.VISIBLE);
                mDeleteBook.setVisibility(View.GONE);

            }
        });


    }


    @Override
    public void onClick(View view) {

    }
}
