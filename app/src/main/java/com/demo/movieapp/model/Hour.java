package com.demo.movieapp.model;

import java.util.ArrayList;

public class Hour {
    String showTimeId;
    String movieId;
    int day;
    String startTime;
    String cinemaName;
    int roomId;
    ArrayList<String> reservedSeats;

    public Hour() {
    }


    public Hour(String showTimeId, String movieId, int day, String startTime, String cinemaName, int roomId, ArrayList<String> reservedSeats) {
        this.showTimeId = showTimeId;
        this.movieId = movieId;
        this.day = day;
        this.startTime = startTime;
        this.cinemaName = cinemaName;
        this.roomId = roomId;
        this.reservedSeats = reservedSeats;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getShowTimeId() {
        return showTimeId;
    }

    public void setShowTimeId(String showTimeId) {
        this.showTimeId = showTimeId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public ArrayList<String> getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(ArrayList<String> reservedSeats) {
        this.reservedSeats = reservedSeats;
    }
}
