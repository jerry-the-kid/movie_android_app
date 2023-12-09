package com.demo.movieapp.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CinemaFilmSchedule {
    String movieName;
    String cinemaName;
    List<String> hours;

    public CinemaFilmSchedule(String movieName, String cinemaName, List<String> hours) {
        this.movieName = movieName;
        this.cinemaName = cinemaName;
        this.hours = hours;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public List<String> getHours() {
        return hours;
    }

    public void setHours(List<String> hours) {
        this.hours = hours;
    }
}
