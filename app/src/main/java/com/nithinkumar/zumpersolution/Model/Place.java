package com.nithinkumar.zumpersolution.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nithin on 10/31/2017.
 */

public class Place {
    private double lat;
    private double lon;
    private String name;
    private String address;
    private double rating;
    private String reference;
    private String mobileNUmber;
    private List<Review> reviews;
    private List<Photo> photos;

    public String getMobileNUmber() {
        return mobileNUmber;
    }

    public void setMobileNUmber(String mobileNUmber) {
        this.mobileNUmber = mobileNUmber;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Place(double lat, double lon, String address, String name, double ratings, String reference) {
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.address = address;
        this.rating = ratings;
        this.reference = reference;
        this.reviews = new ArrayList<>();
      //  this.photos = new ArrayList<>();
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
