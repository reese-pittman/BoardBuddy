package com.boardbuddy.model;

import java.util.ArrayList;

//this will handle the user data and information
public class User {
    private String username;
    private String passwordHash; // may just be a password, but we may hash it
    private final int userID; // May be used for database purposes
    private final static ArrayList<User> userList = new ArrayList<>();
    private final ArrayList<Collection> gameCollections; // Users can have multiple collections of games

    public User(String username, String passwordHash, int userID) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.userID = userID; //(should be unique for each user)
        this.gameCollections = new java.util.ArrayList<>();

        addUser(this);
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
    public static ArrayList<User> getUserList() {
        return userList;
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
    
    /**
     * Adds a new collection to a certain user
     * @param collection
     */
    public void addGameCollection(Collection collection) {
        gameCollections.add(collection);
    }

    /**
     * Adds a new user to the userList
     * @param user
     */
    public static void addUser(User user) {
        userList.add(user);
    }

    /**
     * Searches through the userlist to find user with username
     * 
     * @param username desired username to search for.
     * @return null if not found.
     */
    public static User fetchUser(String username) { // TODO: Change to uid maybe because users can have same name
        for (User user : userList) {
            System.err.println(user.getUsername() + " " + user.getUID());
            
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    public String maskPassword(int length) {
        return "*".repeat(length);
    }
}