package com.imarneanu.android.newsapp.data;

/**
 * Created by imarneanu on 7/29/16.
 */
public class News {
    public String sectionId;
    public String sectionName;
    public String webPublicationDate;
    public String webTitle;
    public String webUrl;

    public News(String sectionId, String sectionName, String webPublicationDate, String webTitle, String webUrl) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
    }

    public String getCategoryImage() {
        return sectionId.concat(".png");
    }

}
