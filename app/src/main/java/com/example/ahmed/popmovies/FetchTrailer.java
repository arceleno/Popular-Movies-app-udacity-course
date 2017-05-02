package com.example.ahmed.popmovies;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class FetchTrailer extends AsyncTask<Long,Void,List<Trailer>> {
    final String LOG_TAG = FetchTrailer.class.getSimpleName();
    List<Trailer> trailerDataList=new ArrayList<>();
    TrailerAdapter trailerAdapter;
    Context context;
    View rootView;



    public FetchTrailer(Context context,View rootView){
        this.context=context;
        this.rootView=rootView;
    }


    @Override
    protected List<Trailer> doInBackground(Long... params) {
        String url1 = "http://api.themoviedb.org/3/movie/";
        String url2 = "/videos?api_key=5af3db634899a5f2320bedb9d54a41c1";

        StringBuffer buffer1 = new StringBuffer(url1);
        buffer1.append(params[0].toString());
        buffer1.append(url2);
        String LastUrl = buffer1.toString();

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(LastUrl);
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


            String finalJson = buffer.toString();
            JSONObject trailerJson = new JSONObject(finalJson);
            JSONArray trailerJsonArray = trailerJson.getJSONArray("results");
            List<Trailer> TrailerList = new ArrayList<>();

            for (int i = 0; i < trailerJsonArray.length(); i++) {
                JSONObject finalObject = trailerJsonArray.getJSONObject(i);

                String Link = finalObject.getString("key");
                String Name = finalObject.getString("name");
                String Site = finalObject.getString("site");
                String id = Link;
                TrailerList.add(new Trailer(Link, Name, Site, id));


            }

            return TrailerList;



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
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
    protected void onPostExecute(List<Trailer> trailers) {
        super.onPostExecute(trailers);

            ExpandableHeightListView list = (ExpandableHeightListView) rootView.findViewById(R.id.trailers_List);
            trailerDataList = trailers;
            trailerAdapter = new TrailerAdapter(context, trailerDataList);

            list.setAdapter(trailerAdapter);
            list.setExpanded(true);


    /*    list.setOnTouchListener(new ListView.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });
        */


            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Trailer trailer = trailerDataList.get(position);
                    Uri http = Uri.parse(trailer.getLink()).buildUpon().build();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(http);
                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(intent);

                    }


                }
            });

    }
}
