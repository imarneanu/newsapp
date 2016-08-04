package com.imarneanu.android.newsapp.activites;

import com.imarneanu.android.newsapp.R;
import com.imarneanu.android.newsapp.adapters.NewsRecyclerAdapter;
import com.imarneanu.android.newsapp.adapters.NewsRecyclerListener;
import com.imarneanu.android.newsapp.data.News;
import com.imarneanu.android.newsapp.utils.JsonParserUtils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements NewsRecyclerListener.OnItemClickListener {

    private static final String BASE_URL = "https://ajax.googleapis.com/ajax/services/feed/load?v=1.0&q=";
    private static final String SPORTS_NEWS = "http://feeds.reuters.com/reuters/sportsNews";

    private ArrayList<News> mNews;
    private LinearLayout mHeaderProgress;
    private NewsRecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String keyCategories = "pref_categories";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> categories = preferences.getStringSet(keyCategories, null);

        if (categories == null) {
            categories = News.Category.links();
        }

        mHeaderProgress = (LinearLayout) findViewById(R.id.headerProgress);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FetchReutersNewsTask newsTask = new FetchReutersNewsTask();
        newsTask.execute(categories);

        mRecyclerAdapter = new NewsRecyclerAdapter(this);
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

    private class FetchReutersNewsTask extends AsyncTask<Set<String>, Void, ArrayList<News>> {

        @Override
        protected void onPreExecute() {
            mHeaderProgress.setVisibility(View.VISIBLE);
        }

        @SafeVarargs
        @Override
        protected final ArrayList<News> doInBackground(Set<String>... params) {
            ArrayList<News> news = new ArrayList<>(4 * params[0].size());

            HttpURLConnection urlConnection;
            BufferedReader reader;

            try {
                for (String param : params[0]) {
                    URL url = new URL(BASE_URL + param);
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
                    ArrayList<News> data = JsonParserUtils.getNewsDataFromJson(buffer.toString());
                    if (data != null) {
                        news.addAll(data);
                    }
                }

                return news;
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

            mHeaderProgress.setVisibility(View.GONE);
        }

    }
}
