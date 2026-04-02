package com.boardbuddy.service;

import com.boardbuddy.model.BoardGame;
import com.boardbuddy.model.User;
import com.boardbuddy.model.Collection;
import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Dashboard {

    private Collection activeCollection;
    private List<Collection> usersCollections;
    private User user;
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
                collection.getGames() != null &&
                !collection.getGames().isEmpty()) {
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
            activeCollection.getGames() != null &&
            !activeCollection.getGames().isEmpty()) {
            baseGames = activeCollection.getGames();
        } else {
            baseGames = getRandomGames(allDatabaseGames, 10);
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
    public ArrayList<BoardGame> onSearch(String query, ArrayList<Game> allDatabaseGames) {
        this.activeSearchQuery = (query == null) ? "" : query.trim().toLowerCase();
        return getDashboardGames(allDatabaseGames);
    }

    /**
     * Called when the user selects a collection by name.
     */
    public ArrayList<BoardGame> onCollectionSelected(String collectionName, ArrayList<Game> allDatabaseGames) {
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
    public BoardGame onGameSelected(BoardGame game) {
        return game;
    }

    /**
     * Temporary version until persistence exists.
     * Just updates the object in memory.
     */
    public void recordPlay(BoardGame game) {
        if (game != null) {
            game.incrementPlays();
        }
    }

    private ArrayList<BoardGame> applySearchFilter(ArrayList<BoardGame> games) {
        if (games == null) {
            return new ArrayList<>();
        }

        if (activeSearchQuery == null || activeSearchQuery.isBlank()) {
            return games;
        }

        // Filter games based on search query (case-insensitive)
        return games.stream()
                .filter(game -> game != null && game.getName() != null && game.getName().toLowerCase().contains(activeSearchQuery))
                .collect(Collectors.toList());
    }

    // Utility method to get random games from the database
    private ArrayList<BoardGame> getRandomGames(ArrayList<BoardGame> allGames, int limit) {
        if (allGames == null || allGames.isEmpty()) {
            return new ArrayList<>();
        }

        List<BoardGame> copy = new ArrayList<>(allGames);
        Collections.shuffle(copy);

        return copy.stream().limit(limit).collect(Collectors.toList());
    }
}