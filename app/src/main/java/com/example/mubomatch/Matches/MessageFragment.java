package com.example.mubomatch.Matches;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.mubomatch.Matches.MatchesMessage.MatchesMessageAdapter;
import com.example.mubomatch.Matches.MatchesMessage.MatchesMessageObject;
import com.example.mubomatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MessageFragment extends Fragment {

    CardView fr,mr;
    Animation uptodown,downtoup;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mMatchesAdapter;
    private RecyclerView.LayoutManager mMathesLayoutManager;

    private RecyclerView mMessageRecyclerView;
    private RecyclerView.Adapter mMessageAdapter;
    private RecyclerView.LayoutManager mMessageLayoutManager;

    private String lastMessageUserID,lastMessageUsername,lastMessagePpUrl,lastMessage;



    private String currentUserID,chatId;
    private TextView noMatchText;
    private DatabaseReference mDatabaseUser,mDatabaseChat,mOtherUserDatabase;


    public MessageFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_matches, container, false);

        mDatabaseChat = FirebaseDatabase.getInstance().getReference().child("Chats");


        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.userRecyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mMathesLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mMathesLayoutManager);
        mMatchesAdapter = new MessageAdapter(getDataSetMatches(),getContext());
        mRecyclerView.setAdapter(mMatchesAdapter);



        mMessageRecyclerView = (RecyclerView) rootView.findViewById(R.id.userMessageRecyclerView);
        mMessageRecyclerView.setNestedScrollingEnabled(false);
        mMessageRecyclerView.setHasFixedSize(true);
        mMessageLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mMessageRecyclerView.setLayoutManager(mMessageLayoutManager);
        mMessageAdapter = new MatchesMessageAdapter(getDataSetMessage(),getContext());
        mMessageRecyclerView.setAdapter(mMessageAdapter);

        noMatchText = (TextView) rootView.findViewById(R.id.noMatchesText);



        fr = (CardView) rootView.findViewById(R.id.F1);
        mr = (CardView) rootView.findViewById(R.id.F2);

        uptodown= AnimationUtils.loadAnimation(getContext(),R.anim.uptodown);
        downtoup= AnimationUtils.loadAnimation(getContext(),R.anim.downtoup);
        fr.setAnimation(uptodown);
        mr.setAnimation(downtoup);

        getUserMatchID();
        getUserMessage();













        return rootView;
    }

    private void getUserMessage() {


    }


    private void getUserMatchID() {
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("Connections").child("Matches");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for(DataSnapshot match: dataSnapshot.getChildren()){
                        FetchMatchInformation(match.getKey());
                        getChatId(match.getKey());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getChatId(String userId){
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("Connections").child("Matches").child(userId).child("ChatId");
        mOtherUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);


        mOtherUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    lastMessageUserID = dataSnapshot.getKey();
                    lastMessageUsername ="";
                    lastMessagePpUrl ="";

                    if(dataSnapshot.child("Name").getValue()!=null){
                        lastMessageUsername = dataSnapshot.child("Name").getValue().toString();

                    }

                    if(dataSnapshot.child("ProfileImageUrl").getValue()!=null){
                        lastMessagePpUrl = dataSnapshot.child("ProfileImageUrl").getValue().toString();

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        mDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    chatId = dataSnapshot.getValue().toString();
                    mDatabaseChat = mDatabaseChat.child(chatId);

                    Query lastQuery = mDatabaseChat.orderByKey().limitToLast(1);
                    lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot data : dataSnapshot.getChildren())
                            {
                                String lastMessage = data.child("Text").getValue().toString();
                                MatchesMessageObject objL = new MatchesMessageObject(lastMessageUserID, lastMessageUsername,lastMessagePpUrl,lastMessage);
                                resultsMessage.add(objL);
                                mMessageAdapter.notifyDataSetChanged();

                                if (resultsMatches.isEmpty()){
                                    noMatchText.setVisibility(View.VISIBLE);
                                }
                                else {noMatchText.setVisibility(View.GONE);}

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }});
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


    private void FetchMatchInformation(String key){
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String userId= dataSnapshot.getKey();
                    String name="";
                    String profileImageUrl="";

                    if(dataSnapshot.child("Name").getValue()!=null){
                        name = dataSnapshot.child("Name").getValue().toString();

                    }

                    if(dataSnapshot.child("ProfileImageUrl").getValue()!=null){
                        profileImageUrl = dataSnapshot.child("ProfileImageUrl").getValue().toString();

                    }

                    MessageObject obj = new MessageObject(userId, name , profileImageUrl);
                    resultsMatches.add(obj);
                    mMatchesAdapter.notifyDataSetChanged();






                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private ArrayList<MessageObject> resultsMatches = new ArrayList<MessageObject>() ;

    private List<MessageObject> getDataSetMatches() {
        return resultsMatches;
    }

    private ArrayList<MatchesMessageObject> resultsMessage = new ArrayList<MatchesMessageObject>() ;

    private List<MatchesMessageObject> getDataSetMessage() {
        return resultsMessage;
    }



    }
