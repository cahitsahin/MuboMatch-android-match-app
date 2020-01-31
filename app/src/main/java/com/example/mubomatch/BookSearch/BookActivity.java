package com.example.mubomatch.BookSearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.mubomatch.MoviesSearch.MovieObject;
import com.example.mubomatch.MoviesSearch.MovieProcesses;
import com.example.mubomatch.MoviesSearch.MoviesActivity;
import com.example.mubomatch.MoviesSearch.SearchMoviesAdapter;
import com.example.mubomatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {


    private BookProcesses proc;
    private List<BookObject> bookList;
    private TextView Title,nothingText;
    private RecyclerView mSearchBookRecyclerView;
    private RecyclerView.Adapter mSearchBookAdapter;
    private RecyclerView.LayoutManager mSearchBookLayoutManager;
    private SearchView mSearchView;
    private ImageView mBackButton,addBook,deleteBook,nothingImage;
    public FirebaseAuth mAuth;
    private String currentUserId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);


        currentUserId = mAuth.getInstance().getUid();




        mSearchBookRecyclerView = (RecyclerView) findViewById(R.id.bookSearchRecyclerView);
        mSearchBookRecyclerView.setNestedScrollingEnabled(false);
        mSearchBookRecyclerView.setHasFixedSize(true);
        mSearchBookLayoutManager = new LinearLayoutManager(BookActivity.this);
        mSearchBookRecyclerView.setLayoutManager(mSearchBookLayoutManager);
        mSearchBookAdapter = new SearchBooksAdapter(getDataSetBook(),BookActivity.this);
        mSearchBookRecyclerView.setAdapter(mSearchBookAdapter);

        mSearchView = (SearchView) findViewById(R.id.book_search);

        mBackButton = (ImageView) findViewById(R.id.back_button);

        nothingText = (TextView) findViewById(R.id.noBookText);
        nothingImage = (ImageView) findViewById(R.id.noBookImage);



        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateScore();
                finish();
                return;
            }
        });




        proc = new BookProcesses();




        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                resultsSearchBook.clear();

                bookList = proc.searchBooks(s);

                if (bookList.size()<1){
                    nothingText.setVisibility(View.VISIBLE);
                    nothingImage.setVisibility(View.VISIBLE);
                }
                else {
                    nothingText.setVisibility(View.GONE);
                    nothingImage.setVisibility(View.GONE);
                }

                for (int i=0 ; i< bookList.size();i++){

                    BookObject book = bookList.get(i);


                    resultsSearchBook.add(book);
                    mSearchBookAdapter.notifyDataSetChanged();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {


                return false;
            }
        });



    }

    private void calculateScore() {
        String id = currentUserId;
        String movieScoreUrl = "https://us-central1-second-project-258608.cloudfunctions.net/calculateBookScore?id=ID";
        try {
            URL url = new URL(movieScoreUrl.replaceAll("ID", id));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.getInputStream();
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<BookObject> resultsSearchBook = new ArrayList<BookObject>() ;

    private List<BookObject> getDataSetBook() {
        return resultsSearchBook;
    }

}