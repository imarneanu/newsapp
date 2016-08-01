package com.imarneanu.android.newsapp.data;

/**
 * Created by imarneanu on 7/29/16.
 */
public class News {
    public String title;
    public String content;
    public String date;
    public String link;

    public Category category;

    @SuppressWarnings("unused")
    public enum Category {
        artsNews("artsNews.png"),
        businessNews("businessNews.png"),
        domesticNews("domesticNews.png"),
        entertainmentNews("entertainmentNews.png"),
        environmentNews("environmentNews.png"),
        healthNews("healthNews.png"),
        lifestyleMolt("lifestyleMolt.png"),
        oddlyEnoughNews("oddlyEnoughNews.png"),
        peopleNews("peopleNews.png"),
        politicsNews("politicsNews.png"),
        RCOMUS_24("RCOMUS_24.png"),
        scienceNews("scienceNews.png"),
        technologyNews("technologyNews.png"),
        topNews("topNews.png"),
        vcMedia("vcMedia.png"),
        worldNews("worldNews.png"),
        wtMostRead("wtMostRead.png");

        private String value;

        Category(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public News(String title, String content, String date, String link, Category category) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.link = link;
        this.category = category;
    }
}
