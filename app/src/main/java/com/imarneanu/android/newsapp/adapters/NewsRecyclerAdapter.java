package com.imarneanu.android.newsapp.adapters;

import com.imarneanu.android.newsapp.R;
import com.imarneanu.android.newsapp.data.News;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imarneanu on 7/29/16.
 */
public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {
    private List<News> mNews;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNewsTitle;
        public TextView mNewsContent;
        public TextView mNewsDate;

        public ViewHolder(View view) {
            super(view);

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
        holder.mNewsTitle.setText(news.title);
        holder.mNewsContent.setText(news.content);
        holder.mNewsDate.setText(news.date);
    }


    public NewsRecyclerAdapter(ArrayList<News> news) {
        mNews = news;
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }
}
