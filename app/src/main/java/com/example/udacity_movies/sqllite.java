package com.example.udacity_movies;
import android.net.Uri;
import android.provider.BaseColumns;

public class sqllite{
    public static final class Entry implements BaseColumns {


        public static final String TABLE_NAME = "waitlistd";
        public static final String COLUMN_MOVIE_NAME = "moviename";
        public static final String COLUMN_RELEASE_DATE = "releasedate";
        public static final String COLUMN_VOTE_AVERAGE = "rating";
        public static final String AUTHORITY = "com.example.udacity_movies";
        public static final String PATH_TASKS = "waitlistd";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();
    }
}
