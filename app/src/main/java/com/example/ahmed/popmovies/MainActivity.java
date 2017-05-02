package com.example.ahmed.popmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NameListener {

     boolean mInTwoPane=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.fl_detail)!=null){

            mInTwoPane=true;

        }

            MainActivityFragment mainActivityFragment = new MainActivityFragment();
            mainActivityFragment.setNameListener(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, mainActivityFragment, "").commit();

             Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
             setSupportActionBar(toolbar);

    }


    @Override
    public void setSelectedName(Movie name) {


        /// case one pane

        if(!mInTwoPane){


            Intent i = new Intent(this, DetailActivity.class);
            i.putExtra("movie",name);
            startActivity(i);


        }else{
            //////// Case Two pane


            DetailActivityFragment detailActivityFragment= new DetailActivityFragment();
             Bundle extras = new Bundle();
             extras.putParcelable("movie", name);
            detailActivityFragment.setArguments(extras);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_detail,detailActivityFragment,"").commit();




        }

    }
}
