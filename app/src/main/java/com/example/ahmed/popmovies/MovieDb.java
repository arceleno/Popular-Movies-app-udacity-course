package com.example.ahmed.popmovies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MovieDb extends SQLiteOpenHelper {

    public static final String DATTABASE_NAME="movies.db";
    public static final int VERSION=1;


    public MovieDb(Context context) {
        super(context, DATTABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE=" CREATE TABLE "+ MovieContracts.MOVIES_TABLE.TABLE_NAME +"(" +
                MovieContracts.MOVIES_TABLE._ID +" INTEGER PRIMARY KEY, "+
                MovieContracts.MOVIES_TABLE.COLUMN_TITLE +" TEXT NOT NULL,"+
                MovieContracts.MOVIES_TABLE.COLUMN_POSTER_IMAGE +" TEXT NOT NULL,"+
                MovieContracts.MOVIES_TABLE.COLUMN_RELEASE_DATE +" TEXT NOT NULL,"+
                MovieContracts.MOVIES_TABLE.COLUMN_VOTE_AVERAGE +" TEXT NOT NULL,"+
                MovieContracts.MOVIES_TABLE.COLUMN_OVERVIEW +" REAL TEXT NOT NULL"+
                ")";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + MovieContracts.MOVIES_TABLE.TABLE_NAME);
        onCreate(db);

    }
}
