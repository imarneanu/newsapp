package com.imarneanu.android.newsapp.adapters;

import com.imarneanu.android.newsapp.R;
import com.imarneanu.android.newsapp.data.News;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by imarneanu on 7/29/16.
 */
public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {
    private final String TAG = NewsRecyclerAdapter.class.getSimpleName();

    private Context mContext;
    private List<News> mNews;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mNewsContainer;
        public TextView mNewsTitle;
        public TextView mNewsContent;
        public TextView mNewsDate;

        public ViewHolder(View view) {
            super(view);

            mNewsContainer = (LinearLayout) view.findViewById(R.id.news_container);
            mNewsTitle = (TextView) view.findViewById(R.id.news_title);
            mNewsContent = (TextView) view.findViewById(R.id.news_content);
            mNewsDate = (TextView) view.findViewById(R.id.news_date);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        News news = mNews.get(position);

        try {
            holder.mNewsContainer.setBackground(Drawable.createFromStream(mContext.getAssets().open(news.category.toString()), null));
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        holder.mNewsTitle.setText(news.title);
        holder.mNewsContent.setText(news.content);
        holder.mNewsDate.setText(news.date);
    }

    public NewsRecyclerAdapter(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<News> news) {
        mNews = news;
    }

    @Override
    public int getItemCount() {
        return mNews == null ? 0 : mNews.size();
    }
}
