package com.example.ahmed.popmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ReviewAdapter extends ArrayAdapter<Review> {

    private Context context;
    private List<Review> ReviewsData=new ArrayList<>();
    public ReviewAdapter(Context context, List<Review> reviewsData) {

        super(context,0, reviewsData);
        this.context=context;
        this.ReviewsData=reviewsData;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Review review=getItem(position);
        if (convertView==null){

            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.reviews_list_item,null);
        }
        TextView tAuthor=(TextView)convertView.findViewById(R.id.text_author);
        TextView tContent=(TextView)convertView.findViewById(R.id.text_content);
        tAuthor.setText("Author: "+review.getAuthor());
        tContent.setText("Review: "+review.getContent()+"\n");

        return convertView;


    }
}
