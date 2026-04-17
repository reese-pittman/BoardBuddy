package com.boardbuddy;

import com.boardbuddy.service.InputXml;
import com.boardbuddy.service.LoginBackend;

/**
 * Main Program
 */
public class Main {

    

    /**
     * Testing main
     * @param args
     */
    public static void main(String[] args) {
        
        /**
         * Importing Collection and printing gamelist
         */
        // String inputPath = "bgg90Games.xml";
        // Collection masterCollection = InputXml.parse(inputPath, "Master", -1);

        // if (masterCollection == null) {
        //     System.out.println("failure");
        //     return;
        // }
        // ArrayList<BoardGame> masterList = masterCollection.getGameList();
        //         System.out.println("Collection: " + masterCollection.getCollectionName());
        // System.out.println("Games found: " + masterList.size());
        // System.out.println("---");
 
        // for (BoardGame game : masterList) {
        //     System.out.println("Name:        " + game.getName());
        //     System.out.println("ID:          " + game.getId());
        //     System.out.println("Year:        " + game.getYear());
        //     System.out.println("Players:     " + game.getMinPlayers() + " - " + game.getMaxPlayers());
        //     System.out.println("Play time:   " + game.getPlayTime() + " min");
        //     System.out.println("Thumbnail:   " + game.getThumbnail());
        //     System.out.println("---");
        // }


        InputXml.parse("reviews.xml", "Reviews", -1); // return value can be ignored
        InputXml.parse("users.xml", "Users", -1);

        // Start the chain to login -> dashboard
        // Unused is okay
        @SuppressWarnings("unused")
        LoginBackend startLogin = new LoginBackend();

        

    }

}
