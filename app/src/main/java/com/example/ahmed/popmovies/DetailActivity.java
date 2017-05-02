package com.example.ahmed.popmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent sentIntent=getIntent();
        Bundle sentBundle=sentIntent.getExtras();

        DetailActivityFragment detailActivityFragment=new DetailActivityFragment();
        detailActivityFragment.setArguments(sentBundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_detail,detailActivityFragment,"").commit();
    }

}
