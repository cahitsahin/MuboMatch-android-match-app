package com.example.mubomatch.Library;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.mubomatch.Library.LibraryArtist.OtherUserLibraryMusicFragment;
import com.example.mubomatch.Library.LibraryBook.OtherUserLibraryBookFragment;
import com.example.mubomatch.Library.LibraryMovie.OtherUserLibraryMovieFragment;
import com.example.mubomatch.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class OtherUserLibraryActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private LibraryTabAdapter OtherUserAdapter;
    private TabLayout otherUserTabLayout;
    private ViewPager otherUserViewPager;
    private String otherUserID,otherUserName,otherUserPp;
    private ImageView mBackButton,mOtherUserProfileImage;
    private TextView mOtherUserLibraryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_library);


        otherUserID = getIntent().getExtras().getString("otherUserID");
        otherUserName = getIntent().getExtras().getString("otherUserName");
        otherUserPp = getIntent().getExtras().getString("otherUserPpUrl");

        mOtherUserLibraryName = (TextView) findViewById(R.id.otherUserNameTitle);
        mOtherUserProfileImage=(ImageView) findViewById(R.id.otherUserPP);
        mBackButton=(ImageView) findViewById(R.id.back_button);

        mOtherUserLibraryName.setText(otherUserName+" 's Library");
        Glide.with(getApplication()).load(otherUserPp).into(mOtherUserProfileImage);

        Bundle bundle = new Bundle();
        bundle.putString("otherUserID", otherUserID);
        OtherUserLibraryMovieFragment fragobj = new OtherUserLibraryMovieFragment();
        fragobj.setArguments(bundle);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                return;

            }
        });



        otherUserViewPager = (ViewPager) findViewById(R.id.otherUserViewPager);
        otherUserTabLayout = (TabLayout) findViewById(R.id.otherUserTabLayout);
        OtherUserAdapter = new LibraryTabAdapter(getSupportFragmentManager());
        OtherUserAdapter.addFragment(new OtherUserLibraryMovieFragment(), "Movies");
        OtherUserAdapter.addFragment(new OtherUserLibraryMusicFragment(), "Music");
        OtherUserAdapter.addFragment(new OtherUserLibraryBookFragment(), "Book");
        otherUserViewPager.setAdapter(OtherUserAdapter);
        otherUserTabLayout.setupWithViewPager(otherUserViewPager);
    }

    public String getMyData() {
        return otherUserID;
    }
}
