package com.boardbuddy.model;

import com.boardbuddy.persistence.OutputXml;
import java.util.ArrayList;

//Class to represent a review for a board game
public class Review {
    private String Title;
    private String Description; 
    private int rating;
    private int userID; // used to link the review to a user
    private int gameID; // used to link the review to a game

    public static ArrayList<Review> reviewList = new ArrayList<>();

    /**
     * Contructs a review object given the parameters.
     * 
     * @param title
     * @param description
     * @param rating
     * @param userID
     * @param gameID
     */
    public Review(String title, String description, int rating, int userID, int gameID) {
        this.Title = title;
        this.Description = description;
        this.rating = rating;
        this.userID = userID;
        this.gameID = gameID; 
    }

    /**
     * Add a new review to a List of Reviews.
     * 
     * @param nReview
     */
    public static void addReview(Review newReview){
        reviewList.add(newReview);
        
        // TODO: TESTING ONLY
        OutputXml.saveReviews();
    }
    
    
    /**
     * 
     * @param request The gameID of the desired game
     * @return Returns a list of all reviews matching the desired gameID.
     */
    public static ArrayList<Review> fetchReviews(int request){
        ArrayList<Review> requestedList = new ArrayList<>();
        for (Review review : reviewList) {
            if (review.getGameID() == request) {
                requestedList.add(review);
            }
        }

        return requestedList;
    }

    // Setters
    public void setTitle(String title) {
        this.Title = title;
    }
    public void setDescription(String description) {
        this.Description = description;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    // Getters
    public String getTitle() {
        return Title;
    }
    public String getDescription() {
        return Description;
    }
    public int getRating() {
        return rating;
    }
    public int getUserID() {
        return userID;
    }
    public int getGameID() {
        return gameID;
    }
    public static ArrayList<Review> getReviews() {
        return reviewList;
    }
}