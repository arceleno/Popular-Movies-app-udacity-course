package com.example.ahmed.popmovies;

import android.os.Parcel;
import android.os.Parcelable;


public class Trailer  implements Parcelable{

    private String Link;
    private String Name;
    private String Site;
    private String ID;

    public String getID() {
        return ID;
    }

    public String getLink() {
        return Link;
    }

    public String getName() {
        return Name;
    }

    public String getSite() {
        return Site;
    }



    public Trailer(String link,String name,String site,String id){
        this.Link="https://www.youtube.com/watch?v="+link;
        this.Name=name;
        this.Site=site;
        this.ID=id;



    }

    protected Trailer(Parcel in) {
        Link = in.readString();
        Name = in.readString();
        Site = in.readString();
        ID = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Link);
        dest.writeString(Name);
        dest.writeString(Site);
        dest.writeString(ID);
    }
}
