package com.example.mubomatch.UserProfile;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mubomatch.R;
import com.example.mubomatch.UserProfile.Movies.ProfileMoviesAdapter;
import com.example.mubomatch.UserProfile.Movies.ProfileMoviesObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OtherUserActivity extends AppCompatActivity {

    private String otherUserID,otherUserName,otherUserProfileImageUrl;
    private TextView mOtherUserNameField;
    private DatabaseReference mOtherUserDatabase;
    private RecyclerView mOtherUserMovieRecyclerView;
    private RecyclerView.LayoutManager mOtherUserMoviesLayoutManager;
    private RecyclerView.Adapter mOtherUserMoviesAdapter;
    private ImageView mOtherUserProfileImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);

        otherUserID = getIntent().getExtras().getString("otherUserID");


        mOtherUserNameField = (TextView) findViewById(R.id.otherUserName);
        mOtherUserProfileImage = (ImageView) findViewById(R.id.otherProfileImage);

        mOtherUserMovieRecyclerView = (RecyclerView) findViewById(R.id.other_user_movie_recycler);
        mOtherUserMovieRecyclerView.setHasFixedSize(true);
        mOtherUserMovieRecyclerView.setNestedScrollingEnabled(false);
        mOtherUserMoviesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mOtherUserMovieRecyclerView.setLayoutManager(mOtherUserMoviesLayoutManager);
        mOtherUserMoviesAdapter = new ProfileMoviesAdapter(getDataSetMovies(),this);
        mOtherUserMovieRecyclerView.setAdapter(mOtherUserMoviesAdapter);



        mOtherUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(otherUserID);

        getUserInfo();



        mOtherUserDatabase.child("Movies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String posterUrl = String.valueOf(dataSnapshot1.child("PosterUrl").getValue());
                    ProfileMoviesObject obj = new ProfileMoviesObject(posterUrl);
                    resultsOtherUserMovies.add(obj);

                    mOtherUserMoviesAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void getUserInfo() {
        mOtherUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("Name")!=null){
                        otherUserName = map.get("Name").toString();
                        mOtherUserNameField.setText(otherUserName);
                    }

                    if(map.get("ProfileImageUrl")!=null){
                        otherUserProfileImageUrl = map.get("ProfileImageUrl").toString();
                        switch(otherUserProfileImageUrl){
                            case "Default":
                                Glide.with(getApplication()).load(R.mipmap.ic_launcher).into(mOtherUserProfileImage);
                                break;
                            default:
                                Glide.with(getApplication()).load(otherUserProfileImageUrl).into(mOtherUserProfileImage);
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


    private ArrayList<ProfileMoviesObject> resultsOtherUserMovies = new ArrayList<ProfileMoviesObject>() ;

    private List<ProfileMoviesObject> getDataSetMovies() {
        return resultsOtherUserMovies;
    }


}

