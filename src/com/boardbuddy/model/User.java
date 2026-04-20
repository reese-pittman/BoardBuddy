package com.boardbuddy.model;

import java.util.ArrayList;

//this will handle the user data and information
public class User {
    private String username;
    private String passwordHash; // may just be a password, but we may hash it
    private final int userID; // May be used for database purposes
    private final ArrayList<Collection> gameCollections; // Users can have multiple collections of games

    public User(String username, String passwordHash, int userID) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.userID = userID; //(should be unique for each user)
        this.gameCollections = new java.util.ArrayList<>();
    }

    // Getters
    public String getUsername()     { return username; }
    public String getPasswordHash() { return passwordHash; }
    public int getUID() {
        return userID;
    }
    public ArrayList<Collection> getUsersCollections() {
        return gameCollections;
    }

    public void deleteGameCollection(Collection collection) {
        gameCollections.remove(collection);
    }

    // Setters
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    // Just for creating a new array and then adding the new review to it
    public void addReview(Review review) {
        Review.reviewList.add(review);
    }

    public void addGameCollection(Collection collection) {
        gameCollections.add(collection);
    }


}