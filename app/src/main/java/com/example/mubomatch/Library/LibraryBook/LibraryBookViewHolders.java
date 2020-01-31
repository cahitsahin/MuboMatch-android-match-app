package com.example.mubomatch.Library.LibraryBook;

import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mubomatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class LibraryBookViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView mBookPoster;
    public TextView mBookTitle,mBookGenre,mBookYear;
    public TextView mBookId,mMatchText;
    public TextView mBookUrl;
    public TextView mBookPublisher;
    public TextView mBookWriter;
    public TextView mBookISBN;
    public String CurrentUserId;
    public FirebaseAuth mAuth;
    public String bookId,bookUrl,bookTitle,bookYear,bookISBN,bookPublisher,bookWriter,bookGenre;
    public Button mAddBook,mDeleteBook;


    DatabaseReference mDatabaseUser,mDatabaseMovies;



    public LibraryBookViewHolders(View itemView) {
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

        mDeleteBook.setVisibility(View.VISIBLE);





        mDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookId = mBookId.getText().toString();



                mDatabaseUser.child(bookId).removeValue();
                mDeleteBook.setVisibility(View.GONE);
                mAddBook.setVisibility(View.VISIBLE);

                String id = CurrentUserId;
                String movieScoreUrl = "https://us-central1-second-project-258608.cloudfunctions.net/calculateBookScore?id=ID";
                try {
                    URL url = new URL(movieScoreUrl.replaceAll("ID", id));
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
        });




        mAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookId = mBookId.getText().toString();
                bookUrl=mBookUrl.getText().toString();
                bookTitle = mBookTitle.getText().toString();
                bookPublisher = mBookPublisher.getText().toString();
                bookWriter = mBookWriter.getText().toString();
                bookISBN = mBookISBN.getText().toString();
                bookGenre = mBookGenre.getText().toString();
                bookYear = mBookYear.getText().toString();


                Map movieInfo = new HashMap();
                movieInfo.put("Title", bookTitle);
                movieInfo.put("Author", bookWriter);
                movieInfo.put("PosterUrl",bookUrl);
                movieInfo.put("Publisher",bookPublisher);
                movieInfo.put("ISBN",bookISBN);
                movieInfo.put("Genre",bookGenre);
                movieInfo.put("Year",bookYear);
                movieInfo.put("BookID",bookId);



                mDatabaseUser.child(bookId).updateChildren(movieInfo);
                mAddBook.setVisibility(View.GONE);
                mDeleteBook.setVisibility(View.VISIBLE);

                String id = CurrentUserId;
                String movieScoreUrl = "https://us-central1-second-project-258608.cloudfunctions.net/calculateBookScore?id=ID";
                try {
                    URL url = new URL(movieScoreUrl.replaceAll("ID", id));
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
        });





    }
    @Override
    public void onClick(View view){




    }


}
