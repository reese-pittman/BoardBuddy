package com.boardbuddy.ui;

import com.boardbuddy.model.BoardGame;
import com.boardbuddy.model.Collection;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class CollectionPanel extends JFrame {

    private final ArrayList<Collection> userCollections;

    private JComboBox<String> collectionDropdown;
    private JButton dashboardButton;
    private JButton profileButton;

    private JLabel titleLabel;
    private JPanel gamesPanel;

    public CollectionPanel(ArrayList<Collection> userCollections) {
        this.userCollections = userCollections;

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

        loadCollectionNames();

        topPanel.add(collectionDropdown, BorderLayout.CENTER);

        navPanel.add(dashboardButton);
        navPanel.add(profileButton);
        topPanel.add(navPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Center
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

        dashboardButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Go to Dashboard");
        });

        profileButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Go to Profile Page");
        });
    }

    private void loadCollectionNames() {
        collectionDropdown.removeAllItems();

        for (Collection collection : userCollections) {
            collectionDropdown.addItem(collection.getCollectionName());
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
            if (userCollections.get(i).getCollectionName().equalsIgnoreCase("Favorite")) {
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
                                "Remove \"" + game.getName() + "\" from " + selectedCollection.getCollectionName() + "?",
                                "Remove Game",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (choice == JOptionPane.YES_OPTION) {
                            boolean removed = selectedCollection.removeGame(game);

                            if (removed) {
                                JOptionPane.showMessageDialog(
                                        this,
                                        game.getName() + " was removed from " + selectedCollection.getCollectionName() + "."
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