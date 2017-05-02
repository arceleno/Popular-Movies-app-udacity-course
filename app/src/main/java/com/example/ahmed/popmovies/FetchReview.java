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


public class FetchReview extends AsyncTask<Long,Void,List<Review>> {

    final String LOG_TAG = FetchTrailer.class.getSimpleName();
    List<Review> reviewsDataList=new ArrayList<>();
    ReviewAdapter reviewAdapter;
    Context context;
    View rootView;


    public FetchReview(Context context,View rootView){
        this.context=context;
        this.rootView=rootView;

    }


    @Override
    protected List<Review> doInBackground(Long... params) {

        String url1 = "http://api.themoviedb.org/3/movie/";
        String url2="/reviews?api_key=5af3db634899a5f2320bedb9d54a41c1";

        StringBuffer buffer1=new StringBuffer(url1);
        buffer1.append(params[0].toString());
        buffer1.append(url2);
        String LastUrl=buffer1.toString();

        HttpURLConnection connection = null;
        BufferedReader reader = null;
      try {

        URL url=new URL(LastUrl);
        connection=(HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        InputStream stream=connection.getInputStream();
        reader=new BufferedReader(new InputStreamReader(stream));
        StringBuffer buffer =  new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {

            buffer.append(line + "\n");
        }



          String finalJson= buffer.toString();
          JSONObject reviewJson=new JSONObject(finalJson);
          JSONArray reviewJsonArray=reviewJson.getJSONArray("results");
          List<Review> ReviewList=new ArrayList<>();


          for(int i=0;i<reviewJsonArray.length();i++) {
              JSONObject finalObject = reviewJsonArray.getJSONObject(i);
              String Author=finalObject.getString("author");
              String Content=finalObject.getString("content");
              String Site=finalObject.getString("url");

              ReviewList.add(new Review(Author,Content,Site));

          }

          return ReviewList;


          } catch (ProtocolException e) {
               e.printStackTrace();
           } catch (MalformedURLException e) {
                e.printStackTrace();
          } catch (IOException e) {
               e.printStackTrace();
          } catch (JSONException e) {
          e.printStackTrace();
      }


        return null;
    }


    @Override
    protected void onPostExecute(List<Review> reviews) {
        super.onPostExecute(reviews);

        ExpandableHeightListView list=(ExpandableHeightListView) rootView.findViewById(R.id.reviews_List);
        reviewsDataList=reviews;

        reviewAdapter=new ReviewAdapter(context,reviewsDataList);
        list.setAdapter(reviewAdapter);
        list.setExpanded(true);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Review review=reviewsDataList.get(position);
                Uri http = Uri.parse(review.getUrl()).buildUpon().build();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(http);
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);

                }


            }
        });



    }
}
