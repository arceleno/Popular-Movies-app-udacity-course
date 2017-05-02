package com.example.ahmed.popmovies;

import android.os.Parcel;
import android.os.Parcelable;


public class Movie implements Parcelable
{
    private Long id;
    private String poster_path;
    private String backdrop_path;
    private String title;
    private String overview;
    private String release_date;
    private String vote_average;

    public Movie(String url,String title,String release_date,String vote_average,String overview,Long id){

        this.poster_path=url;
        this.title=title;
        this.release_date=release_date;
        this.vote_average=vote_average;
        this.overview=overview;
        this.id=id;

    }

    protected Movie(Parcel in) {
        id = in.readLong();
        poster_path = in.readString();
        backdrop_path = in.readString();
        title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        vote_average = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Long getId() {
        return id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(poster_path);
        dest.writeString(backdrop_path);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(vote_average);
    }
}
