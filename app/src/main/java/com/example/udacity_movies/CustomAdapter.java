package com.example.udacity_movies;

import android.content.Context;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
   Context context;
   LayoutInflater layoutInflater;

    public static final String MOVIE_BASE_URL="https://image.tmdb.org/t/p/w185";

    CustomAdapter(Context con, ArrayList<movie> movielist){
        con.getApplicationContext();
        context=con;
        this.layoutInflater=LayoutInflater.from(con);
        this.list=movielist;
    }

    ArrayList<movie> list;
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public movie getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


    movie movies = getItem(position);
    convertView = layoutInflater.inflate(R.layout.grid_items, parent, false);


    final ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

    imageView.setAdjustViewBounds(true);


    Picasso.get()
            .load(MOVIE_BASE_URL + movies.getPosterPath()).error(R.drawable.image_placeholder)
            .into(imageView);

        return convertView;
    }


}