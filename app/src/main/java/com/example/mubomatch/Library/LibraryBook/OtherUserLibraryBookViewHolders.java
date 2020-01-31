package com.example.mubomatch.Library.LibraryBook;

import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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


public class OtherUserLibraryBookViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView mBookPoster;
    public TextView mBookTitle,mBookGenre,mBookYear,mMatchText;
    public TextView mBookId;
    public TextView mBookUrl;
    public TextView mBookPublisher;
    public TextView mBookWriter;
    public TextView mBookISBN;
    public LinearLayout mBookLayout;
    public String CurrentUserId;
    public FirebaseAuth mAuth;
    public String bookId,bookUrl,bookTitle,bookYear,bookISBN,bookPublisher,bookWriter,bookGenre;
    public Button mAddBook,mDeleteBook;


    DatabaseReference mDatabaseUser,mDatabaseMovies;



    public OtherUserLibraryBookViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        CurrentUserId = mAuth.getInstance().getUid();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUserId).child("Books");




        mBookPoster = (ImageView) itemView.findViewById(R.id.library_book_poster);
        mBookTitle = (TextView) itemView.findViewById(R.id.library_book_name);
        mBookPublisher = (TextView) itemView.findViewById(R.id.library_book_publisher);
        mBookWriter = (TextView) itemView.findViewById(R.id.library_book_author);
        mBookISBN = (TextView) itemView.findViewById(R.id.library_book_isbn) ;
        mBookId= (TextView) itemView.findViewById(R.id.library_bookId);
        mBookUrl=(TextView) itemView.findViewById(R.id.library_BookUrl);
        mBookYear=(TextView) itemView.findViewById(R.id.library_book_year);
        mAddBook=(Button) itemView.findViewById(R.id.library_add_book_library_button);
        mDeleteBook=(Button) itemView.findViewById(R.id.library_delete_book_library_button);
        mBookGenre=(TextView)itemView.findViewById(R.id.library_book_genre);
        mBookLayout=(LinearLayout) itemView.findViewById(R.id.Lbook);
        mMatchText=(TextView) itemView.findViewById(R.id.match_text);


        mDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookId = mBookId.getText().toString();

                if (dataSnapshot.hasChild(bookId)){
                    mBookLayout.setBackgroundResource(R.drawable.match_item_gradient);
                    mMatchText.setVisibility(View.VISIBLE);
                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    @Override
    public void onClick(View view){




    }


}
