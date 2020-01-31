package com.example.mubomatch;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mubomatch.Cards.CardsArrayAdapter;
import com.example.mubomatch.Cards.CardsObject;
import com.example.mubomatch.Library.OtherUserLibraryActivity;
import com.example.mubomatch.MainPage.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import java.util.ArrayList;
import java.util.List;


public class MatcherFragment extends Fragment {


    private com.example.mubomatch.Cards.CardsArrayAdapter CardsArrayAdapter;
    private FirebaseAuth mAuth;
    private String currentUid;
    private String minMovie = "-100", minBook = "-100", minMusic = "-100";
    private TextView mNoMatchText;
    private DatabaseReference usersDb;
    private List<CardsObject> rowItems;
    private ArrayList<String> finalMatches;
    private ArrayList<String> idsMovie,idsMusic,idsBook;
    private ArrayList<Double> similaritiesMovie,similaritiesMusic,similaritiesBook;


    public MatcherFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootV = inflater.inflate(R.layout.fragment_matcher, container, false);

        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        mNoMatchText = (TextView) rootV.findViewById(R.id.noMatchText);
        mAuth = FirebaseAuth.getInstance();
        currentUid = mAuth.getCurrentUser().getUid();
        rowItems = new ArrayList<CardsObject>();

        getUserMinValue();
        getUserMatch();


