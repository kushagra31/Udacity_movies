package com.example.udacity_movies;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.AndroidException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TaskContentProvider extends ContentProvider {
    UriMatcher uriMatcher;
    public static final int WAITLISTD=100;
    public static final int WAITLISTD_WITH_ID=101;
    private dbhelper db;

    public static final UriMatcher urim =builduri();
    public static UriMatcher builduri(){
    UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
    uriMatcher.addURI(sqllite.Entry.AUTHORITY,sqllite.Entry.PATH_TASKS,WAITLISTD);
    uriMatcher.addURI(sqllite.Entry.AUTHORITY,sqllite.Entry.PATH_TASKS+"/#",WAITLISTD_WITH_ID);
    return uriMatcher;

}
    @Override
    public boolean onCreate() {
       Context context=getContext();
        db= new  dbhelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        Cursor cursor=null;
        switch (uriMatcher.match(uri)) {

            case WAITLISTD: {
                // Code for querying with a date
                break;
            }
            case WAITLISTD_WITH_ID: {
                // Code for querying the weather table
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;


    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();


        Uri returnuri;
        switch (urim.match(uri)){
            case WAITLISTD:



                long id=sqLiteDatabase.insert(sqllite.Entry.TABLE_NAME,null,contentValues);
                if(id>0){
                    returnuri= ContentUris.withAppendedId(sqllite.Entry.CONTENT_URI,id);
                }

                else{
                  throw new SQLException();
                }
               break;
               default:
               throw new UnsupportedOperationException("Unknown uri"+uri);


        }
         getContext().getContentResolver().notifyChange(uri,null);
        return returnuri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        switch (urim.match(uri)){
            case WAITLISTD:







                break;
            default:
                throw new UnsupportedOperationException("Unknown uri"+uri);


        }


        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
