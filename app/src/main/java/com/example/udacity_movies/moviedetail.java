package com.example.udacity_movies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.udacity_movies.sqllite.Entry.TABLE_NAME;

public class moviedetail extends AppCompatActivity  implements
        LoaderManager.LoaderCallbacks<Cursor>,OnMovieListener {



    String key;
    ArrayList<movie> review;
    private SQLiteDatabase mdb;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
       String MOVIE_BASE_URL="https://image.tmdb.org/t/p/w185";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_layout);



        Intent intent = getIntent();
        final movie mov_intent = (movie) intent.getSerializableExtra("de");
        new fetch(this,this,mov_intent.getId()).execute();



        dbhelper db = new dbhelper(this);
        mdb= db.getWritableDatabase();




        ImageView im =(ImageView) findViewById(R.id.imageView2);
        Picasso.get().load(MOVIE_BASE_URL + mov_intent.getPosterPath()).fit().into(im);

        ImageView ima =(ImageView) findViewById(R.id.imageView3);
        Picasso.get().load(MOVIE_BASE_URL + mov_intent.getBackdropPath()).fit().into(ima);

        TextView tv= (TextView) findViewById(R.id.textView);
        tv.setText(mov_intent.getTitle());
        TextView tv2= (TextView) findViewById(R.id.textView2);
        tv2.setText(mov_intent.getOverview());

        final ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        toggleButton.setText(null);
        toggleButton.setTextOn(null);
        toggleButton.setTextOff(null);


        final ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);






        if(ifNumberExists()) {
            toggleButton.setChecked(true);
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.fav));

        }else{
            toggleButton.setChecked(false);
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.notfav));

        }


        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                buttonView.startAnimation(scaleAnimation);

                if (isChecked) {



                    ContentValues cv = new ContentValues();

                    cv.put(sqllite.Entry._ID, mov_intent.getId());
                    cv.put(sqllite.Entry.COLUMN_MOVIE_NAME,mov_intent.getTitle());
                    cv.put(sqllite.Entry.COLUMN_VOTE_AVERAGE,mov_intent.getVoteAverage());
                    cv.put(sqllite.Entry.COLUMN_RELEASE_DATE,mov_intent.getReleaseDate());

                    Uri uri = getContentResolver().insert(sqllite.Entry.CONTENT_URI,cv);











                    Cursor cursor = getAllGuests();


                    int j =cursor.getCount();
                    System.out.println(j);
                    int i;
                    for(i=0;i<j;i++) {

                        cursor.moveToPosition(i);
                        int u = cursor.getInt(2);
                        System.out.println(u+"\n");

                        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.fav));

                    }
                    cursor.close();
                } else {
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.notfav));

                }
            }
        });


    }



    public boolean ifNumberExists()
    {
        Intent intent = getIntent();
        final movie mov_intent = (movie) intent.getSerializableExtra("de");
        Cursor cursor = null;
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE  " + _ID + " = '" + mov_intent.getId() +"'";
        dbhelper db = new dbhelper(this);
        mdb= db.getWritableDatabase();
        cursor= mdb.rawQuery(selectQuery,null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle loaderArgs) {

        return new AsyncTaskLoader<Cursor>(this) {

            // Initialize a Cursor, this will hold all the task data
            Cursor mTaskData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mTaskData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mTaskData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                // Will implement to load data

                // COMPLETED (5) Query and load all task data in the background; sort by priority
                // [Hint] use a try/catch block to catch any errors in loading data

                try {
                    return getContentResolver().query(sqllite.Entry.CONTENT_URI,
                            null,
                            null,
                            null,
                            _ID);

                } catch (Exception e) {

                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mTaskData = data;
                super.deliverResult(data);
            }
        };

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }


    private Cursor getAllGuests(){
        return mdb.query(
                TABLE_NAME,null,null,null,null,null,null
        );
    }






    @Override
    public void Popularmovies(ArrayList<movie> moviesa, ArrayList<movie> movi) {

    }


    @Override
    public void reviews(ArrayList<movie> moviesa) {


        review =moviesa;

        movie movie1= review.get(1);
        System.out.println(movie1.getAuthor());

    }
    private long addNewGuest(int movie, int a,String b,String c) {

        ContentValues cv = new ContentValues();

        cv.put(sqllite.Entry._ID, movie);
        cv.put(sqllite.Entry.COLUMN_MOVIE_NAME,b);
        cv.put(sqllite.Entry.COLUMN_VOTE_AVERAGE,a);

        cv.put(sqllite.Entry.COLUMN_RELEASE_DATE,c);
        return mdb.insert(TABLE_NAME, null, cv);
    }
    @Override
    public void onCarsError(String error) {
        key=error;

        ImageButton button = (ImageButton) findViewById(R.id.Play);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+key+"&feature=youtu.be"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);

            }
        });

        ImageView ima =(ImageView) findViewById(R.id.imageView3);


        ima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+key+"&feature=youtu.be"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);
            }
        });
    }
}

