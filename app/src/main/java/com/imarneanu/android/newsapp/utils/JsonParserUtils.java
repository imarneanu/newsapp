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
        final String RESPONSE_DATA = "response";
        final String RESPONSE_STATUS = "status";
        final String RESULTS = "results";
        final String SECTION_ID = "sectionId";
        final String SECTION_NAME = "sectionName";
        final String WEB_PUBLICATION_DATE = "webPublicationDate";
        final String WEB_TITLE = "webTitle";
        final String WEB_URL = "webUrl";

        try {
            JSONObject data = new JSONObject(jsonString).getJSONObject(RESPONSE_DATA);
            String responseStatus = data.getString(RESPONSE_STATUS);
            if (responseStatus.compareTo("ok") != 0) {
                return null;
            }
            JSONArray resultsArray = data.getJSONArray(RESULTS);

            ArrayList<News> news = new ArrayList<>(resultsArray.length());

            for (int i = 0; i < resultsArray.length(); i++) {
                // Get the JSON object representing the news
                JSONObject newsData = resultsArray.getJSONObject(i);

                String sectionId = newsData.getString(SECTION_ID);
                String sectionName = newsData.getString(SECTION_NAME);
                String webPublicationDate = newsData.getString(WEB_PUBLICATION_DATE);
                String webTitle = newsData.getString(WEB_TITLE);
                String webUrl = newsData.getString(WEB_URL);

                news.add(i, new News(sectionId, sectionName, webPublicationDate, webTitle, webUrl));
            }
            return news;
        } catch (JSONException e) {
            Log.e(JsonParserUtils.class.getSimpleName(), e.getMessage(), e);
        }
        return null;
    }
}
