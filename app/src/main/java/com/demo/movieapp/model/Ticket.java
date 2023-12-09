package com.demo.movieapp.model;

import java.util.Date;

public class Ticket {
    String title;
    Date time;
    int imageLink;
    double stars;
    String[] seats;
    String cinema;

    public Ticket(String title, Date time, int imageLink, double stars, String[] seats, String cinema) {
        this.title = title;
        this.time = time;
        this.imageLink = imageLink;
        this.stars = stars;
        this.seats = seats;
        this.cinema = cinema;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getImageLink() {
        return imageLink;
    }

    public void setImageLink(int imageLink) {
        this.imageLink = imageLink;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public String[] getSeats() {
        return seats;
    }

    public void setSeats(String[] seats) {
        this.seats = seats;
    }

    public String getCinema() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }
}
