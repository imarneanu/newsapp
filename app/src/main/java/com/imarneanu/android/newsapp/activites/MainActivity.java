package com.imarneanu.android.newsapp.activites;

import com.imarneanu.android.newsapp.R;
import com.imarneanu.android.newsapp.adapters.NewsRecyclerAdapter;
import com.imarneanu.android.newsapp.adapters.NewsRecyclerListener;
import com.imarneanu.android.newsapp.data.News;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsRecyclerListener.OnItemClickListener {

    private ArrayList<News> mNews;
    private NewsRecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNews = new ArrayList<>(3);
        mNews.add(0, new News("Taliban claim Kabul truck bomb blast", "KABUL (Reuters) - The Taliban claimed responsibility for a truck bomb attack on a military and logistics services compound, ...", "Mon, 01 Aug 2016 04:16:03 -0700", "http://feeds.reuters.com/~r/Reuters/worldNews/~3/O6PFfymUuLI/us-afghanistan-blast-idUSKCN10B0WA"));
        mNews.add(1, new News("Russian helicopter downed in rebel-held Idlib province", "BEIRUT (Reuters) - A Russian military helicopter carrying five people was shot down in the north of Syria's rebel-held Idlib ...", "Mon, 01 Aug 2016 04:14:56 -0700", "http://feeds.reuters.com/~r/Reuters/worldNews/~3/0jngY8Aa-sI/us-mideast-crisis-syria-helicopter-idUSKCN10C1WI"));
        mNews.add(2, new News("'Give them a bloody nose': Xi pressed for stronger South China Sea response", "BEIJING (Reuters) - China's leadership is resisting pressure from elements within the military for a more forceful response to ...", "Sun, 31 Jul 2016 19:29:58 -0700", "http://feeds.reuters.com/~r/Reuters/worldNews/~3/tzq0f8PAh9A/us-southchinasea-ruling-china-insight-idUSKCN10B10G"));

        mRecyclerAdapter = new NewsRecyclerAdapter(mNews);
        recyclerView.setAdapter(mRecyclerAdapter);

        recyclerView.addOnItemTouchListener(new NewsRecyclerListener(this, this));
    }

    @Override
    public void onItemClick(View view, int position) {
        String url = mNews.get(position).url;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
