package com.example.booklistingapp;

public class Book {

    private String mTitle;
    private String mAuthor;
    private double mAverageRating;

    public Book (String title, String author, double averageRating) {
        mTitle = title;
        mAuthor = author;
        mAverageRating = averageRating;
    }


    public String getTitle ()  { return mTitle; }

    public String getAuthor() { return mAuthor; }

    public double getAverageRating() { return mAverageRating; }


}
