package com.example.ahmed.popmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class MovieAdapter extends ArrayAdapter<Movie>{

    private List<Movie> movieList= new ArrayList();
    private  int resource;
    private Context mContext;

    Movie movie;



    public MovieAdapter(Context context, int resource, List<Movie> movies) {
        super(context, resource, movies);

        mContext=context;
        this.movieList=movies;
        this.resource=resource;
    }




   @Override
    public int getCount() {

        return movieList.size();

    }

    @Override
    public Movie getItem(int position) {

        return movieList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return super.getItemId(position);
    }

    @Override
    public void addAll(Collection collection) {
        movieList.addAll(collection);

    }

    @Override
    public void add(Movie object) {
        movieList.add(object);
    }

    public class ViewHolder{
        ImageView MoviePoster;
        public ViewHolder(View view){
            MoviePoster =(ImageView)view.findViewById(R.id.imageViewMovie);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view=convertView;
        movie=getItem(position);
        if(view==null){

            LayoutInflater inflater =(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.grid_view_image,null);

            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);

        }else {
            viewHolder=(ViewHolder)view.getTag();

        }

        String url=movie.getPoster_path();
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185"+url).into(viewHolder.MoviePoster);

      return view;

    }






}
