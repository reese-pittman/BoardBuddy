// <<<<<<< HEAD:src/com/boardbuddy/Model/Collection.java
// package com.boardbuddy.Model;

// import com.boardbuddy.Model.Game;
// import java.lang.reflect.Array;
// import java.util.ArrayList;;
// =======
package com.boardbuddy.model;

import java.util.ArrayList;
import java.util.List;
// >>>>>>> origin:src/com/boardbuddy/model/Collection.java

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

    public Collection getCollection() {
        return this;
    }

    // public Game getGame(int index) {
    //     if (index >= 0 && index < gameList.size()) {
    //         return games.get(index);
    //     }
    //     return null; // or throw an exception
    // }

    // public ArrayList<BoardGame> getGames() {
    //     return gameList;
    // }

    // Setters
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public void addGame(BoardGame game) {
        gameList.add(game);
    }


}
