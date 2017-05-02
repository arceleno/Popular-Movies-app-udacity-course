package com.example.ahmed.popmovies;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;


public class FetchFavourites extends AsyncTask<Void,Void,List<Movie>> {


    private static final String[] MOVIE_COLUMNS = {
            MovieContracts.MOVIES_TABLE._ID,
            MovieContracts.MOVIES_TABLE.COLUMN_TITLE,
            MovieContracts.MOVIES_TABLE.COLUMN_POSTER_IMAGE,
            MovieContracts.MOVIES_TABLE.COLUMN_RELEASE_DATE,
            MovieContracts.MOVIES_TABLE.COLUMN_VOTE_AVERAGE,
            MovieContracts.MOVIES_TABLE.COLUMN_OVERVIEW
    };


    public static final int C_ID= 0;
    public static final int C_TITLE= 1;
    public static final int C_POSTER_IMAGE= 2;
    public static final int C_RELEASE_DATE= 3;
    public static final int C_VOTE_AVERAGE= 4;
    public static final int C_OVERVIEW= 5;


    private MovieAdapter mMovieAdapter;
    private Context mContext;

    List<Movie> movieList;



    public FetchFavourites(Context context,MovieAdapter movieAdapter,List<Movie> movies){
        this.mContext=context;
        this.mMovieAdapter=movieAdapter;
        this.movieList=movies;

    }


    @Override
    protected List<Movie> doInBackground(Void... params) {
        movieList=new ArrayList<>();
        Cursor cursor=mContext.getContentResolver().query(
                MovieContracts.MOVIES_TABLE.CONTENT_URI,
                MOVIE_COLUMNS,
                null,
                null,
                null
        );
        if(cursor != null && cursor.moveToFirst()){

            do{
                Movie movie=new Movie(cursor.getString(C_POSTER_IMAGE),
                        cursor.getString(C_TITLE),
                        cursor.getString(C_RELEASE_DATE),
                        cursor.getString(C_VOTE_AVERAGE),
                        cursor.getString(C_OVERVIEW),
                        cursor.getLong(C_ID)
                        );
                movieList.add(movie);

            }while (cursor.moveToNext());
            cursor.close();
        }

        return movieList;

    }


    @Override
    protected void onPostExecute(List<Movie> movies) {


        if (mMovieAdapter != null) {
            mMovieAdapter.clear();
            for (Movie m : movies)
                mMovieAdapter.add(m);
            mMovieAdapter.notifyDataSetChanged();
        }

    }
}
