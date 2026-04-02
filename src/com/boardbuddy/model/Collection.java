package com.boardbuddy.model;

import java.util.ArrayList;
import java.util.List;

//this will handle the user data and information
public class Collection {
    private String collectionName;
    private int userID;     // TODO: Used to link the collection to a user
    private List<BoardGame> gameList = new ArrayList<>(); // List of games in the collection

    public Collection(String collectionName, int userID) {
        this.collectionName = collectionName;
        this.userID = userID;
        this.gameList = new ArrayList<>();
    }

    // Getters
    public String getCollectionName() {
        return collectionName;
    }
    public List<BoardGame> getGameList() {
        return gameList;
    }

    // Setters
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public void addGame(BoardGame game) {
        gameList.add(game);
    }


}
