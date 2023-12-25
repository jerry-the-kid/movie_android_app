package com.demo.movieapp.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;

    private String imageUrl;

    public LoggedInUser(String userId, String displayName, String imageUrl) {
        this.userId = userId;
        this.displayName = displayName;
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}