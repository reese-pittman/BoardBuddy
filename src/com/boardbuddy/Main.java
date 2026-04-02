package com.boardbuddy;

import com.boardbuddy.model.BoardGame;
import com.boardbuddy.model.Collection;
import com.boardbuddy.model.User;
import com.boardbuddy.service.Dashboard;
import com.boardbuddy.service.InputXml;
import com.boardbuddy.ui.DashPanel;

// View classes, should be what we actually need since the view classes will access everything else.
// import com.boardbuddy.ui.GameView;

// others
import java.util.List;

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




        // DASHBOARD
        SwingUtilities.invokeLater(() -> {

            User testUser = new User("test","pass", -1);
            Dashboard testDash = new Dashboard(testUser);

            DashPanel panel = new DashPanel(testDash,result.getGameList());

            panel.setVisible(true);

        });
        

    }

    // Actual final main goes down here
}
