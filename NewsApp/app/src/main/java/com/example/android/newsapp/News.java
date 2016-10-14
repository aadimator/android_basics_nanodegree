package com.example.android.newsapp;

/**
 * Created by Aadam on 10/3/2016.
 */

public class News {

    private String mTitle;
    private String mTrailText = "";
    private String mUrl;

    public News(String title, String trailText, String url) {
        mTitle = title;
        mTrailText = trailText;
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public boolean hasTrailText() {
        return mTrailText != "";
    }

    public String getTrailText() {
        return mTrailText;
    }

    public String getUrl() {
        return mUrl;
    }
}