        CardsArrayAdapter = new CardsArrayAdapter(getContext(), R.layout.item_cards, rowItems);
        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) rootV.findViewById(R.id.frame);
        flingContainer.setAdapter(CardsArrayAdapter);

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                CardsArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {

                CardsObject obj = (CardsObject) dataObject;
                String userId = obj.getUserID();
                usersDb.child(userId).child("Connections").child("No").child(currentUid).setValue(true);
                Toast.makeText(getContext(), "Unlike", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                CardsObject obj = (CardsObject) dataObject;
                String userId = obj.getUserID();
                usersDb.child(userId).child("Connections").child("Yes").child(currentUid).setValue(true);
                isConnectionMatch(userId);
                Toast.makeText(getContext(), "Like", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });


        // OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                CardsObject obj = (CardsObject) dataObject;
                String userId = obj.getUserID();
                String userPpUrl = obj.getProfileImageUrl();
                String userName = obj.getUserName();
                Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), OtherUserLibraryActivity.class);
                Bundle a = new Bundle();
                a.putString("otherUserID", userId);
                a.putString("otherUserName", userName);
                a.putString("otherUserPpUrl", userPpUrl);
                intent.putExtras(a);
                getContext().startActivity(intent);
            }
        });

        return rootV;
    }

    private void getUserMinValue() {

        MainActivity activity = (MainActivity) getActivity();
        minMovie = activity.getMovieValue();
        minMusic = activity.getMusicValue();
        minBook = activity.getBookValue();

        idsMovie=activity.getIdsMovie();
        idsBook=activity.getIdsBook();
        idsMusic=activity.getIdsMusic();

        similaritiesBook=activity.getSimilaritiesBook();
        similaritiesMovie=activity.getSimilaritiesMovie();
        similaritiesMusic=activity.getSimilaritiesMusic();

    }


    private void isConnectionMatch(String userId) {
        DatabaseReference currentUserConnectionsDb = usersDb.child(currentUid).child("Connections").child("Yes").child(userId);
        currentUserConnectionsDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(getContext(), "New Match", Toast.LENGTH_SHORT).show();

                    String key = FirebaseDatabase.getInstance().getReference().child("Chat").push().getKey();

                    usersDb.child(dataSnapshot.getKey()).child("Connections").child("Matches").child(currentUid).child("ChatId").setValue(key);

                    usersDb.child(currentUid).child("Connections").child("Matches").child(dataSnapshot.getKey()).child("ChatId").setValue(key);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    private void getUserMatch() {



        checkUserPreferences();

    }


    private String userSex;
    private String PreferenceSex;

    public void checkUserPreferences() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userDb = usersDb.child(user.getUid());
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getKey().equals(user.getUid())) {
                    if (dataSnapshot.exists()) {
                        if (dataSnapshot.child("Preference").getValue() != null) {
                            userSex = dataSnapshot.child("Preference").getValue().toString();
                            switch (userSex) {
                                case "mf":
                                    PreferenceSex = "MaleFemale";
                                    break;
                                case "m":
                                    PreferenceSex = "Male";
                                    break;

                                case "f":
                                    PreferenceSex = "Female";
                                    break;


                            }
                            GetUserMatch();
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public void GetUserMatch() {

        finalMatches = new ArrayList<String>(idsMovie);
        finalMatches.retainAll(idsMusic);
        finalMatches.retainAll(idsBook);


        Log.w("dikkatMatches", finalMatches.toString());
        Log.w("dikkatMMovie", idsMovie.toString());


        rowItems.clear();
        int m;
        for (m = 0; m < finalMatches.size(); m++) {

            final String otherUserId = finalMatches.get(m);
            final Double MovieSimilarity = similaritiesMovie.get(idsMovie.indexOf(otherUserId));
            final Double BookSimilarity = similaritiesBook.get(idsBook.indexOf(otherUserId));
            final Double MusicSimilarity = similaritiesMusic.get(idsMusic.indexOf(otherUserId));


            usersDb.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.getKey().equals(otherUserId)) {
                        if (dataSnapshot.child("Sex").getValue() != null) {
                            if (PreferenceSex.equals("MaleFemale")) {
                                if (dataSnapshot.exists() && !dataSnapshot.child("Connections").child("Yes").hasChild(currentUid) && !dataSnapshot.child("Connections").child("No").hasChild(currentUid)) {

                                    String age = dataSnapshot.child("BirthDate").getValue().toString();
                                    String lastFourDigits = age.substring(age.length() - 4);
                                    int mAge = 2020 - Integer.parseInt(lastFourDigits);

                                    CardsObject Item = new CardsObject(dataSnapshot.getKey(), dataSnapshot.child("Name").getValue().toString(), mAge, dataSnapshot.child("ProfileImageUrl").getValue().toString(), MovieSimilarity, BookSimilarity, MusicSimilarity);
                                    if (MovieSimilarity >= Integer.parseInt(minMovie) && BookSimilarity >= Integer.parseInt(minBook) && MusicSimilarity >= Integer.parseInt(minMusic)) {
                                        rowItems.add(Item);
                                        Log.d("dik", rowItems.toString());
                                    }

                                }
                            } else {
                                if (dataSnapshot.exists() && !dataSnapshot.child("Connections").child("Yes").hasChild(currentUid) && !dataSnapshot.child("Connections").child("No").hasChild(currentUid) && dataSnapshot.child("Sex").getValue().toString().equals(PreferenceSex)) {
                                    String ProfileImageUrl = "Default";
                                    if (!dataSnapshot.child("ProfileImageUrl").getValue().equals("Default")) {
                                        ProfileImageUrl = dataSnapshot.child("ProfileImageUrl").getValue().toString();
                                    }

                                    String age = dataSnapshot.child("BirthDate").getValue().toString();
                                    String lastFourDigits = age.substring(age.length() - 4);
                                    int mAge = 2020 - Integer.parseInt(lastFourDigits);

                                    CardsObject Item = new CardsObject(dataSnapshot.getKey(), dataSnapshot.child("Name").getValue().toString(), mAge, ProfileImageUrl, MovieSimilarity, BookSimilarity, MusicSimilarity);
                                    if (MovieSimilarity >= Integer.parseInt(minMovie) && BookSimilarity >= Integer.parseInt(minBook) && MusicSimilarity >= Integer.parseInt(minMusic)) {
                                        rowItems.add(Item);
                                        Log.d("dik", rowItems.toString());
                                    }
                                }
                            }
                        }
                    }

                    if (rowItems.isEmpty()) {
                        mNoMatchText.setVisibility(View.VISIBLE);

                    } else mNoMatchText.setVisibility(View.GONE);
                    CardsArrayAdapter.notifyDataSetChanged();

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });


        }


    }


}

