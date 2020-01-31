package com.example.mubomatch.UserProfile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mubomatch.BookSearch.BookActivity;
import com.example.mubomatch.LoginActivity;
import com.example.mubomatch.MusicSearch.ArtistActivity;
import com.example.mubomatch.UserProfile.Artist.ProfileArtistsAdapter;
import com.example.mubomatch.UserProfile.Artist.ProfileArtistsObject;
import com.example.mubomatch.UserProfile.Books.ProfileBooksAdapter;
import com.example.mubomatch.UserProfile.Books.ProfileBooksObject;
import com.example.mubomatch.UserProfile.Movies.ProfileMoviesAdapter;
import com.example.mubomatch.UserProfile.Movies.ProfileMoviesObject;
import com.example.mubomatch.MoviesSearch.MoviesActivity;
import com.example.mubomatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class ProfileFragment extends Fragment {

    RelativeLayout l1;
    LinearLayout l2;
    Animation uptodown, downtoup;


    private RecyclerView mMovieRecyclerView;
    private RecyclerView.Adapter mMoviesAdapter;
    private RecyclerView.LayoutManager mMoviesLayoutManager;
    private RecyclerView mBookRecyclerView;
    private RecyclerView.Adapter mBookAdapter;
    private RecyclerView.LayoutManager mBookLayoutManager;
    private RecyclerView mArtistRecyclerView;
    private RecyclerView.Adapter mArtistAdapter;
    private RecyclerView.LayoutManager mArtistLayoutManager;


    private TextView mNameField, mAddMovie, mAddArtist, mAddBook, mAddMovieText, mAddArtistText, mAddBookText;

    private Button mBack, mConfirm;

    private ImageView mProfileImage, mLogout, mSetting;

    private FirebaseAuth mAuth;

    private DatabaseReference mUserDatabase;

    private String userId, name, profileImageUrl, userSex, posterUrl, age;

    private Uri resultUri;


    public ProfileFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        l1 = (RelativeLayout) v.findViewById(R.id.L1);
        l2 = (LinearLayout) v.findViewById(R.id.L2);
        uptodown = AnimationUtils.loadAnimation(getContext(), R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(getContext(), R.anim.downtoup);
        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);


        mMovieRecyclerView = (RecyclerView) v.findViewById(R.id.movie_recycler);
        mMovieRecyclerView.setHasFixedSize(true);
        mMovieRecyclerView.setNestedScrollingEnabled(false);
        mMoviesLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mMovieRecyclerView.setLayoutManager(mMoviesLayoutManager);
        mMoviesAdapter = new ProfileMoviesAdapter(getDataSetMovies(), getContext());
        mMovieRecyclerView.setAdapter(mMoviesAdapter);

        mArtistRecyclerView = (RecyclerView) v.findViewById(R.id.artist_recycler);
        mArtistRecyclerView.setHasFixedSize(true);
        mArtistRecyclerView.setNestedScrollingEnabled(false);
        mArtistLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mArtistRecyclerView.setLayoutManager(mArtistLayoutManager);
        mArtistAdapter = new ProfileArtistsAdapter(getDataSetArtist(), getContext());
        mArtistRecyclerView.setAdapter(mArtistAdapter);

        mBookRecyclerView = (RecyclerView) v.findViewById(R.id.book_recycler);
        mBookRecyclerView.setHasFixedSize(true);
        mBookRecyclerView.setNestedScrollingEnabled(false);
        mBookLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mBookRecyclerView.setLayoutManager(mBookLayoutManager);
        mBookAdapter = new ProfileBooksAdapter(getDataSetBooks(), getContext());
        mBookRecyclerView.setAdapter(mBookAdapter);


        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);


        mNameField = (TextView) v.findViewById(R.id.name);

        mProfileImage = (ImageView) v.findViewById(R.id.profileImage);

        mLogout = (ImageView) v.findViewById(R.id.logoutButton);
        mSetting = (ImageView) v.findViewById(R.id.settingButton);
        mAddMovie = (TextView) v.findViewById(R.id.movie_add);
        mAddArtist = (TextView) v.findViewById(R.id.artist_add);
        mAddBook = (TextView) v.findViewById(R.id.book_add);
        mAddMovieText = (TextView) v.findViewById(R.id.add_movie_text);
        mAddArtistText = (TextView) v.findViewById(R.id.add_artist_text);
        mAddBookText = (TextView) v.findViewById(R.id.add_book_text);
        getUserInfo();
        getUserMovie();
        getBookInfo();
        getArtistInfo();


        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        mAddArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ArtistActivity.class);
                startActivity(intent);

            }
        });

        mAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BookActivity.class);
                startActivity(intent);

            }
        });
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                return;

            }
        });

        mAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MoviesActivity.class);
                startActivity(intent);

            }
        });


        return v;


    }

    public void getUserMovie() {

        mUserDatabase.child("Movies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                resultsMovies.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    posterUrl = String.valueOf(dataSnapshot1.child("PosterUrl").getValue());
                    ProfileMoviesObject obj1 = new ProfileMoviesObject(posterUrl);


                    resultsMovies.add(obj1);

                    mMoviesAdapter.notifyDataSetChanged();
                }


                if (resultsMovies.isEmpty()) {
                    mAddMovieText.setVisibility(View.VISIBLE);
                } else mAddMovieText.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void getBookInfo() {


        mUserDatabase.child("Books").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                resultsBook.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    posterUrl = String.valueOf(dataSnapshot1.child("PosterUrl").getValue());

                    ProfileBooksObject obj3 = new ProfileBooksObject(posterUrl);

                    resultsBook.add(obj3);

                    mBookAdapter.notifyDataSetChanged();
                }


                if (resultsBook.isEmpty()) {
                    mAddBookText.setVisibility(View.VISIBLE);
                } else mAddBookText.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getArtistInfo() {

        mUserDatabase.child("Artist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                resultArtist.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    posterUrl = String.valueOf(dataSnapshot1.child("PosterUrl").getValue());

                    ProfileArtistsObject obj3 = new ProfileArtistsObject(posterUrl);

                    resultArtist.add(obj3);

                    mArtistAdapter.notifyDataSetChanged();
                }


                if (resultArtist.isEmpty()) {
                    mAddArtistText.setVisibility(View.VISIBLE);
                } else mAddArtistText.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void getUserInfo() {
        mUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("Name") != null) {
                        name = map.get("Name").toString();
                        mNameField.setText(name);
                    }


                    if (map.get("ProfileImageUrl") != null) {
                        profileImageUrl = map.get("ProfileImageUrl").toString();
                        switch (profileImageUrl) {
                            case "Default":
                                Glide.with(getActivity().getApplication()).load(R.mipmap.ic_launcher).into(mProfileImage);
                                break;
                            default:
                                Glide.with(getActivity().getApplication()).load(profileImageUrl).into(mProfileImage);
                                break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private ArrayList<ProfileMoviesObject> resultsMovies = new ArrayList<ProfileMoviesObject>();

    public List<ProfileMoviesObject> getDataSetMovies() {
        return resultsMovies;
    }

    private ArrayList<ProfileArtistsObject> resultArtist = new ArrayList<ProfileArtistsObject>();

    public List<ProfileArtistsObject> getDataSetArtist() {
        return resultArtist;
    }

    private ArrayList<ProfileBooksObject> resultsBook = new ArrayList<ProfileBooksObject>();

    public List<ProfileBooksObject> getDataSetBooks() {
        return resultsBook;
    }


}
