package com.imarneanu.android.newsapp.utils;

import com.imarneanu.android.newsapp.data.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by imarneanu on 8/4/16.
 */
public class JsonParserUtils {

    public static ArrayList<News> getNewsDataFromJson(String jsonString) {
        // These are the names of the JSON objects that need to be extracted.
        final String RESPONSE_DATA = "responseData";
        final String RESPONSE_STATUS = "responseStatus";
        final String FEED = "feed";
        final String ENTRIES = "entries";
        final String TITLE = "title";
        final String CONTENT = "contentSnippet";
        final String DATE = "publishedDate";
        final String LINK = "link";
        final String CATEGORIES = "categories";

        try {
            JSONObject data = new JSONObject(jsonString);
            int responseStatus = data.getInt(RESPONSE_STATUS);
            if (responseStatus != 200) {
                return null;
            }
            JSONObject responseJson = data.getJSONObject(RESPONSE_DATA);
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

                JSONArray categoriesArray = newsData.getJSONArray(CATEGORIES);
                String category = categoriesArray.getString(0);

                news.add(i, new News(title, content, date, link, Enum.valueOf(News.Category.class, category)));
            }
            return news;
        } catch (JSONException e) {
            Log.e(JsonParserUtils.class.getSimpleName(), e.getMessage(), e);
        }
        return null;
    }
}
