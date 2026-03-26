


//this will handle the user data and information
public class Review {
    private String Title;
    private String Description; 
    private int rating; 

    public Review(String title, String description, int rating) {
        this.Title = title;
        this.Description = description;
        this.rating = rating;
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

}