package com.demo.movieapp.model;

import java.util.Date;

public class Notification {
    String title;
    Date time;
    String cinema;
    String user_id;

    public Notification() {
    }

    public Notification(String title, Date time, String cinema, String user_id) {
        this.title = title;
        this.time = time;
        this.cinema = cinema;
        this.user_id = user_id;
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

    public String getCinema() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
