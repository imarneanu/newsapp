package com.imarneanu.android.newsapp.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Created by imarneanu on 7/29/16.
 */
public class News implements Comparable<News> {
    public String title;
    public String content;
    public String date;
    public String link;

    public Category category;

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

        /*
         * method used to get all category links
         */
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

    @Override
    public int compareTo(News news) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(news.date));
            Date comparedDate = cal.getTime();
            cal.setTime(sdf.parse(date));
            Date comparableDate = cal.getTime();

            // sort news to show from newest to oldest news - no matter the category
            if (comparedDate.after(comparableDate)) {
                return 1;
            } else if (comparedDate.before(comparableDate)) {
                return -1;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
