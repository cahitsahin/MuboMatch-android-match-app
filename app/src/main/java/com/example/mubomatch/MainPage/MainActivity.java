package com.example.mubomatch.MainPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mubomatch.IsLibraryEnoughFragment;
import com.example.mubomatch.LoginActivity;
import com.example.mubomatch.MainPage.TabAdapter;
import com.example.mubomatch.MatcherFragment;
import com.example.mubomatch.Matches.MessageFragment;
import com.example.mubomatch.MoviesSearch.MoviesActivity;
import com.example.mubomatch.R;
import com.example.mubomatch.RegisterActivity;
import com.example.mubomatch.UserProfile.Artist.ProfileArtistsObject;
import com.example.mubomatch.UserProfile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    public ArrayList<String> idsMovie,idsMusic,idsBook;
    public ArrayList<Double> similaritiesMovie,similaritiesMusic,similaritiesBook;
    FirebaseAuth mAuth;
    DatabaseReference userDb;
    TextView minMovie, minMusic, minBook;
    private String userID;
    Boolean isBookEnough, isMovieEnough, isMusicEnough;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        minMovie = (TextView) findViewById(R.id.movie);
        minBook = (TextView) findViewById(R.id.book);
        minMusic = (TextView) findViewById(R.id.music);

        isLibraryEnough();
        getUserMatch();


        userDb.child("MinMatchValues").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

                    if (map.get("MinMovie") != null) {
                        String minM = map.get("MinMovie").toString();
                        minMovie.setText(minM);
                    }

                    if (map.get("MinMusic") != null) {
                        String minMu = map.get("MinMusic").toString();
                        minMusic.setText(minMu);
                    }

                    if (map.get("MinBook") != null) {
                        String minB = map.get("MinBook").toString();
                        minBook.setText(minB);
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(new ProfileFragment());
    }

    public void isLibraryEnough() {
        userDb.child("Movies").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int movieC = (int) dataSnapshot.getChildrenCount();
                if (movieC > 4) {
                    isMovieEnough = true;
                } else isMovieEnough = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        userDb.child("Artist").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int artistC = (int) dataSnapshot.getChildrenCount();
                if (artistC > 4) {
                    isMusicEnough = true;
                } else
                    isMusicEnough = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        userDb.child("Books").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int bookC = (int) dataSnapshot.getChildrenCount();
                if (bookC > 4) {
                    isBookEnough = true;
                } else
                    isBookEnough = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void getUserMatch() {

        String id = userID;
        String sim = "-100";


        String movieScoreUrl = "https://us-central1-second-project-258608.cloudfunctions.net/movieMatch?id=ID&sim=SIM";
        StringBuffer response = new StringBuffer();
        try {
            movieScoreUrl = movieScoreUrl.replaceAll("ID", id);
            movieScoreUrl = movieScoreUrl.replaceAll("SIM", sim);
            URL url = new URL(movieScoreUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream stream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader buffer = new BufferedReader(reader);
            String line;
            while ((line = buffer.readLine()) != null) {
                response.append(line);
            }
            buffer.close();
            connection.disconnect();

            similaritiesMovie = new ArrayList<Double>();
            idsMovie = new ArrayList<String>();
            String json = response.toString();
            if (!json.equals("")) {
                String[] pairs = json.split(",");
                for (int i = 0; i < pairs.length; i++) {
                    String pair = pairs[i];
                    String[] keyValue = pair.split(":");
                    idsMovie.add(keyValue[0]);
                    similaritiesMovie.add(Double.valueOf(keyValue[1]));
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String bookScoreUrl = "https://us-central1-second-project-258608.cloudfunctions.net/bookMatch?id=ID&sim=SIM";
        StringBuffer responseBook = new StringBuffer();
        try {
            bookScoreUrl = bookScoreUrl.replaceAll("ID", id);
            bookScoreUrl = bookScoreUrl.replaceAll("SIM", sim);
            URL urlBook = new URL(bookScoreUrl);
            HttpURLConnection connectionBook = (HttpURLConnection) urlBook.openConnection();
            InputStream streamBook = connectionBook.getInputStream();
            InputStreamReader readerBook = new InputStreamReader(streamBook);
            BufferedReader bufferBook = new BufferedReader(readerBook);
            String lineBook;
            while ((lineBook = bufferBook.readLine()) != null) {
                responseBook.append(lineBook);
            }
            bufferBook.close();
            connectionBook.disconnect();

            similaritiesBook = new ArrayList<Double>();
            idsBook = new ArrayList<String>();
            String jsonBook = responseBook.toString();
            if (!jsonBook.equals("")) {
                String[] pairsBook = jsonBook.split(",");
                for (int i = 0; i < pairsBook.length; i++) {
                    String pairBook = pairsBook[i];
                    String[] keyValue = pairBook.split(":");
                    idsBook.add(keyValue[0]);
                    similaritiesBook.add(Double.valueOf(keyValue[1]));

                }
            }

            Log.w("dikkatBook", idsBook.toString());
            Log.w("dıkkatBook2", similaritiesBook.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String musicScoreUrl = "https://us-central1-second-project-258608.cloudfunctions.net/musicMatch?id=ID&sim=SIM";
        StringBuffer responseMusic = new StringBuffer();
        try {

            musicScoreUrl = musicScoreUrl.replaceAll("ID", id);
            musicScoreUrl = musicScoreUrl.replaceAll("SIM", sim);
            URL urlMusic = new URL(musicScoreUrl);
            HttpURLConnection connectionMusic = (HttpURLConnection) urlMusic.openConnection();
            InputStream streamMusic = connectionMusic.getInputStream();
            InputStreamReader readerMusic = new InputStreamReader(streamMusic);
            BufferedReader bufferMusic = new BufferedReader(readerMusic);
            String lineMusic;
            while ((lineMusic = bufferMusic.readLine()) != null) {
                responseMusic.append(lineMusic);
            }
            bufferMusic.close();
            connectionMusic.disconnect();

            similaritiesMusic = new ArrayList<Double>();
            idsMusic = new ArrayList<String>();
            String jsonMusic = responseMusic.toString();
            if (!jsonMusic.equals("")) {
                String[] pairsMusic = jsonMusic.split(",");
                for (int i = 0; i < pairsMusic.length; i++) {
                    String pairMusic = pairsMusic[i];
                    String[] keyValue = pairMusic.split(":");
                    idsMusic.add(keyValue[0]);
                    similaritiesMusic.add(Double.valueOf(keyValue[1]));

                }
            }

            Log.w("dikkatMusic", idsMusic.toString());
            Log.w("dıkkatMusic2", similaritiesMusic.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public String getMovieValue() {

        String minMov = minMovie.getText().toString();

        return minMov;
    }

    public String getMusicValue() {
        String minMus = minMusic.getText().toString();

        return minMus;
    }

    public String getBookValue() {
        String minBoo = minBook.getText().toString();

        return minBoo;
    }


    public ArrayList<String> getIdsMovie() {

        return idsMovie;
    }
    public ArrayList<String> getIdsMusic() {

        return idsMusic;
    }
    public ArrayList<String> getIdsBook() {

        return idsBook;
    }

    public ArrayList<Double> getSimilaritiesMovie() {

        return similaritiesMovie;
    }

    public ArrayList<Double> getSimilaritiesMusic() {

        return similaritiesMusic;
    }
    public ArrayList<Double> getSimilaritiesBook() {

        return similaritiesBook;
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_profile:
                            openFragment(new ProfileFragment());
                            return true;
                        case R.id.action_match:
                            isLibraryEnough();
                            if (isMovieEnough && isBookEnough && isMusicEnough) {
                                openFragment(new MatcherFragment());
                            } else
                                openFragment(new IsLibraryEnoughFragment());
                            Log.w("boolenB", String.valueOf(isBookEnough));
                            Log.w("boolenM", String.valueOf(isMovieEnough));
                            Log.w("boolenA", String.valueOf(isMusicEnough));

                            return true;
                        case R.id.action_message:
                            openFragment(new MessageFragment());
                            return true;
                    }
                    return false;
                }
            };
}