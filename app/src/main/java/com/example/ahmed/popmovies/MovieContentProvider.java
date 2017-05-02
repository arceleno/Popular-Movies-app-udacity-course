package com.example.ahmed.popmovies;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;


public class MovieContentProvider extends ContentProvider {

    private MovieDb movieDb;
    private static final UriMatcher uriMatcher=buildUriMatcher();

    static final int MOVIE = 100;
    static final int MOVIE_WITH_ID = 101;

    static UriMatcher buildUriMatcher(){
        final UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);
        final String authority=MovieContracts.CONTENT_AUTHORITY;
        matcher.addURI(authority,MovieContracts.MOVIES_TABLE.TABLE_NAME,MOVIE);
        matcher.addURI(authority,MovieContracts.MOVIES_TABLE.TABLE_NAME+"/#",MOVIE_WITH_ID);
        return matcher;
    }


    @Override
    public boolean onCreate() {
        movieDb=new MovieDb(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor;
        int match = uriMatcher.match(uri);
        switch (match){
            case MOVIE :
               retCursor=movieDb.getReadableDatabase().query(
                        MovieContracts.MOVIES_TABLE.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case MOVIE_WITH_ID :
                String movieSelection= MovieContracts.MOVIES_TABLE.TABLE_NAME + "." + MovieContracts.MOVIES_TABLE._ID +"= ?";
                String[] movieSelectionArgs=new String[]{MovieContracts.MOVIES_TABLE.getMovieID(uri)};
                retCursor=movieDb.getReadableDatabase().query(
                        MovieContracts.MOVIES_TABLE.TABLE_NAME,
                        projection,
                        movieSelection,
                        movieSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException();
        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int match=uriMatcher.match(uri);
        String type;
        switch (match){
            case MOVIE :
                type= MovieContracts.MOVIES_TABLE.CONTENT_TYPE;
                break;
            case MOVIE_WITH_ID :
                type= MovieContracts.MOVIES_TABLE.CONTENT_ITEM_TYPE;
                break;
            default:
                throw new UnsupportedOperationException();
        }

        return type;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Uri uri1;
        int match=uriMatcher.match(uri);
        SQLiteDatabase sqLiteDatabase=movieDb.getWritableDatabase();

        switch (match){

            case MOVIE :
                Long movieId=sqLiteDatabase.insert(MovieContracts.MOVIES_TABLE.TABLE_NAME,null,values);
                if (movieId == -1){
                     uri1=null;

                }else {
                    uri1= MovieContracts.MOVIES_TABLE.buildMovieUri(movieId);
                }
                break;
            default:
                throw new UnsupportedOperationException();

        }
        sqLiteDatabase.close();
        return uri1;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int movieDeleted;
        int match = uriMatcher.match(uri);
        SQLiteDatabase sqLiteDatabase = movieDb.getWritableDatabase();
        if (selection == null) {
            selection = "1";
        }
        switch (match){
            case MOVIE :
                movieDeleted=sqLiteDatabase.delete(MovieContracts.MOVIES_TABLE.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException();
        }

        if (movieDeleted !=0){
            getContext().getContentResolver().notifyChange(uri,null);

        }
        sqLiteDatabase.close();
        return movieDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int moviesUpdated;
        int match = uriMatcher.match(uri);
        SQLiteDatabase sqLiteDatabase = movieDb.getWritableDatabase();
        switch (match){
            case MOVIE :
                moviesUpdated=sqLiteDatabase.update(MovieContracts.MOVIES_TABLE.TABLE_NAME,values,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException();
        }
        if (moviesUpdated !=0){
            getContext().getContentResolver().notifyChange(uri,null);

        }
        sqLiteDatabase.close();
        return moviesUpdated;
    }
}
