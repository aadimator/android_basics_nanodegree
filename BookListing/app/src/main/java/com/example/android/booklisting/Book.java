package com.example.android.booklisting;

/**
 * Created by Aadam on 10/1/2016.
 */

public class Book {

    private String mTitle;
    private String mAuthor;
    private String mPublishedDate;

    public Book(String title, String author, String publishedDate) {
        mTitle = title;
        mAuthor = author;
        mPublishedDate = publishedDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

}
