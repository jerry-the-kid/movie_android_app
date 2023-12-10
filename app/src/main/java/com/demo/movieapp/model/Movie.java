package com.demo.movieapp.model;

public class Movie {
    String title;
    String director;
    int releaseYear;
    String summary;
    String youtubeID;
    int tomatometer;
    int audienceScore;
    String imageUrl;

    String id;

    public Movie() {
    }

    public Movie(String title, String director, int releaseYear, String summary, String youtubeID, int tomatometer, int audienceScore, String imageUrl) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.summary = summary;
        this.youtubeID = youtubeID;
        this.tomatometer = tomatometer;
        this.audienceScore = audienceScore;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getYoutubeID() {
        return youtubeID;
    }

    public void setYoutubeID(String youtubeID) {
        this.youtubeID = youtubeID;
    }

    public int getTomatometer() {
        return tomatometer;
    }

    public void setTomatometer(int tomatometer) {
        this.tomatometer = tomatometer;
    }

    public int getAudienceScore() {
        return audienceScore;
    }

    public void setAudienceScore(int audienceScore) {
        this.audienceScore = audienceScore;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
