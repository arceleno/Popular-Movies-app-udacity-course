package com.example.ahmed.popmovies;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;


public  final class MovieContracts {

    public static final String CONTENT_AUTHORITY="com.example.ahmed.popmovies";
    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final class MOVIES_TABLE implements BaseColumns{

        public static final String TABLE_NAME="movies";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }


        public static final String CONTENT_TYPE= ContentResolver.CURSOR_DIR_BASE_TYPE +"/"+ CONTENT_AUTHORITY +"/"+ TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE= ContentResolver.CURSOR_ITEM_BASE_TYPE +"/"+ CONTENT_AUTHORITY +"/"+ TABLE_NAME;


        public static final String COLUMN_POSTER_IMAGE = "poster_image";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_RELEASE_DATE = "release_date";

        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        public static final String COLUMN_OVERVIEW = "overview";

        public static String getMovieID(Uri uri){
            return uri.getPathSegments().get(1);
        }

    }

}
