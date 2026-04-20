package com.boardbuddy.ui;

import com.boardbuddy.model.BoardGame;
import com.boardbuddy.model.Collection;
import com.boardbuddy.service.Dashboard;
import com.boardbuddy.service.InputXml;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class DashPanel extends JFrame {

    private final Dashboard dashboard;
    private final ArrayList<BoardGame> allDatabaseGames;

    private JTextField searchField;
    private JButton collectionsButton;
    private JButton profileButton;

    private JLabel titleLabel;
    private JPanel gamesPanel;

    public DashPanel(Dashboard dashboard) {
        this.dashboard = dashboard;

        // MASTER game collection
        String inputPath = "bgg90Games.xml";
        Collection master = InputXml.parse(inputPath, "Master", -1);
        //

        allDatabaseGames = (master != null) ? master.getGameList() : new ArrayList<>();

        setTitle("BoardBuddy Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadGames(dashboard.getDashboardGames(allDatabaseGames));
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // ─── Top Bar ─────────────────────────────────────────────
        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        searchField = new JTextField(20);
        collectionsButton = new JButton("Collections");
        profileButton = new JButton("Profile");

        topPanel.add(searchField, BorderLayout.CENTER);

        navPanel.add(collectionsButton);
        navPanel.add(profileButton);

        topPanel.add(navPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // ─── Center Content ─────────────────────────────────────
        JPanel centerPanel = new JPanel(new BorderLayout());

        titleLabel = new JLabel(dashboard.getActiveCollectionName());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        centerPanel.add(titleLabel, BorderLayout.NORTH);

        gamesPanel = new JPanel();
        gamesPanel.setLayout(new GridLayout(0, 3, 10, 10));
        gamesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(gamesPanel);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // ─── Event Handlers ─────────────────────────────────────
        searchField.addActionListener(e -> {
            String query = searchField.getText();
            ArrayList<BoardGame> filteredGames = dashboard.onSearch(query, allDatabaseGames);
            titleLabel.setText(dashboard.getActiveCollectionName());
            loadGames(filteredGames);
        });

        collectionsButton.addActionListener(e -> {
            new CollectionPanel(dashboard.getUser().getUsersCollections()).setVisible(true);
            dispose();
        });

        profileButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Connect profile navigation here.");
        });
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
                    dashboard.onGameSelected(game);
                });

                gamesPanel.add(gameButton);
            }
        }

        gamesPanel.revalidate();
        gamesPanel.repaint();
    }

    private void openCollectionsMenu() {
        String collectionName = JOptionPane.showInputDialog(
                this,
                "Enter collection name:" // TODO: Make and go to a collections screen.
        );

        if (collectionName != null && !collectionName.trim().isEmpty()) {
            ArrayList<BoardGame> games = dashboard.onCollectionSelected(collectionName, allDatabaseGames);
            titleLabel.setText(dashboard.getActiveCollectionName());
            loadGames(games);
        }

    }

}
