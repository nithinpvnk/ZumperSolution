package com.nithinkumar.zumpersolution.Model;

/**
 * Created by Nithin on 10/31/2017.
 */

public class Review{
    private String author;
    private long time;
    private double rating;
    private String reviewText;

    public Review(String author, long time, double rating, String reviewText) {
        this.author = author;
        this.time = time;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    public String getAuthor() {
        return author;
    }

    public long getTime() {
        return time;
    }

    public double getRating() {
        return rating;
    }

    public String getReviewText() {
        return reviewText;
    }
}