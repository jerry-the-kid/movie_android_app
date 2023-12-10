package com.demo.movieapp.model;

import java.util.List;

public class GlobalState {
    private static GlobalState instance;
    private List<Movie> movieList;

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
}
