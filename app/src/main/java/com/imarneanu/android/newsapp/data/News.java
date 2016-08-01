package com.imarneanu.android.newsapp.data;

/**
 * Created by imarneanu on 7/29/16.
 */
public class News {
    public String title;
    public String content;
    public String date;
    public String link;

    public News(String title, String content, String date, String link) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.link = link;
    }
}
