package com.example.udacity_movies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbhelper extends SQLiteOpenHelper {

    public static final String database_name = "favlist.db";
    public static final int database_version = 197;


    public dbhelper(Context context) {
        super(context, database_name, null, database_version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + sqllite.Entry.TABLE_NAME + " (" +
                sqllite.Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                sqllite.Entry.COLUMN_MOVIE_NAME + " String, " +
                sqllite.Entry.COLUMN_VOTE_AVERAGE+" integer,"+
                sqllite.Entry.COLUMN_RELEASE_DATE+" String"+
                "); ";


        System.out.println(SQL_CREATE_WAITLIST_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + sqllite.Entry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}