package com.imarneanu.android.newsapp.data;

import java.util.HashSet;
import java.util.Set;

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
        artsNews("artsNews.png", "http://feeds.reuters.com/news/artsculture"),
        businessNews("businessNews.png", "http://feeds.reuters.com/reuters/businessNews"),
        domesticNews("domesticNews.png", "http://feeds.reuters.com/Reuters/domesticNews"),
        entertainmentNews("entertainmentNews.png", "http://feeds.reuters.com/reuters/entertainment"),
        environmentNews("environmentNews.png", "http://feeds.reuters.com/reuters/environment"),
        healthNews("healthNews.png", "http://feeds.reuters.com/reuters/healthNews"),
        lifestyleMolt("lifestyleMolt.png", "http://feeds.reuters.com/reuters/lifestyle"),
        oddlyEnoughNews("oddlyEnoughNews.png", "http://feeds.reuters.com/reuters/oddlyEnoughNews"),
        peopleNews("peopleNews.png", "http://feeds.reuters.com/reuters/peopleNews"),
        politicsNews("politicsNews.png", "http://feeds.reuters.com/Reuters/PoliticsNews"),
        RCOMUS_24("RCOMUS_24.png", "http://feeds.reuters.com/ReutersPictures"),
        scienceNews("scienceNews.png", "http://feeds.reuters.com/reuters/scienceNews"),
        technologyNews("technologyNews.png", "http://feeds.reuters.com/reuters/technologyNews"),
        topNews("topNews.png", "http://feeds.reuters.com/reuters/topNews"),
        vcMedia("vcMedia.png", "http://feeds.reuters.com/news/reutersmedia"),
        worldNews("worldNews.png", "http://feeds.reuters.com/Reuters/worldNews"),
        wtMostRead("wtMostRead.png", "http://feeds.reuters.com/reuters/MostRead");

        private String value;
        private String link;

        Category(String value, String link) {
            this.value = value;
            this.link = link;
        }

        @Override
        public String toString() {
            return value;
        }

        public static Set<String> links() {
            Category[] categories = values();
            Set<String> links = new HashSet<>(categories.length);

            for (Category category : categories) {
                links.add(category.link);
            }

            return links;
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
