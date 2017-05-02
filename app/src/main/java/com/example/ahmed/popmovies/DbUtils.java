package com.example.ahmed.popmovies;

import android.content.ContentValues;


public  final  class DbUtils {

    public static ContentValues contentValues(Movie movie){

        ContentValues contentValues=new ContentValues();
        contentValues.put(MovieContracts.MOVIES_TABLE._ID,movie.getId());
        contentValues.put(MovieContracts.MOVIES_TABLE.COLUMN_TITLE,movie.getTitle());
        contentValues.put(MovieContracts.MOVIES_TABLE.COLUMN_POSTER_IMAGE,movie.getPoster_path());
        contentValues.put(MovieContracts.MOVIES_TABLE.COLUMN_RELEASE_DATE,movie.getRelease_date());
        contentValues.put(MovieContracts.MOVIES_TABLE.COLUMN_VOTE_AVERAGE,movie.getVote_average());
        contentValues.put(MovieContracts.MOVIES_TABLE.COLUMN_OVERVIEW,movie.getOverview());

        return contentValues;
    }
}
