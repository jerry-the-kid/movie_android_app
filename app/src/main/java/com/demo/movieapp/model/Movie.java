package com.demo.movieapp.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Movie extends BaseObservable {
    String title;
    String director;
    int releaseYear;
    String summary;
    String youtubeID;
    int tomatometer;
    int audienceScore;
    String imageUrl;

    ArrayList<String> categories;
    ArrayList<String> actors;

    String id;

    public Movie() {
    }

    public Movie(String title, String director, int releaseYear, String summary, String youtubeID, int tomatometer, int audienceScore, String imageUrl, ArrayList<String> categories) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.summary = summary;
        this.youtubeID = youtubeID;
        this.tomatometer = tomatometer;
        this.audienceScore = audienceScore;
        this.imageUrl = imageUrl;
        this.categories = categories;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Bindable
    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Bindable
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Bindable
    public String getYoutubeID() {
        return youtubeID;
    }

    public void setYoutubeID(String youtubeID) {
        this.youtubeID = youtubeID;
    }

    @Bindable
    public int getTomatometer() {
        return tomatometer;
    }

    public void setTomatometer(int tomatometer) {
        this.tomatometer = tomatometer;
    }

    @Bindable
    public int getAudienceScore() {
        return audienceScore;
    }

    public void setAudienceScore(int audienceScore) {
        this.audienceScore = audienceScore;
    }

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    public double getStarPoint() {
        double score = (double) (this.audienceScore / 20.0);
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String roundedScoreString = decimalFormat.format(score);
        double roundedScore = Double.parseDouble(roundedScoreString);

        return roundedScore;
    }

    public String getSearchString() {
        StringBuilder searchString = new StringBuilder();

        searchString.append(title).append(",");
        searchString.append(director).append(",");
        searchString.append(releaseYear).append(",");

        if (categories != null && !categories.isEmpty()) {
            searchString.append(String.join(",", categories)).append(",");
        }

        if (actors != null && !actors.isEmpty()) {
            searchString.append(String.join(",", actors));
        }

        return searchString.toString();
    }
}
