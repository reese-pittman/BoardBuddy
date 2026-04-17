package com.boardbuddy.service;

import com.boardbuddy.model.BoardGame;
import com.boardbuddy.model.Collection;
import com.boardbuddy.model.User;
import com.boardbuddy.ui.GameView;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;

public class Dashboard {

    private Collection activeCollection;
    private final ArrayList<Collection> usersCollections;
    private final User user;
    private String activeSearchQuery = "";

    public Dashboard(User user) {
        this.user = user;
        this.usersCollections = user.getUsersCollections();
        this.activeCollection = chooseDefaultCollection();
    }

    /**
     * Picks the collection to show on dashboard startup.
     *
     * This is the Process:
     * 1. If user has collections, use the first non-empty one.
     * 2. If all collections are empty, defualt to Favorites.
     * 3. If nothing works, return null and dashboard will use random games.
     */
    private Collection chooseDefaultCollection() {
        if (usersCollections == null || usersCollections.isEmpty()) {
            return null;
        }

        for (Collection collection : usersCollections) {
            if (collection != null &&
                collection.getGameList() != null &&
                !collection.getGameList().isEmpty()) {
                return collection;
            }
        }

        for (Collection collection : usersCollections) {
            if (collection != null &&
                collection.getCollectionName() != null &&
                collection.getCollectionName().equalsIgnoreCase("Favorites")) {
                return collection;
            }
        }

        return null;
    }

    /**
     * Returns games for the dashboard.
     *
     * If active collection has games, return those.
     * Otherwise return random games from all available database games.
     */
    public ArrayList<BoardGame> getDashboardGames(ArrayList<BoardGame> allDatabaseGames) {
        ArrayList<BoardGame> baseGames;

        if (activeCollection != null &&
            activeCollection.getGameList() != null &&
            !activeCollection.getGameList().isEmpty()) {
            baseGames = activeCollection.getGameList();
        } else {
            baseGames = getRandomGames(allDatabaseGames, 10);
            // baseGames = getRandomGames(allDatabaseGames, allDatabaseGames.size());
        }

        return applySearchFilter(baseGames);
    }

    /**
     * Returns the active collection name for display purposes.
     */
    public String getActiveCollectionName() {
        if (activeCollection == null) {
            return "Recommended Games";
        }
        return activeCollection.getCollectionName();
    }

    /**
     * Called when user searches.
     */
    public ArrayList<BoardGame> onSearch(String query, ArrayList<BoardGame> allDatabaseGames) {
        this.activeSearchQuery = (query == null) ? "" : query.trim().toLowerCase();

        ArrayList<BoardGame> filtered = applySearchFilter(allDatabaseGames);
        filtered.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        return filtered;
    }


    /**
     * Called when the user selects a collection by name.
     */
    public ArrayList<BoardGame> onCollectionSelected(String collectionName, ArrayList<BoardGame> allDatabaseGames) {
        if (collectionName == null || usersCollections == null) {
            return getDashboardGames(allDatabaseGames);
        }

        for (Collection collection : usersCollections) {
            if (collection != null &&
                collection.getCollectionName() != null &&
                collection.getCollectionName().equalsIgnoreCase(collectionName)) {
                this.activeCollection = collection;
                break;
            }
        }

        return getDashboardGames(allDatabaseGames);
    }

    /**
     * Called when user clicks a game.
     */
    public void onGameSelected(BoardGame game) {
        GameView selected = new GameView(user, game.getId());
        selected.setGame(game);

        JFrame sFrame = new JFrame(game.getName());
        sFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        sFrame.add(selected);
        sFrame.setSize(420,380);
        sFrame.setLocationRelativeTo(null);
        sFrame.setVisible(true);
    }

    private ArrayList<BoardGame> applySearchFilter(ArrayList<BoardGame> games) {
        if (games == null) {
            return new ArrayList<>();
        }

        ArrayList<BoardGame> filtered = new ArrayList<>();
        for (BoardGame game : games) {
            if (game != null && game.getName() != null && game.getName().toLowerCase().contains(activeSearchQuery)) {
                filtered.add(game);
            }
        }

        return filtered;
    }

    // Utility method to get random games from the database
    private ArrayList<BoardGame> getRandomGames(ArrayList<BoardGame> allGames, int limit) {
        if (allGames == null || allGames.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<BoardGame> copy = new ArrayList<>(allGames);
        Collections.shuffle(copy);

        return new ArrayList<>(copy.subList(0, Math.min(limit, copy.size())));
    }
}