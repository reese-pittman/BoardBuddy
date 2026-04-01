

//Class to represent a review for a board game
public class Review {
    private String Title;
    private String Description; 
    private int rating;
    private int userID; // used to link the review to a user
    private String gameName; // used to link the review to a game

    public Review(String title, String description, int rating, int userID, String gameName) {
        this.Title = title;
        this.Description = description;
        this.rating = rating;
        this.userID = userID;
        this.gameName = gameName;
    }

    // Setters
    public void setTitle(String title) {
        this.Title = title;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    // Getters
    public String getTitle() {
        return Title;
    }
    public String getDescription() {
        return Description;
    }
    public int getRating() {
        return rating;
    }
    public int getUserID() {
        return userID;
    }
    public String getGameName() {
        return gameName;
    }
}