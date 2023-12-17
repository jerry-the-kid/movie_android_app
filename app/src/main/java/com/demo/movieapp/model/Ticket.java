package com.demo.movieapp.model;

import java.util.ArrayList;
import java.util.Date;

public class Ticket {
    String title;
    String time;
    String imageUrl;
    ArrayList<String> reservedSeats;
    String cinema;
    int roomId;
    Date date;

    public Ticket() {
    }

    public Ticket(String title, String time, String imageUrl, ArrayList<String> reservedSeats, String cinema, int roomId, Date date) {
        this.title = title;
        this.time = time;
        this.imageUrl = imageUrl;
        this.reservedSeats = reservedSeats;
        this.cinema = cinema;
        this.roomId = roomId;
        this.date = date;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<String> getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(ArrayList<String> reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    public String getCinema() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}


