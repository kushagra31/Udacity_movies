package com.example.udacity_movies;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class datafake {

    public static void insertFakeData(SQLiteDatabase db){
        if(db == null){
            return;
        }
        //create a list of fake guests
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(sqllite.Entry.COLUMN_MOVIE_NAME, "John");
        cv.put(sqllite.Entry.COLUMN_RELEASE_DATE, 12);
        list.add(cv);

        cv = new ContentValues();
        cv.put(sqllite.Entry.COLUMN_MOVIE_NAME, "Tim");
        cv.put(sqllite.Entry.COLUMN_RELEASE_DATE, 2);
        list.add(cv);

        cv = new ContentValues();
        cv.put(sqllite.Entry.COLUMN_MOVIE_NAME, "Jessica");
        cv.put(sqllite.Entry.COLUMN_RELEASE_DATE, 99);
        list.add(cv);

        cv = new ContentValues();
        cv.put(sqllite.Entry.COLUMN_MOVIE_NAME, "Larry");
        cv.put(sqllite.Entry.COLUMN_RELEASE_DATE, 1);
        list.add(cv);

        cv = new ContentValues();
        cv.put(sqllite.Entry.COLUMN_MOVIE_NAME, "Kim");
        cv.put(sqllite.Entry.COLUMN_RELEASE_DATE, 45);
        list.add(cv);

        //insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (sqllite.Entry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(sqllite.Entry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }

    }
}