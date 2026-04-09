package com.boardbuddy;

import com.boardbuddy.model.BoardGame;
import com.boardbuddy.model.Collection;
import com.boardbuddy.model.User;
import com.boardbuddy.service.Dashboard;
import com.boardbuddy.service.InputXml;
import com.boardbuddy.service.LoginBackend;
import com.boardbuddy.ui.DashPanel;

// View classes, should be what we actually need since the view classes will access everything else.
// import com.boardbuddy.ui.GameView;

// others
import java.util.ArrayList;

import javax.swing.SwingUtilities;

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
        Collection masterCollection = InputXml.parse(inputPath, "Master", -1);

        if (masterCollection == null) {
            System.out.println("failure");
            return;
        }

        ArrayList<BoardGame> masterList = masterCollection.getGameList();
                System.out.println("Collection: " + masterCollection.getCollectionName());
        System.out.println("Games found: " + masterList.size());
        System.out.println("---");
 
        for (BoardGame game : masterList) {
            System.out.println("Name:        " + game.getName());
            System.out.println("ID:          " + game.getId());
            System.out.println("Year:        " + game.getYear());
            System.out.println("Players:     " + game.getMinPlayers() + " - " + game.getMaxPlayers());
            System.out.println("Play time:   " + game.getPlayTime() + " min");
            System.out.println("Thumbnail:   " + game.getThumbnail());
            System.out.println("---");
        }

        LoginBackend startLogin = new LoginBackend(masterCollection);

        

    }

    // Actual final main goes down here
}
