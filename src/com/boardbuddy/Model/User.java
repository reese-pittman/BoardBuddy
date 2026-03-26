
import java.util.ArrayList;
import java.util.Collection;

//this will handle the user data and information
public class User {
    private String username;
    private String passwordHash; // may just be a password, but we may hash it
    private ArrayList<Review> reviews; // May be here or on games
    private ArrayList<Collection> gameCollections; // Users can have multiple collections of games

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.reviews = new ArrayList<>();
        this.gameCollections = new java.util.ArrayList<>();
    }

    // Getters
    public String getUsername()     { return username; }
    public String getPasswordHash() { return passwordHash; }

    //Setters
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //Just for creating a new array and then adding the new review to
    public void addReview(Review review) {
        reviews.add(review);
    }

    public void addGameCollection(Collection<Game> collection) {
        gameCollections.add(collection);
    }
}