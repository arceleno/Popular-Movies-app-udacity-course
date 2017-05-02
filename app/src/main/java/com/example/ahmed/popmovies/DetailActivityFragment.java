package com.example.ahmed.popmovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.squareup.picasso.Picasso;


public class DetailActivityFragment extends Fragment {

    ImageView tvImageIcon;
    TextView tvTitle;
    TextView tvReleaseDate;
    TextView tvRate;
    TextView tvOverView;

    Movie movie;
    boolean IsFavourite;


    public DetailActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       // movie=getActivity().getIntent().getParcelableExtra("movie");

        ConnectivityManager cm=(ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=cm.getActiveNetworkInfo();


        inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         View rootView= inflater.inflate(R.layout.fragment_detail, container, false);
        final FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab);


        Bundle sentBundle=getArguments();
        if(sentBundle!=null) {
            movie = sentBundle.getParcelable("movie");
        }



        ////////////// Detail Movie /////////////////



        tvImageIcon=(ImageView)rootView.findViewById(R.id.poster_view);
        tvTitle=(TextView) rootView.findViewById(R.id.title_view);
        tvReleaseDate=(TextView) rootView.findViewById(R.id.date_view);
        tvRate=(TextView) rootView.findViewById(R.id.average_view);
        tvOverView=(TextView) rootView.findViewById(R.id.overview_view);


        ExpandableHeightListView listView=(ExpandableHeightListView) rootView.findViewById(R.id.trailers_List);
        FetchTrailer fetchTrailer=new FetchTrailer(getContext(),listView);

        ExpandableHeightListView listView1=(ExpandableHeightListView) rootView.findViewById(R.id.reviews_List);
        FetchReview fetchReview=new FetchReview(getContext(),listView1);
        Long Rid=movie.getId();


        if(movie!=null){

            Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185"+movie.getPoster_path()).into(tvImageIcon);
            tvTitle.setText(movie.getTitle());
            tvReleaseDate.setText(movie.getRelease_date());
            tvRate.setText(movie.getVote_average()+"/10");
            tvOverView.setText(movie.getOverview()+"\n");


            if(networkInfo!=null && networkInfo.isConnected()&& networkInfo.isAvailable()){
                fetchTrailer.execute(movie.getId());
                fetchReview.execute(Rid);
            }

        }

          IsFavourite= Favourite(getContext(), String.valueOf(movie.getId()));


        if(IsFavourite){
            fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.heart_red));
        }else {

            fab.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.heart_white));

        }




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String toastTitle;

                if(!IsFavourite){
                    ContentValues contentValues = DbUtils.contentValues(movie);
                    getContext().getContentResolver().insert(MovieContracts.MOVIES_TABLE.CONTENT_URI,contentValues);
                     fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.heart_red));
                    toastTitle="Added To Favourites";

                    IsFavourite=true;


                }else {

                    getContext().getContentResolver().delete(MovieContracts.MOVIES_TABLE.CONTENT_URI,
                            MovieContracts.MOVIES_TABLE._ID +"= ?",
                            new String[]{String.valueOf(movie.getId())}
                    );
                    fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.heart_white));
                    toastTitle="Remove From Favourites";

                    IsFavourite=false;

                }

               // Snackbar.make(view,snackbarTitle, Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                Toast.makeText(getContext(),toastTitle,Toast.LENGTH_SHORT).show();


            }

        });

        return rootView;
    }


    public static boolean Favourite(Context context, String id){


        Cursor cursor =context.getContentResolver().query(
                MovieContracts.MOVIES_TABLE.CONTENT_URI,
                null,
                MovieContracts.MOVIES_TABLE._ID +"= ?",
                new String []{id},
                null);
        if (cursor != null) {
            int numberRows = cursor.getCount();
            cursor.close();
            return (numberRows > 0);
        }


        return true;
    }
}

