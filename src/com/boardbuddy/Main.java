package com.boardbuddy;

import com.boardbuddy.model.BoardGame;
import com.boardbuddy.model.Collection;
import com.boardbuddy.service.InputXml;
import java.util.List;

/**
 * Main Program
 */
public class Main {

    

    /**
     * Testing main
     * @param args
     */
    public static void main(String[] args) {
        
        String inputPath = "bgg90Games.xml";

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

    // Actual final main goes down here
}
