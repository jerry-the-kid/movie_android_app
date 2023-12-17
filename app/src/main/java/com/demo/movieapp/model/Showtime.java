package com.demo.movieapp.model;

import java.util.ArrayList;

public class Showtime {
    String id;
    int day;
    String movieId;
    String movieName;
    String cinemaId;
    String cinemaName;
    ArrayList<Room> rooms;

    public Showtime() {
    }

    public Showtime(String id, int day, String movieId, String movieName, String cinemaId, String cinemaName, ArrayList<Room> rooms) {
        this.id = id;
        this.day = day;
        this.movieId = movieId;
        this.movieName = movieName;
        this.cinemaId = cinemaId;
        this.cinemaName = cinemaName;
        this.rooms = rooms;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}
