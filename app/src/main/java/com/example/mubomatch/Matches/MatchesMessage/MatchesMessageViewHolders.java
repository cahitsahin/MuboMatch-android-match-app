package com.example.mubomatch.Matches.MatchesMessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mubomatch.Chat.ChatActivity;
import com.example.mubomatch.R;

public class MatchesMessageViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mMatchMId,mMatchMName,mMatchMPpUrl,mMatchLastM;
    public ImageView mMatchMImage;
    public MatchesMessageViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);


        mMatchMId = (TextView) itemView.findViewById(R.id.Matches_MId);
        mMatchMName = (TextView) itemView.findViewById(R.id.Matches_MName);
        mMatchMImage = (ImageView) itemView.findViewById(R.id.Matches_MImage);
        mMatchMPpUrl = (TextView) itemView.findViewById(R.id.Matches_MPpUrl);
        mMatchLastM= (TextView) itemView.findViewById(R.id.lastMessage);

    }
    @Override
    public void onClick(View view){
        Intent intent = new Intent(view.getContext(), ChatActivity.class);
        Bundle b = new Bundle();
        b.putString("matchId",mMatchMId.getText().toString());
        b.putString("matchName",mMatchMName.getText().toString());
        b.putString("matchPpUrl",mMatchMPpUrl.getText().toString());
        intent.putExtras(b);
        view.getContext().startActivity(intent);

    }
}
