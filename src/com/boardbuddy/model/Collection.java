package com.boardbuddy.model;

import com.boardbuddy.service.BoardGame;
import com.boardbuddy.InputXml;

import java.util.ArrayList;
import java.util.List;

//this will handle the user data and information
public class Collection {
    private String collectionName;
    private int userID;     // Used to link the collection to a user
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

    // testing 
    public static void main() {
        
        String inputPath = "bgg3Games.xml";

        /**
         * Importing Collection and printing gamelist
         */
        Collection result = InputXml.parse(inputPath, "TestCollection", -1);

        if (result == null) {
            System.out.println("failure");
            return;
        }

        List<BoardGame> games = result.getGameList();
                System.out.println("Collection: " + result.getCollectionName());
        System.out.println("Games found: " + games.size());
        System.out.println("---");
 
        for (BoardGame game : games) {
            System.out.println("Name:        " + game.getName());
            System.out.println("ID:          " + game.getId());
            System.out.println("Year:        " + game.getYear());
            System.out.println("Players:     " + game.getMinPlayers() + " - " + game.getMaxPlayers());
            System.out.println("Play time:   " + game.getPlayTime() + " min");
            System.out.println("Thumbnail:   " + game.getThumbnail());
            System.out.println("---");
        }




    }
}

