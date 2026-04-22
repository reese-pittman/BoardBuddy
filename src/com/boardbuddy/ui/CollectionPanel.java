package com.boardbuddy.ui;

import com.boardbuddy.model.BoardGame;
import com.boardbuddy.model.Collection;
import com.boardbuddy.model.User;
import com.boardbuddy.service.Dashboard;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class CollectionPanel extends JFrame {
    private final ArrayList<Collection> userCollections;
    private final User user;
    private final Dashboard dashboard;

    private JComboBox<String> collectionDropdown;
    private JTextField searchField;
    private JButton dashboardButton;
    private JButton profileButton;
    private JButton deleteCollectionButton;
    private JButton createCollectionButton;

    private JLabel titleLabel;
    private JPanel gamesPanel;

    public CollectionPanel(ArrayList<Collection> userCollections, User user, Dashboard dashboard) {
        this.userCollections = userCollections;
        this.user = user;
        this.dashboard = dashboard;

        setTitle("BoardBuddy Collections");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setDefaultCollection();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Top bar
        // Adding a drop box to sort though the games 
        JComboBox<String> sortBox = new JComboBox<>(new String[]{"Default", "Min Players", "Max Players", "Year", "Play Time"});
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        collectionDropdown = new JComboBox<>();
        searchField = new JTextField(20);
        dashboardButton = new JButton("Dashboard");
        profileButton = new JButton("Profile");
        createCollectionButton = new JButton("Create Collection");
        deleteCollectionButton = new JButton("Delete Collection");

        loadCollectionNames();

        topPanel.add(collectionDropdown, BorderLayout.CENTER);
        navPanel.add(searchField);
        navPanel.add(createCollectionButton);
        navPanel.add(dashboardButton);
        navPanel.add(profileButton);
        navPanel.add(deleteCollectionButton);
        topPanel.add(navPanel, BorderLayout.EAST);
        navPanel.add(new JLabel("Sort by:"));
        navPanel.add(sortBox);

        add(topPanel, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel(new BorderLayout());

        titleLabel = new JLabel("Collections");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.add(titleLabel, BorderLayout.NORTH);

        gamesPanel = new JPanel();
        gamesPanel.setLayout(new GridLayout(0, 3, 10, 10));
        gamesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(gamesPanel);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Events
        collectionDropdown.addActionListener(e -> {
            int selectedIndex = collectionDropdown.getSelectedIndex();

            if (selectedIndex >= 0 && selectedIndex < userCollections.size()) {
                Collection selectedCollection = userCollections.get(selectedIndex);
                titleLabel.setText(selectedCollection.getCollectionName());
                loadGames(selectedCollection.getGameList());
            }
        });

        /**
         * Searching by getting the active collection same as above, then doing the same sorting in dashboard.
         */
        searchField.addActionListener(e -> {
            int selectedIndex = collectionDropdown.getSelectedIndex();

            Collection selectedCollection = userCollections.get(selectedIndex);
            ArrayList<BoardGame> activeGames = selectedCollection.getGameList();
            String query = searchField.getText();
            ArrayList<BoardGame> filteredGames = dashboard.onSearch(query, activeGames);
            loadGames(filteredGames);
        });

        createCollectionButton.addActionListener(e -> {
            String collectionName = JOptionPane.showInputDialog(
                    this,
                    "Enter new collection name:"
            );

            if (collectionName == null) {
                return;
            }

            collectionName = collectionName.trim();

            if (collectionName.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Collection name cannot be empty.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            for (Collection collection : userCollections) {
                if (collection.getCollectionName() != null
                        && collection.getCollectionName().equalsIgnoreCase(collectionName)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "A collection with that name already exists.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
            }

            Collection newCollection = new Collection(collectionName, -1);
            userCollections.add(newCollection);

            loadCollectionNames();
            collectionDropdown.setSelectedItem(collectionName);
            titleLabel.setText(collectionName);
            loadGames(newCollection.getGameList());
        });

        deleteCollectionButton.addActionListener(e -> {
            int selectedIndex = collectionDropdown.getSelectedIndex();

            if (selectedIndex >= 0 && selectedIndex < userCollections.size()) {
                Collection selectedCollection = userCollections.get(selectedIndex);

                int choice = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete the collection \""
                        + selectedCollection.getCollectionName() + "\"?",
                        "Delete Collection",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {
                    user.deleteGameCollection(selectedCollection);
                    loadCollectionNames();
                    setDefaultCollection();
                }
            }
        });
        // TODO: Rename a collection button

        dashboardButton.addActionListener(e -> {
            new DashPanel(new Dashboard(user)).setVisible(true);
            dispose();
        });

        profileButton.addActionListener(e -> {
            new ProfilePanelUI(user).setVisible(true);
            dispose();
        });

    
    // action listner for the drop box
    sortBox.addActionListener(e -> {
        int selectedIndex = collectionDropdown.getSelectedIndex();

        if (selectedIndex < 0 || selectedIndex >= userCollections.size()) return;
        ArrayList<BoardGame> sorted = new ArrayList<>(userCollections.get(selectedIndex).getGameList());

        String selected = (String) sortBox.getSelectedItem();
        switch (selected) {
            case "Min Players":
                sorted.sort((a, b) -> a.getMinPlayers() - b.getMinPlayers());
                break;
            case "Max Players":
                sorted.sort((a, b) -> a.getMaxPlayers() - b.getMaxPlayers());
                break;
            case "Year":
                sorted.sort((a, b) -> a.getYear() - b.getYear());
                break;
            case "Play Time":
                sorted.sort((a, b) -> a.getPlayTime() - b.getPlayTime());
            break;
        }

        loadGames(sorted);   });
    }

    private void loadCollectionNames() {
        collectionDropdown.removeAllItems();

        if (userCollections == null) {
            return;
        }

        for (Collection collection : userCollections) {
            if (collection != null && collection.getCollectionName() != null) {
                collectionDropdown.addItem(collection.getCollectionName());
            }
        }
    }

    private void setDefaultCollection() {
        if (userCollections == null || userCollections.isEmpty()) {
            titleLabel.setText("No Collections");
            loadGames(new ArrayList<>());
            return;
        }

        int favoriteIndex = 0;

        for (int i = 0; i < userCollections.size(); i++) {
            Collection collection = userCollections.get(i);

            if (collection != null
                    && collection.getCollectionName() != null
                    && collection.getCollectionName().equalsIgnoreCase("Favorite")) {
                favoriteIndex = i;
                break;
            }
        }

        collectionDropdown.setSelectedIndex(favoriteIndex);

        Collection selectedCollection = userCollections.get(favoriteIndex);
        titleLabel.setText(selectedCollection.getCollectionName());
        loadGames(selectedCollection.getGameList());
    }

    private void loadGames(ArrayList<BoardGame> games) {
        gamesPanel.removeAll();

        if (games == null || games.isEmpty()) {
            gamesPanel.add(new JLabel("No games found."));
        } else {
            for (BoardGame game : games) {
                JButton gameButton = new JButton(game.getName());
                gameButton.setPreferredSize(new Dimension(200, 100));

                gameButton.addActionListener(e -> {
                    GameView selected = new GameView(user, game.getId());
                    selected.setGame(game);

                    JFrame sFrame = new JFrame(game.getName());
                    sFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    sFrame.add(selected);
                    sFrame.setSize(900,600);
                    sFrame.setLocationRelativeTo(null);
                    sFrame.setVisible(true);
                });

                gamesPanel.add(gameButton);
            }
        }

        gamesPanel.revalidate();
        gamesPanel.repaint();
    }
}

