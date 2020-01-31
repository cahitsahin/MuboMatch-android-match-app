package com.example.mubomatch.Library.LibraryArtist;

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


public class OtherUserLibraryMusicViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView mArtistPoster;
    public TextView mArtistTitle,mArtistGenre,mArtistId,mArtistUrl;
    public TextView mMatchText;
    public String CurrentUserId;
    public FirebaseAuth mAuth;
    public LinearLayout mArtistLayout;
    public String artistId,artistUrl,artistTitle,artistGenre;
    public Button mAddArtist,mDeleteArtist;


    DatabaseReference mDatabaseUser,mDatabaseMovies;



    public OtherUserLibraryMusicViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        CurrentUserId = mAuth.getInstance().getUid();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUserId).child("Artist");




        mArtistPoster = (ImageView) itemView.findViewById(R.id.artist_library_poster);
        mArtistTitle = (TextView) itemView.findViewById(R.id.artist_library_name);
        mArtistId= (TextView) itemView.findViewById(R.id.artist_library_id);
        mArtistUrl=(TextView) itemView.findViewById(R.id.artistLibraryUrl);
        mAddArtist=(Button) itemView.findViewById(R.id.add_artist_library_button);
        mDeleteArtist=(Button) itemView.findViewById(R.id.delete_artist_library_button);
        mArtistGenre=(TextView)itemView.findViewById(R.id.artist_library_genre);
        mArtistLayout=(LinearLayout) itemView.findViewById(R.id.Lmusic);
        mMatchText = (TextView) itemView.findViewById(R.id.match_text);



        mDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                artistId = mArtistId.getText().toString();

                if (dataSnapshot.hasChild(artistId)) {
                    mArtistLayout.setBackgroundResource(R.drawable.match_item_gradient);
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
