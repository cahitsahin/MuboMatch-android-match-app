package com.example.mubomatch.Chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mubomatch.Library.OtherUserLibraryActivity;
import com.example.mubomatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mChatAdapter;
    private RecyclerView.LayoutManager mChatLayoutManager;

    private EditText mSendEditText;

    private ImageView mSendButtoon,mMatchPp,mBackButton;
    private TextView mMatchName;

    private String currentUserID, matchId , chatId, matchName,matchPpUrl;

    DatabaseReference mDatabaseUser,mDatabaseChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        matchId = getIntent().getExtras().getString("matchId");
        matchName = getIntent().getExtras().getString("matchName");
        matchPpUrl = getIntent().getExtras().getString("matchPpUrl");

        mMatchName = (TextView) findViewById(R.id.matchNameTitle);
        mBackButton = (ImageView) findViewById(R.id.back_button);
        mMatchPp = (ImageView) findViewById(R.id.matchPp);


        mMatchName.setText(matchName);

        Glide.with(getApplication()).load(matchPpUrl).into(mMatchPp);


        mMatchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActivity.this,OtherUserLibraryActivity.class);
                Bundle a = new Bundle();
                a.putString("otherUserID",matchId);
                a.putString("otherUserName",matchName);
                a.putString("otherUserPpUrl",matchPpUrl);
                intent.putExtras(a);
                startActivity(intent);
            }
        });

        mMatchPp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActivity.this,OtherUserLibraryActivity.class);
                Bundle a = new Bundle();
                a.putString("otherUserID",matchId);
                a.putString("otherUserName",matchName);
                a.putString("otherUserPpUrl",matchPpUrl);
                intent.putExtras(a);
                startActivity(intent);
            }
        });







        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("Connections").child("Matches").child(matchId).child("ChatId");

        mDatabaseChat = FirebaseDatabase.getInstance().getReference().child("Chats");

        getChatId();





        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);
        mChatLayoutManager = new LinearLayoutManager(ChatActivity.this);
        mRecyclerView.setLayoutManager(mChatLayoutManager);
        mChatAdapter = new ChatAdapter(getDataSetChat(),ChatActivity.this);
        mRecyclerView.setAdapter(mChatAdapter);

        mSendEditText = findViewById(R.id.send_text);
        mSendButtoon = findViewById(R.id.send_button);
        
        mSendButtoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                return;
            }
        });

    }

    private void sendMessage() {


        String sendMessageText = mSendEditText.getText().toString();

        if (!sendMessageText.isEmpty()){
            DatabaseReference newMessageDb = mDatabaseChat.push();

            Map newMessage = new HashMap();
            newMessage.put("CreatedByUser",currentUserID);
            newMessage.put("Text",sendMessageText);

            newMessageDb.setValue(newMessage);
        }
        mSendEditText.setText(null);
    }

    private void getChatId(){
        mDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    chatId = dataSnapshot.getValue().toString();
                    mDatabaseChat = mDatabaseChat.child(chatId);
                    getChatMessages();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getChatMessages() {
        mDatabaseChat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    String message = null;
                    String createdByUser = null;

                    if(dataSnapshot.child("Text").getValue()!=null){
                        message = dataSnapshot.child("Text").getValue().toString();
                    }
                    if(dataSnapshot.child("CreatedByUser").getValue()!=null){
                        createdByUser = dataSnapshot.child("CreatedByUser").getValue().toString();
                    }

                    if (message!=null && createdByUser!=null ){
                        Boolean currentUserBoolean= false;
                        if (createdByUser.equals(currentUserID)){
                            currentUserBoolean = true;
                        }
                        ChatObject newMessage = new ChatObject(message,currentUserBoolean);
                        resultsChat.add(newMessage);
                        mChatAdapter.notifyDataSetChanged();
                    }
                }
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

    private ArrayList<ChatObject> resultsChat = new ArrayList<ChatObject>() ;

    private List<ChatObject> getDataSetChat() {
        return resultsChat;
    }
}
