package com.boardbuddy.Model;

import com.boardbuddy.Model.Game;
import java.lang.reflect.Array;
import java.util.ArrayList;;

//this will handle the user data and information
public class Collection {
    private String collectionName;
    private int userID;     // Used to link the collection to a user
    private ArrayList<Game> games; // List of games in the collection

    public Collection(String collectionName, int userID) {
        this.collectionName = collectionName;
        this.userID = userID;
        this.games = new ArrayList<>();
    }

    // Getters
    public String getCollectionName() {
        return collectionName;
    }

    public Collection getCollection() {
        return this;
    }

    public Game getGame(int index) {
        if (index >= 0 && index < games.size()) {
            return games.get(index);
        }
        return null; // or throw an exception
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    // Setters

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public void addGame(Game game) {
        games.add(game);
    }
}