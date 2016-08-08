package com.imarneanu.android.newsapp;

import com.imarneanu.android.newsapp.adapters.NewsRecyclerAdapter;
import com.imarneanu.android.newsapp.adapters.NewsRecyclerListener;
import com.imarneanu.android.newsapp.data.News;
import com.imarneanu.android.newsapp.utils.JsonParserUtils;
import com.imarneanu.android.newsapp.utils.NetworkUtils;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsRecyclerListener.OnItemClickListener {

    private static final String GUARDIAN_URL = "http://content.guardianapis.com/search?api-key=ff7a822c-f0c3-4f36-8ad5-d62fb2258413";

    private ArrayList<News> mNews;
    private LinearLayout mHeaderProgress;
    private TextView mEmptyView;
    private NewsRecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHeaderProgress = (LinearLayout) findViewById(R.id.headerProgress);
        mEmptyView = (TextView) findViewById(R.id.emptyView);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new NewsRecyclerListener(this, this));

        mRecyclerAdapter = new NewsRecyclerAdapter(this);
        recyclerView.setAdapter(mRecyclerAdapter);

        loadNews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            loadNews();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position) {
        String url = mNews.get(position).webUrl;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void loadNews() {
        if (NetworkUtils.isNetworkAvailable(this)) {
            FetchReutersNewsTask newsTask = new FetchReutersNewsTask();
            newsTask.execute();
            mEmptyView.setVisibility(View.GONE);
        } else {
            Snackbar.make(mEmptyView, getString(R.string.no_internet), Snackbar.LENGTH_LONG).show();
            mEmptyView.setText(getString(R.string.no_news_loaded));
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    private class FetchReutersNewsTask extends AsyncTask<Void, Void, ArrayList<News>> {

        @Override
        protected void onPreExecute() {
            mHeaderProgress.setVisibility(View.VISIBLE);
        }

        @SafeVarargs
        @Override
        protected final ArrayList<News> doInBackground(Void... voids) {
            try {
                URL url = new URL(GUARDIAN_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();

                if (inputStream == null) {
                    return null;
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                return JsonParserUtils.getNewsDataFromJson(buffer.toString());
            } catch (IOException e) {
                Log.e(MainActivity.class.getSimpleName(), e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<News> news) {
            if (news != null) {
                mNews = news;
                mRecyclerAdapter.setData(mNews);
                mRecyclerAdapter.notifyDataSetChanged();
            } else {
                mEmptyView.setText(getString(R.string.no_news));
                mEmptyView.setVisibility(View.VISIBLE);
            }
            mHeaderProgress.setVisibility(View.GONE);
        }

    }
}
