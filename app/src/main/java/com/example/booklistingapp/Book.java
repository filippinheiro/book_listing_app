package com.example.booklistingapp;

import android.graphics.Bitmap;
import android.media.Image;

public class Book {

    private String mTitle;
    private String mAuthor;
    private Bitmap mThumbnail;
    private double mAverageRating;

    public Book (String title, String author, double averageRating, Bitmap thumbnail) {
        mTitle = title;
        mAuthor = author;
        mThumbnail = thumbnail;
        mAverageRating = averageRating;
    }


    public String getTitle ()  { return mTitle; }

    public String getAuthor() { return mAuthor; }

    public double getAverageRating() { return mAverageRating; }

    public Bitmap getThumbnail() { return mThumbnail;  }


}
