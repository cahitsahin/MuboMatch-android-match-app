package com.example.mubomatch.Matches;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mubomatch.Chat.ChatActivity;
import com.example.mubomatch.R;

public class MessageViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mMatchId,mMatchName,mMatchPpUrl;
    public ImageView mMatchImage;
    public MessageViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);


        mMatchId = (TextView) itemView.findViewById(R.id.Matches_Id);
        mMatchName = (TextView) itemView.findViewById(R.id.Matches_Name);
        mMatchImage = (ImageView) itemView.findViewById(R.id.Matches_Image);
        mMatchPpUrl = (TextView) itemView.findViewById(R.id.Matches_PpUrl);
    }
    @Override
    public void onClick(View view){
        Intent intent = new Intent(view.getContext(), ChatActivity.class);
        Bundle b = new Bundle();
        b.putString("matchId",mMatchId.getText().toString());
        b.putString("matchName",mMatchName.getText().toString());
        b.putString("matchPpUrl",mMatchPpUrl.getText().toString());
        intent.putExtras(b);
        view.getContext().startActivity(intent);

    }
}
