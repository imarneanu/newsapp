package com.imarneanu.android.newsapp.activites;

import com.imarneanu.android.newsapp.Constants;
import com.imarneanu.android.newsapp.R;
import com.imarneanu.android.newsapp.adapters.NewsRecyclerAdapter;
import com.imarneanu.android.newsapp.adapters.NewsRecyclerListener;
import com.imarneanu.android.newsapp.data.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsRecyclerListener.OnItemClickListener {

    private final String TAG = MainActivity.class.getSimpleName();

    private ArrayList<News> mNews;
    private NewsRecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FetchReutersNewsTask newsTask = new FetchReutersNewsTask();
        newsTask.execute();

        mRecyclerAdapter = new NewsRecyclerAdapter(mNews);
        recyclerView.setAdapter(mRecyclerAdapter);

        recyclerView.addOnItemTouchListener(new NewsRecyclerListener(this, this));
    }

    @Override
    public void onItemClick(View view, int position) {
        String url = mNews.get(position).link;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private class FetchReutersNewsTask extends AsyncTask<Void, Void, ArrayList<News>> {

        @Override
        protected ArrayList<News> doInBackground(Void... voids) {
            HttpURLConnection urlConnection;
            BufferedReader reader;

            try {
                URL url = new URL(Constants.BASE_URL + Constants.WORLD_NEWS);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();

                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                return getNewsDataFromJson(buffer.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<News> news) {
            if (news != null) {
                mNews = news;
                mRecyclerAdapter.setData(mNews);
                mRecyclerAdapter.notifyDataSetChanged();
            }
        }

        private ArrayList<News> getNewsDataFromJson(String jsonString) {
            // These are the names of the JSON objects that need to be extracted.
            final String RESPONSE_DATA = "responseData";
            final String FEED = "feed";
            final String ENTRIES = "entries";
            final String TITLE = "title";
            final String CONTENT = "contentSnippet";
            final String DATE = "publishedDate";
            final String LINK = "link";

            try {
                JSONObject responseJson = new JSONObject(jsonString).getJSONObject(RESPONSE_DATA);
                JSONObject feedJson = responseJson.getJSONObject(FEED);
                JSONArray entriesArray = feedJson.getJSONArray(ENTRIES);

                ArrayList<News> news = new ArrayList<>(entriesArray.length());

                for (int i = 0; i < entriesArray.length(); i++) {
                    // Get the JSON object representing the news
                    JSONObject newsData = entriesArray.getJSONObject(i);

                    String title = newsData.getString(TITLE);
                    String content = newsData.getString(CONTENT);
                    String date = newsData.getString(DATE);
                    String link = newsData.getString(LINK);

                    news.add(i, new News(title, content, date, link));
                }
                return news;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
        }

    }
}
