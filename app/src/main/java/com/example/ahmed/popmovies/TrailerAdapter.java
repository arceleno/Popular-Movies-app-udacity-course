package com.example.ahmed.popmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class TrailerAdapter extends ArrayAdapter<Trailer> {

    private Context context;
    private List<Trailer> ImageTrailerUrls=new ArrayList<>();

    public TrailerAdapter(Context context, List<Trailer> ImageTrailerUrls) {
        super(context,0, ImageTrailerUrls);
        this.context=context;
        this.ImageTrailerUrls=ImageTrailerUrls;

    }

    @Override
    public void add(Trailer object) {
        ImageTrailerUrls.add(object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Trailer trailer=getItem(position);


        if (convertView==null){

            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.trailers_list_item,null);
        }

        ImageView imageView=(ImageView)convertView.findViewById(R.id.site_logo);
        StringBuffer buffer=new StringBuffer("http://img.youtube.com/vi/");
        buffer.append(trailer.getID());
        buffer.append("/0.jpg");
        String url=buffer.toString();

        if(trailer.getSite().equals("YouTube")) {
            Picasso.with(context).load(url).into(imageView);
            TextView textView = (TextView) convertView.findViewById(R.id.Trailer_name);
            textView.setText(trailer.getName());
        }

       return convertView;
    }
}
