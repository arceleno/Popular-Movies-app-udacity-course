package com.example.ahmed.popmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivityFragment extends Fragment {
    private MovieAdapter mAdapter;
    private List<Movie> fMovie;
    private NameListener mNameListener;


    public MainActivityFragment() {
        setHasOptionsMenu(true);


    }

    void setNameListener(NameListener nameListener){

        this.mNameListener=nameListener;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mAdapter = new MovieAdapter(getContext(), R.id.gridviewMovie, new ArrayList<Movie>());

        final GridView gridView = (GridView) rootView.findViewById(R.id.gridviewMovie);
        TextView emptyText = (TextView) rootView.findViewById(R.id.empty_Textview);
        gridView.setEmptyView(emptyText);
        gridView.setAdapter(mAdapter);

        ////////////////////////////////////////////////////

        SharedPreferences preferences = getActivity().getSharedPreferences("MoviesUrl", Context.MODE_PRIVATE);
        String url = "http://api.themoviedb.org/3/movie/popular?api_key=5af3db634899a5f2320bedb9d54a41c1";
        String url1 = preferences.getString("url", "");
        String title=preferences.getString("toastTitle","");
        String check=preferences.getString("checkFavourite","");


        if(check!=""){


             new FetchFavourites(getContext(),mAdapter,fMovie).execute();;

            Toast.makeText(getContext(),title,Toast.LENGTH_SHORT).show();

        }else {

            if(onLine()) {
                if (url1 == "") {

                    updateposters(url);
                    Toast.makeText(getContext(), "Popular", Toast.LENGTH_SHORT).show();

                } else {
                    updateposters(url1);
                    Toast.makeText(getContext(), title, Toast.LENGTH_SHORT).show();
                }
            }

        }


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Movie moviePosition=mAdapter.getItem(position);
                mNameListener.setSelectedName(moviePosition);


            }
        });
        return rootView;
    }




    public boolean onLine(){

        ConnectivityManager cm=(ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=cm.getActiveNetworkInfo();

        if(networkInfo==null || !networkInfo.isConnected() || !networkInfo.isAvailable()){

            Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }



    public void updateposters(String url) {

        new FetchMovies().execute(url);
    }



    //Fetch Movies

    public class FetchMovies extends AsyncTask<String, String, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... params) {
            HttpURLConnection connection = null;

            BufferedReader reader = null;
            try

            {

                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }
                ///////////////////// Json Code/////////////////////


                String finalJson = buffer.toString();
                JSONObject movieJson = new JSONObject(finalJson);
                JSONArray movieJsonArray = movieJson.getJSONArray("results");
                List<Movie> MovieList = new ArrayList<>();

                for (int i = 0; i < movieJsonArray.length(); i++) {
                    JSONObject finalObject = movieJsonArray.getJSONObject(i);

                    String PosterPath = (finalObject.getString("poster_path"));
                    String title = (finalObject.getString("title"));
                    String date = (finalObject.getString("release_date"));
                    String rate = (finalObject.getString("vote_average"));
                    String overView = (finalObject.getString("overview"));
                    Long id = (finalObject.getLong("id"));
                    MovieList.add(new Movie(PosterPath, title, date, rate, overView, id));

                }
                return MovieList;

                ////////////////// End Code //////////////////////////////

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return null;

        }


        @Override
        protected void onPostExecute(List<Movie> result) {
            if (mAdapter != null) {
                mAdapter.clear();
                for (Movie m : result)
                    mAdapter.add(m);
                mAdapter.notifyDataSetChanged();
            }

        }


    }

     //Menu Items

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        SharedPreferences preferences = getActivity().getSharedPreferences("MoviesUrl", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();


        switch (id) {
            case R.id.poular_movies:


                String url = "http://api.themoviedb.org/3/movie/popular?api_key=5af3db634899a5f2320bedb9d54a41c1";
                String pop="Popular";
                editor.putString("checkFavourite","");
                editor.putString("url", url);
                editor.putString("toastTitle",pop);
                editor.apply();

                Toast.makeText(getContext(),pop,Toast.LENGTH_SHORT).show();

                if(onLine()){
                    updateposters(url);
                }

                return true;

            case R.id.top_rated:

                String urll = "http://api.themoviedb.org/3/movie/top_rated?api_key=5af3db634899a5f2320bedb9d54a41c1";
                String top="Top Rated";
                editor.putString("checkFavourite","");
                editor.putString("url", urll);
                editor.putString("toastTitle",top);
                editor.apply();

                Toast.makeText(getContext(),top,Toast.LENGTH_SHORT).show();

                if(onLine()){
                    updateposters(urll);

                }


                return true;
            case R.id.favourites:

                String fav="Favourites";
                editor.putString("checkFavourite","fav");
                editor.putString("toastTitle",fav);
                editor.apply();
                Toast.makeText(getContext(),fav,Toast.LENGTH_SHORT).show();

                new FetchFavourites(getContext(),mAdapter,fMovie).execute();



                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }



}
