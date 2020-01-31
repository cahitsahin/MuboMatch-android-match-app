package com.example.mubomatch.Cards;

import android.content.Context;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mubomatch.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.zip.Inflater;

import at.grabner.circleprogress.CircleProgressView;

public class CardsArrayAdapter extends ArrayAdapter<CardsObject> {


    Context context;
    Double or,or1,or2;

    public CardsArrayAdapter(Context context, int resourceId, List<CardsObject> items){
        super(context,resourceId,items);

    }

    public View getView(int position, View convertView, ViewGroup parent){

        CardsObject card_item = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cards, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        CircleProgressView mMovieCircleView = (CircleProgressView) convertView.findViewById(R.id.circleMovieView);
        CircleProgressView mArtistCircleView = (CircleProgressView) convertView.findViewById(R.id.circleArtistView);
        CircleProgressView mBookCircleView = (CircleProgressView) convertView.findViewById(R.id.circleBookView);




        or= card_item.getMovieSimilarity();
        int i= Integer.valueOf(or.intValue());

        or1= card_item.getBookSimilarity();
        int j= Integer.valueOf(or1.intValue());

        or2= card_item.getMusicSimilarity();
        int k= Integer.valueOf(or2.intValue());

        mMovieCircleView.setValueAnimated(i);
        mArtistCircleView.setValueAnimated(k);
        mBookCircleView.setValueAnimated(j);






        name.setText(card_item.getUserName()+", "+card_item.getAge());
        switch (card_item.getProfileImageUrl()){
            case "Default":
                Glide.with(getContext()).load(R.mipmap.ic_launcher).into(image);
                break;
            default:
                Glide.with(getContext()).load(card_item.getProfileImageUrl()).into(image);
                break;


        }

        return convertView;
    }
}
