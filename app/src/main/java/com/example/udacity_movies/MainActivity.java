package com.example.udacity_movies;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.GridView;

import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements OnMovieListener {


    ArrayList<movie> mPopularList;
    ArrayList<movie> mTopTopRatedList;

    static OnMovieListener m;


    public static void register(OnMovieListener listener){
        m = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean b = isOnline();
        System.out.println(b);
        if(b) {
            new fetch(this, this, 00).execute();
        }

        else {
            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();



        }
        MainActivity.register(this);



    }
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if (id == R.id.pop_movies) {
            boolean b = isOnline();
            if (b) {
                refreshList(mPopularList);
            }
            else{
                Toast.makeText(getApplicationContext(),"No Internet Access",Toast.LENGTH_LONG).show();
            }
        }
        if (id == R.id.top_movies) {
            boolean b = isOnline();
            if(b) {
                refreshList(mTopTopRatedList);
            }
            else{
                Toast.makeText(getApplicationContext(),"No Internet Access",Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);

    }




    @Override
    public void reviews(ArrayList<movie> moviesa) {


    }

    @Override
    public void Popularmovies(final ArrayList<movie> moviesa, final ArrayList<movie> movi) {

        if (isOnline()) {
            this.mPopularList = movi;
            this.mTopTopRatedList = moviesa;


            GridView gridView = (GridView) findViewById(R.id.gridview);
            gridView.invalidateViews();
            gridView.setAdapter(new CustomAdapter(getApplicationContext(), movi));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent,
                                        View v, int position, long id) {

                    if (isOnline()) {
                        movie moviea = (movie) parent.getAdapter().getItem(position);

                        Intent i = new Intent(getApplicationContext(), moviedetail.class);

                        i.putExtra("de", moviea);

                        startActivity(i);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                    }

                }
            });
        }else{
            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
        }
    }
    public void refreshList(ArrayList<movie> li) {

        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.invalidateViews();
        gridView.setAdapter(new CustomAdapter(getApplicationContext(), li));

    }





    @Override
    public void onCarsError(String error) {

    }

}
