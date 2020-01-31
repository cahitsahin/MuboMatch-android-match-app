package com.example.mubomatch.Library;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.mubomatch.Library.LibraryArtist.LibraryMusicFragment;
import com.example.mubomatch.Library.LibraryMovie.LibarayMovieFragment;
import com.example.mubomatch.Library.LibraryBook.LibraryBookFragment;
import com.example.mubomatch.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;


public class LibraryActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private LibraryTabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView mBackButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        mBackButton = (ImageView) findViewById(R.id.back_button);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new LibraryTabAdapter(getSupportFragmentManager());
        adapter.addFragment(new LibarayMovieFragment(), "Movies");
        adapter.addFragment(new LibraryMusicFragment(), "Music");
        adapter.addFragment(new LibraryBookFragment(), "Book");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        ChangeToFragment();

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                return;
            }
        });

    }

    public void ChangeToFragment(){
        Intent intent = this.getIntent();
        if(intent !=null) {
            String strdata = intent.getExtras().getString("UniId");
            if(strdata.equals("From_Artist"))
            { viewPager.setCurrentItem(1,true); }
            if(strdata.equals("From_Book"))
            { viewPager.setCurrentItem(2,true); } }
        else { viewPager.setCurrentItem(0,true); }
    }
}
