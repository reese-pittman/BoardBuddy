package com.boardbuddy.ui;

import com.boardbuddy.model.BoardGame;
import com.boardbuddy.model.Collection;
import com.boardbuddy.model.Review;
import com.boardbuddy.model.User;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameView extends JPanel {

    private static User currentUser;
    private BoardGame currentGame;
    private final int uid;

    private final JLabel imageLabel;
    private final JLabel nameLabel;
    private final JLabel yearLabel;
    private final JLabel playersLabel;
    private final JLabel timeLabel;
    private final JLabel idLabel;
    private final JPanel reviewListPanel;

    public static final int IMAGE_SIZE = 160;

    /**
     * Creates the gameview screen and opens the review screen.
     * 
     * @param uid User ID to keep track of who is looking at a game and leaves reviews
     * @param game Current GameName to tie it to reviews/collections
     */
    public GameView(User currentUser, int game) {

        GameView.currentUser = currentUser;
        uid = currentUser.getUID();

        setLayout(new BorderLayout(0, 12));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        // setSize(900, 600);
        

        // ── Top bar: back button ─────────────────────────────────────────────
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JButton backButton = new JButton("← Back to Dashboard");
        backButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(this).dispose();
        });
        topBar.add(backButton);

        // ── Game info row ────────────────────────────────────────────────────
        JPanel infoRow = new JPanel(new BorderLayout(12, 0));

        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setText("No Image");

        JPanel infoPanel = new JPanel(new GridLayout(5, 1, 0, 4));
        nameLabel    = new JLabel("Name: —");
        yearLabel    = new JLabel("Year: —");
        playersLabel = new JLabel("Players: —");
        timeLabel    = new JLabel("Play Time: —");
        idLabel      = new JLabel("ID: —");

        infoPanel.add(nameLabel);
        infoPanel.add(yearLabel);
        infoPanel.add(playersLabel);
        infoPanel.add(timeLabel);
        infoPanel.add(idLabel);

        infoRow.add(imageLabel, BorderLayout.WEST);
        infoRow.add(infoPanel, BorderLayout.CENTER);

        // ── Reviews section ──────────────────────────────────────────────────
        JPanel reviewsSection = new JPanel(new BorderLayout(0, 8));
        reviewsSection.setBorder(BorderFactory.createTitledBorder("Reviews"));

        reviewListPanel = new JPanel();
        reviewListPanel.setLayout(new BoxLayout(reviewListPanel, BoxLayout.Y_AXIS));
        reviewListPanel.add(new JLabel("No reviews yet."));

        JScrollPane scrollPane = new JScrollPane(reviewListPanel);
        scrollPane.setPreferredSize(new Dimension(0, 160));
        scrollPane.setBorder(null);

        JButton leaveReviewButton = new JButton("Leave a Review");
        leaveReviewButton.addActionListener(e -> showReviewDialog(uid, game));

        // Add To collection
        JButton addToCollection = new JButton("Add game to collection!");
        addToCollection.addActionListener(e -> addCollectionDialog(addToCollection));

        // Remove from collection
        JButton removeFromCollection = new JButton("Remove game from collection!");
        removeFromCollection.addActionListener(e -> removeCollectionDialog(removeFromCollection));

        // All bottom buttons
        JPanel bottomButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottomButtons.add(leaveReviewButton);
        bottomButtons.add(addToCollection);
        bottomButtons.add(removeFromCollection);

        reviewsSection.add(scrollPane, BorderLayout.CENTER);
        reviewsSection.add(bottomButtons, BorderLayout.SOUTH);

        // ── Assemble ─────────────────────────────────────────────────────────
        add(topBar, BorderLayout.NORTH);
        add(infoRow, BorderLayout.CENTER);
        add(reviewsSection, BorderLayout.SOUTH);
    }

    /**
     * Show the options to add current game to a collection. 
     * List all user's collections or add to a new collection.
     * 
     * @param anchor button to anchor dialog back to
     */
    private void addCollectionDialog(JButton anchor) {
        if (currentGame == null) return;

        java.util.ArrayList<Collection> collections = currentUser.getUsersCollections();

        JPopupMenu menu = new JPopupMenu();

        if (collections.isEmpty()) {
            JMenuItem empty = new JMenuItem("No collectoins yet.");
            empty.setEnabled(false);
            menu.add(empty);
        } else {
            for (Collection collection : collections) {
                if (collection == null) continue;
                JMenuItem item = new JMenuItem(collection.getCollectionName());
                item.addActionListener(e -> {
                    boolean success = collection.addGame(currentGame);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "\"" + currentGame.getName() + "\" added to \"" 
                        + collection.getCollectionName() + "\".", "Added to Collection", JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(this,
                        "\"" + currentGame.getName() + "\" was not able to be added to \"" + collection.getCollectionName() + "\".",
                        "Error adding game to collection.",
                        JOptionPane.ERROR_MESSAGE
                        );
                    }                    
                });
                menu.add(item);
            }
            menu.addSeparator();
        }

        // Add to a new collectoin option
        JMenuItem newItem = new JMenuItem("+ New Collection");
        newItem.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(
                this,
                "Enter a name for a new collection:",
                "New Collection",
                JOptionPane.PLAIN_MESSAGE
            );


            // Check if the Collection already exists
            for (Collection collection : collections) {
                if (collection == null) {
                    continue;
                }
                if (collection.getCollectionName().equals(name)) {
                    System.err.println("Is existing!");
                    JOptionPane.showMessageDialog(this,
                    "Collection \"" + name.trim() + "\" already exists!",
                    "Could not create Collection.",
                    JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
            } // Short circuit it making a new collection by returning.

            if (name != null && !name.trim().isEmpty()) {
                Collection newCollection = new Collection(name.trim(), uid);
                currentUser.addGameCollection(newCollection);
                newCollection.addGame(currentGame);
                JOptionPane.showMessageDialog(
                    this,
                    "Collection \"" + name.trim() + "\" created and \"" + currentGame.getName()
                    + "\" added.",
                    "Collection Created",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        menu.add(newItem);

        menu.show(anchor, 0, anchor.getHeight());
    }

    /**
     * Lets user remove a game from a collection.
     * 
     * @param anchor button to anchor dialog back to
     */
    private void removeCollectionDialog(JButton anchor) {
        if (currentGame == null) return;

        java.util.ArrayList<Collection> collections = currentUser.getUsersCollections();

        JPopupMenu menu = new JPopupMenu();

        if (collections.isEmpty()) {
            JMenuItem empty = new JMenuItem("No collectoins yet.");
            empty.setEnabled(false);
            menu.add(empty);
        } else {
            for (Collection collection : collections) {
                if (collection == null) continue;
                JMenuItem item = new JMenuItem(collection.getCollectionName());
                item.addActionListener(e -> {
                    boolean success = collection.removeGame(currentGame);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "\"" + currentGame.getName() + "\" removed from \"" 
                        + collection.getCollectionName() + "\".", "Removed from Collection", JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(this,
                        "\"" + currentGame.getName() + "\" was not able to be removed from \"" + collection.getCollectionName() + "\".",
                        "Error removing game from collection.",
                        JOptionPane.ERROR_MESSAGE
                        );
                    }                    
                });
                menu.add(item);
            }
            menu.addSeparator();
        }

        menu.show(anchor, 0, anchor.getHeight());

    }

    /**
     * Show the review box and passes in the current user and selected game.
     * 
     * @param uid
     * @param game
     */
    private void showReviewDialog(int uid, int game) {
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Leave a Review", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout(8, 8));
        dialog.getRootPane().setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel fields = new JPanel(new GridLayout(3, 2, 8, 8));

        // Title
        fields.add(new JLabel("Title:"));
        JTextField titleField = new JTextField();
        titleField.setPreferredSize(new Dimension(200, 25));
        fields.add(titleField);

        // Description
        fields.add(new JLabel("Description:"));
        JTextArea descriptionField = new JTextArea(4, 20);
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);
        fields.add(descriptionField);

        // Ratings
        fields.add(new JLabel("Rating (1-5):"));
        JSpinner ratingSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 5, 1));
        ratingSpinner.setPreferredSize(new Dimension(200, 25));
        fields.add(ratingSpinner);

        // Bottom buttons
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton cancel = new JButton("Cancel");
        JButton submit = new JButton("Submit");

        cancel.addActionListener(e -> dialog.dispose());
        submit.addActionListener(e -> {
        String title = titleField.getText().trim();
        String description = descriptionField.getText().trim();
        int rating = (int) ratingSpinner.getValue();

        if (!title.isEmpty() && !description.isEmpty()) {
            submitReview(title, description, rating, uid, game);
            loadReviews(game);
        }

        dialog.dispose();
        });

        buttonRow.add(cancel);
        buttonRow.add(submit);

        dialog.add(fields, BorderLayout.CENTER);
        dialog.add(buttonRow, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Passes inputed review values to the Review constructor.
     * 
     * @param title title of user's review
     * @param description description of user's review
     * @param rating rating left by user
     * @param userID userID of user leaving review
     * @param gameName gameID of current game
     */
    private static void submitReview(String title, String description, int rating, int userID, int gameID) {
        Review nReview = new Review(title, description, rating, userID, gameID);
        Review.addReview(nReview);
    }

    /**
     * Load the list of review matching the inputed gameName.
     * 
     * @param game Name of the game you want reviews from, case sensitive
     */
    private void loadReviews(int game) {
        // Call the Review class to get reviews of a specific game name
        List<Review> requestedList = Review.fetchReviews(game);

        reviewListPanel.removeAll();

        if (requestedList.isEmpty()) {
            reviewListPanel.add(new JLabel("No reviews."));
        } else {
            for (Review review : requestedList) {
                JLabel label = new JLabel("<html><b>" + review.getTitle() + "</b><br>" // title
                + review.getDescription() + "<br>Rating: " // description
                + review.getRating() + "/5</html>"); // rating out of 5
                label.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
                reviewListPanel.add(label);
            }
        }
        
        reviewListPanel.revalidate();
        reviewListPanel.repaint();
    }

    /**
     * Loads the image of the passed url
     * 
     * @param url url of desired image
     */
    private void loadImage(String url) {
        // Make sure url is valid
        if (url == null || url.isBlank()) {
            imageLabel.setIcon(null);
            imageLabel.setText("No Image");
            return;
        }

        try {
            // We have to connect to the web to make sure images load I think, this is confusing me
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) URI.create(url).toURL().openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.connect();

            BufferedImage raw = ImageIO.read(conn.getInputStream());
            if (raw == null) {
                System.err.println("ImageIO.read returned null for: " + url);
                imageLabel.setIcon(null);
                imageLabel.setText("No Image");
                return;
            }
            Image scaled = raw.getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
            imageLabel.setText(null);
        } catch (Exception ex) {
            System.err.println("Image load failed: " + ex);
            imageLabel.setIcon(null);
            imageLabel.setText("No Image");
        }
    }

    /**
     * Sets the current game that is clicked
     * 
     * @param game desired game to load
     */
    public void setGame(BoardGame game) {
        this.currentGame = game;
        if (game == null) {
            nameLabel.setText("Name: —");
            yearLabel.setText("Year: —");
            playersLabel.setText("Players: —");
            timeLabel.setText("Play Time: —");
            idLabel.setText("ID: —");
            imageLabel.setIcon(null);
            imageLabel.setText("No Image");
            return;
        }

        nameLabel.setText("Name: " + (game.getName() != null ? game.getName() : "—"));
        yearLabel.setText("Year: " + (game.getYear() > 0 ? game.getYear() : "—"));

        String players = (game.getMinPlayers() == game.getMaxPlayers()) 
        ? String.valueOf(game.getMinPlayers()) : game.getMinPlayers() + " – " + game.getMaxPlayers();

        playersLabel.setText("Players: " + players);
        timeLabel.setText("Play Time: " + (game.getPlayTime() > 0 ? game.getPlayTime() + " min" : "—"));
        idLabel.setText("ID: " + (game.getId() != 0 ? game.getId() : "—"));

        loadReviews(game.getId()); 

        imageLabel.setIcon(null);
        imageLabel.setText("Loading...");

        loadImage(game.getThumbnail());
    }

    /**
     * Main function to test gameview, manually creates catan game. 
     * Also manually makes a review and test user to work with.
     * DOES NOT WORK WITH IMAGES
     * 
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BoardGame catan = new BoardGame(13, "Catan", 3, 4, 120, 1995, 
            "https://cf.geekdo-images.com/W3Bsga_uLP9kO91gZ7H8yw__thumb/img/8a9HeqFydO7Uun_le9bXWPnidcA=/fit-in/200x150/filters:strip_icc()/pic2419375.jpg", null);
            submitReview("GREAT BAD GAME","Catan is a great strategy game but I have no friends to play with.",
                4, -1, 13);

            currentUser = new User("test", "pass", -1);
            int testUID = currentUser.getUID() ;int testGAME = catan.getId();

            Collection testCollection = new Collection("test", testUID);
            currentUser.addGameCollection(testCollection);

            JFrame frame = new JFrame("Board Game Details");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            GameView panel = new GameView(currentUser, testGAME);
            panel.setGame(catan);

            frame.add(panel);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}