package com.boardbuddy.model;

import java.util.ArrayList;


/**
 * This is the way that games are stored together for users
 * 
 */
public class Collection {
    private String collectionName;
    private final int userID;     // TODO: Used to link the collection to a user
    private ArrayList<BoardGame> gameList = new ArrayList<>(); // List of games in the collection

    /**
     * Constructor for the Collection Class
     * 
     * @param collectionName
     * @param userID
     */
    public Collection(String collectionName, int userID) {
        this.collectionName = collectionName;
        this.userID = userID;
        this.gameList = new ArrayList<>();
    }

    // Getters
    public String getCollectionName() {
        return collectionName;
    }
    public ArrayList<BoardGame> getGameList() {
        return gameList;
    }
    public int getCollectionUID() {
        return userID;
    }
    public Collection getCollection() {
        return this;
    }
    public BoardGame getGame(int index) {
        if (index >= 0 && index < gameList.size()) {
            return gameList.get(index);
        }
        return null; // or throw an exception
    }

    // Setters
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    /**
     * Adds a game to the collection
     * @param game
     * @return true if the game was successfully added, false if the game was already in the collection
     */
    public boolean addGame(BoardGame game) {
        for (BoardGame currentGame : gameList) {
            if(currentGame == game) {
                return false;
            }
        }
        gameList.add(game);
        return true;
    }

    /**
     * Removes a game from the collection
     * @param game
     * @return true if the game was successfully removed, false if the game was not found in the collection
     */
    public boolean removeGame(BoardGame game) {
        for (BoardGame currentGame : gameList) {
            if(currentGame == game) {
                gameList.remove(game);
                return true;
            }
        }
        
        return false;
    }

}
