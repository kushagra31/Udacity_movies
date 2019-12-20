package com.example.udacity_movies;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;




public class fetch extends AsyncTask<Void,Void,Void> {

    String popularMoviesURL;
    String topRatedMoviesURL;
    String getkeyURL;
    String getreviewURL;
    String favURL;
    private final OnMovieListener listener;
    private SQLiteDatabase mdb;


    ArrayList<movie> mTopTopRatedList;
    ArrayList<movie> review;
    ArrayList<movie> mPopularList ;
    ArrayList<movie> favlist;
    String key;
    Context context;
    int a;

    public fetch(OnMovieListener mlis,Context context,int a){
        this.listener=mlis;
        this.context=context;
        this.a=a;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }


    @Override
    protected Void doInBackground(Void... voids) {


        popularMoviesURL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=3200ecdecce82a507d711abc7aa9d6ec";
        topRatedMoviesURL = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key=3200ecdecce82a507d711abc7aa9d6ec";
        getkeyURL= "https://api.themoviedb.org/3/movie/"+a+"/videos?api_key=3200ecdecce82a507d711abc7aa9d6ec&language=en-US";
        getreviewURL="https://api.themoviedb.org/3/movie/384018/reviews?api_key=3200ecdecce82a507d711abc7aa9d6ec&language=en-US";
        favURL="u";
        mPopularList = new ArrayList<>();
        mTopTopRatedList = new ArrayList<>();
        review = new ArrayList<>();

        try {
            if(network.networkStatus(context)){

                if(a==00) {
                    mTopTopRatedList = network.fetchData(topRatedMoviesURL);
                    mPopularList = network.fetchData(popularMoviesURL);


                }else {
                    key= network.fetchDta(getkeyURL);
                    review=network.fetchDa(getreviewURL);

                }


            }
            else{
                Toast.makeText(context,"No Internet Connection",Toast.LENGTH_LONG).show();
            }
        } catch (IOException e){
            e.printStackTrace();

        }


        return null;

    }

    @Override
    protected void onPostExecute(Void s) {
        super.onPostExecute(s);
        if (listener!=null){
            listener.onCarsError(key);
            listener.Popularmovies(mTopTopRatedList,mPopularList);
            listener.reviews(review);
        }

    }
}
