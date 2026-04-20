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

    private JComboBox<String> collectionDropdown;
    private JButton dashboardButton;
    private JButton profileButton;
    private JButton deleteCollectionButton;
    private JButton createCollectionButton;

    private JLabel titleLabel;
    private JPanel gamesPanel;

    public CollectionPanel(ArrayList<Collection> userCollections, User user) {
        this.userCollections = userCollections;
        this.user = user;

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
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        collectionDropdown = new JComboBox<>();
        dashboardButton = new JButton("Dashboard");
        profileButton = new JButton("Profile");
        createCollectionButton = new JButton("Create Collection");
        deleteCollectionButton = new JButton("Delete Collection");

        loadCollectionNames();

        topPanel.add(collectionDropdown, BorderLayout.CENTER);

        navPanel.add(createCollectionButton);
        navPanel.add(dashboardButton);
        navPanel.add(profileButton);
        navPanel.add(deleteCollectionButton);
        topPanel.add(navPanel, BorderLayout.EAST);

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

        dashboardButton.addActionListener(e -> {
            new DashPanel(new Dashboard(user)).setVisible(true);
            dispose();
        });

        profileButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Connect profile navigation here.");
        });
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
                    int selectedIndex = collectionDropdown.getSelectedIndex();

                    if (selectedIndex >= 0 && selectedIndex < userCollections.size()) {
                        Collection selectedCollection = userCollections.get(selectedIndex);

                        int choice = JOptionPane.showConfirmDialog(
                                this,
                                "Remove \"" + game.getName() + "\" from "
                                + selectedCollection.getCollectionName() + "?",
                                "Remove Game",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (choice == JOptionPane.YES_OPTION) {
                            boolean removed = selectedCollection.removeGame(game);

                            if (removed) {
                                JOptionPane.showMessageDialog(
                                        this,
                                        game.getName() + " was removed from "
                                        + selectedCollection.getCollectionName() + "."
                                );
                                loadGames(selectedCollection.getGameList());
                            } else {
                                JOptionPane.showMessageDialog(
                                        this,
                                        "Could not remove game.",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE
                                );
                            }
                        }
                    }
                });

                gamesPanel.add(gameButton);
            }
        }

        gamesPanel.revalidate();
        gamesPanel.repaint();
    }
}
