package com.boardbuddy.model;

import java.util.ArrayList;

import javax.swing.JFrame;

import com.boardbuddy.service.Dashboard;
import com.boardbuddy.ui.DashPanel;
import com.boardbuddy.ui.ProfilePanelUI;

//this will handle the user data and information
public class Collection {
    private String collectionName;
    private final int userID;     // TODO: Used to link the collection to a user
    private ArrayList<BoardGame> gameList = new ArrayList<>(); // List of games in the collection

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

    public boolean addGame(BoardGame game) {
        for (BoardGame currentGame : gameList) {
            if(currentGame == game) {
                return false;
            }
        }
        gameList.add(game);
        return true;
    }

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
