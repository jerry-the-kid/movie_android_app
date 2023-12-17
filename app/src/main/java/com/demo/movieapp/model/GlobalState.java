package com.demo.movieapp.model;

import java.util.List;

public class GlobalState {
    private static GlobalState instance;
    private List<Movie> movieList;
    private Movie currentMovie;
    private Hour hour;

    private GlobalState() {
        // private constructor to prevent instantiation
    }

    public static synchronized GlobalState getInstance() {
        if (instance == null) {
            instance = new GlobalState();
        }
        return instance;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public static void setInstance(GlobalState instance) {
        GlobalState.instance = instance;
    }

    public Hour getHour() {
        return hour;
    }

    public void setHour(Hour hour) {
        this.hour = hour;
    }

    public Movie getCurrentMovie() {
        return currentMovie;
    }

    public void setCurrentMovie(Movie currentMovie) {
        this.currentMovie = currentMovie;
    }
}
