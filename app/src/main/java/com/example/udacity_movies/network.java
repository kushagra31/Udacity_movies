package com.example.udacity_movies;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.GridView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import okhttp3.OkHttpClient;

public class network {
    private static SQLiteDatabase mdb;
    private static final String TAG = network.class.getSimpleName();
    public static Boolean networkStatus(Context context){
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }

    public static ArrayList<movie> fetchData(String url) throws IOException {
        ArrayList<movie> movies = new ArrayList<>();
        try {

            URL new_url = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) new_url.openConnection(); //Opening a http connection  to the remote object


            InputStream inputStream = connection.getInputStream();

            Scanner sc = new Scanner(inputStream);

            StringBuffer sb = new StringBuffer();
            while(sc.hasNext()){
                sb.append(sc.nextLine());
            }


            String results = sb.toString();
            parseJson(results,movies);
            inputStream.close();

        } catch (IOException e) {

            e.printStackTrace();
        }

        return movies;
    }

    public static ArrayList<movie> fetchDatafav(String url) throws IOException {
        ArrayList<movie> movies = new ArrayList<>();
        try {

            URL new_url = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) new_url.openConnection();

            InputStream inputStream = connection.getInputStream();

            Scanner sc = new Scanner(inputStream);

            StringBuffer sb = new StringBuffer();
            while(sc.hasNext()){
                sb.append(sc.nextLine());
            }
            Cursor cursor = getAllGuests();


            int j =cursor.getCount();
            System.out.println(j);
            cursor.moveToPosition(2);
            int u=cursor.getInt(0);
            System.out.println(u);

            String results = sb.toString();
            parseJsonsingle(results,movies);
            inputStream.close();

        } catch (IOException e) {

            e.printStackTrace();
        }

        return movies;
    }



    public static String fetchDta(String url) throws IOException {
        String key = null;
        try {

            URL new_url = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) new_url.openConnection(); //Opening a http connection  to the remote object


            InputStream inputStream = connection.getInputStream();

            Scanner sc = new Scanner(inputStream);

            StringBuffer sb = new StringBuffer();
            while(sc.hasNext()){
                sb.append(sc.nextLine());
            }


            String results = sb.toString();
            try{
                JSONObject mainObject = new JSONObject(results);

                JSONArray resArray = mainObject.getJSONArray("results");
                JSONObject jsonObject = resArray.getJSONObject(0);
                key = jsonObject.getString("key");

                System.out.println(key);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "Erro occurred during JSON Parsing", e);
            }
            inputStream.close();

        } catch (IOException e) {

            e.printStackTrace();
        }

        return key;
    }

    public static void keymeth(String data,String key1){
        try{
            JSONObject mainObject = new JSONObject(data);
             key1 = mainObject.getString("key");
            System.out.println(key1);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Erro occurred during JSON Parsing", e);
        }

    }

    public static void parseJson(String data, ArrayList<movie> list){

        try {
            JSONObject mainObject = new JSONObject(data);

            JSONArray resArray = mainObject.getJSONArray("results"); //Getting the results object
            for (int i = 0; i < resArray.length(); i++) {
                JSONObject jsonObject = resArray.getJSONObject(i);
                movie movie = new movie(); //New Movie object
                movie.setId(jsonObject.getInt("id"));
                movie.setVoteAverage(jsonObject.getInt("vote_average"));
                movie.setVoteCount(jsonObject.getInt("vote_count"));
                movie.setOriginalTitle(jsonObject.getString("original_title"));
                movie.setTitle(jsonObject.getString("title"));
                movie.setPopularity(jsonObject.getDouble("popularity"));
                movie.setBackdropPath(jsonObject.getString("backdrop_path"));
                movie.setOverview(jsonObject.getString("overview"));
                movie.setReleaseDate(jsonObject.getString("release_date"));
                movie.setPosterPath(jsonObject.getString("poster_path"));

                list.add(movie);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Erro occurred during JSON Parsing", e);
        }

    }
    public static void parseJsonsingle(String data, ArrayList<movie> list){

        try {
            JSONObject mainObject = new JSONObject(data);

            JSONArray resArray = mainObject.getJSONArray("results"); //Getting the results object
            for (int i = 0; i < resArray.length(); i++) {
                JSONObject jsonObject = resArray.getJSONObject(i);
                movie movie = new movie(); //New Movie object
                movie.setId(jsonObject.getInt("id"));
                movie.setVoteAverage(jsonObject.getInt("vote_average"));
                movie.setVoteCount(jsonObject.getInt("vote_count"));
                movie.setOriginalTitle(jsonObject.getString("original_title"));
                movie.setTitle(jsonObject.getString("title"));
                movie.setPopularity(jsonObject.getDouble("popularity"));
                movie.setBackdropPath(jsonObject.getString("backdrop_path"));
                movie.setOverview(jsonObject.getString("overview"));
                movie.setReleaseDate(jsonObject.getString("release_date"));
                movie.setPosterPath(jsonObject.getString("poster_path"));

                list.add(movie);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Erro occurred during JSON Parsing", e);
        }

    }
    public static void parseJsonreview(String data, ArrayList<movie> list){

        try {
            JSONObject mainObject = new JSONObject(data);

            JSONArray resArray = mainObject.getJSONArray("results");
            for (int i = 0; i < resArray.length(); i++) {
                JSONObject jsonObject = resArray.getJSONObject(i);
                movie movie = new movie(); //New Movie object

                movie.setreview(jsonObject.getString("content"));
                movie.setAuthor(jsonObject.getString("author"));

                list.add(movie);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Erro occurred during JSON Parsing", e);
        }

    }

    public static ArrayList<movie> fetchDa(String getreviewURL)  {

       ArrayList<movie> review= new ArrayList<>();
        try{
            URL url =new URL(getreviewURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream inputStream =connection.getInputStream();

            Scanner sc = new Scanner(inputStream);

            StringBuffer sb = new StringBuffer();
            while(sc.hasNext()){
                sb.append(sc.nextLine());
            }

            String results = sb.toString();
            parseJsonreview(results,review);
            inputStream.close();



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return review;
    }
    private static   Cursor getAllGuests(){
        return mdb.query(
                sqllite.Entry.TABLE_NAME,null,null,null,null,null,null
        );
    }
}

