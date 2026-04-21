package com.boardbuddy.service;

import com.boardbuddy.model.BoardGame;
import com.boardbuddy.model.Collection;
import com.boardbuddy.model.Review;
import com.boardbuddy.model.User;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class InputXml {

    /**
     * Parses the input file by calling a handler depending on what the file contains.
     * This is a collection class, you can just call parse for any file. If importing a list of games it will return a collection,
     * else returns null.
     * Reviews and Users will return null, but make their own internal arraylist of objects respectively.
     * 
     * @param fileIn
     * @param collectionName
     * @param userID
     */
    @SuppressWarnings("UseSpecificCatch")
    public static Collection parse(String fileIn, String collectionName, int userID) {
        try {
            File file = new File(fileIn);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            String root = doc.getDocumentElement().getTagName();

            /**
             * Check what the xml file contains.
             */
            switch (root) {
                case "items" -> {
                    return handleGames(doc, collectionName, userID);
                }
                case "reviews" -> {
                    handleReviews(doc);
                }
                case "users" -> {
                    handleUsers(doc);
                }
                default -> System.out.println("Unknown element: <" + root + ">. Nothing was imported.");
            }
            
        } catch (Exception e) {
            System.err.println("Fialed to parse XML file: " + e.getMessage());
            // e.printStackTrace(); // used for testing
        }
        return null;
    }

    /**
     * Fucnction to import the master game list.
     * Make a new boardgame object for each game instance, 
     * then adds the object to a collection, in this case being the mastergamelist.
     * 
     * @param doc
     * @param collectionName
     * @param userID
     */
    private static Collection handleGames(Document doc, String collectionName, int userID) {
        
        Collection collection = new Collection(collectionName, userID);
 
        NodeList itemNodes = doc.getElementsByTagName("item");
        int count = 0;
 
        for (int i = 0; i < itemNodes.getLength(); i++) {
            Element el = (Element) itemNodes.item(i);
 
            int id = Integer.parseInt(el.getAttribute("id"));
            String name = getValueAttribute(el, "name");
            int minPlayers = parseValueAttribute(el, "minplayers");
            int maxPlayers = parseValueAttribute(el, "maxplayers");
            int playTime = parseValueAttribute(el, "playingtime");
            int year = parseValueAttribute(el, "yearpublished");
            String image = getTextContent(el, "image");
            String thumbnail = getTextContent(el, "thumbnail");
 
            BoardGame game = new BoardGame(id, name, minPlayers, maxPlayers, playTime, year, image, thumbnail);
            collection.addGame(game);
            count++;
        }
 
        System.out.println("Imported " + count + " game(s) into collection \"" + collectionName + "\".");

        return collection;
    }

    /**
     * Function to handle review imports.
     * Just reads the file and makes a new review object for each instance.
     * 
     * @param doc
     */
    private static void handleReviews(Document doc) {
        NodeList reviewNodes = doc.getElementsByTagName("review");
        int count = 0;
 
        for (int i = 0; i < reviewNodes.getLength(); i++) {
            Element el = (Element) reviewNodes.item(i);
 
            String title = getTextContent(el, "title");
            String description = getTextContent(el, "description");
            int rating = parseTextContent(el, "rating");
            int userID = parseTextContent(el, "userID");
            int gameID = Integer.parseInt(getTextContent(el, "gameID"));
 
            // New review object and adding that to the arraylist of reviews
            Review review = new Review(title, description, rating, userID, gameID);
            Review.addReview(review);
            count++;
        }
 
        System.out.println("Imported " + count + " review(s).");
    }

    /**
     * Function to handle users import,
     * will also handle importting collections becuase collections must be tied to users.
     * 
     * @param doc
     */
    private static void handleUsers(Document doc) {
        NodeList userNodes = doc.getElementsByTagName("user");
        int count = 0;
 
        for (int i = 0; i < userNodes.getLength(); i++) {
            Element el = (Element) userNodes.item(i);
 
            String username = getTextContent(el, "username");
            String password = getTextContent(el, "password");

            // TODO: hash password before storing in production (this is a maybe, for now it is just a string)

 
            @SuppressWarnings("unused")
            User newUser = new User(username, password, i);

            // TODO: Read collections from file here maybe?
            String collectionName = getTextContent(el, "collectionName");
            Collection newCollection = new Collection(collectionName, i);
            // TODO: Read Game list from file
            // TODO: Add each game from file to collection
            newUser.addGameCollection(newCollection);
            

            // User.addUser(newUser); // This is done in the constructor now 
            count++;
        }
 
        System.out.println("Imported " + count + " user(s).");
    }

    // Getters
    /**
     * 
     * @param parent
     * @param tag
     * @return
     */
    private static String getTextContent(Element parent, String tag) {
        NodeList nodes = parent.getElementsByTagName(tag);
        if (nodes.getLength() == 0) return "";
        return nodes.item(0).getTextContent().trim();
    }
    /**
     * 
     * @param parent
     * @param tag
     * @return
     */
    private static String getValueAttribute(Element parent, String tag) {
        NodeList nodes = parent.getElementsByTagName(tag);
        if (nodes.getLength() == 0) return "";
        return ((Element) nodes.item(0)).getAttribute("value").trim();
    }
    /**
     * 
     * @param parent
     * @param tag
     * @return
     */
        private static int parseValueAttribute(Element parent, String tag) {
        String value = getValueAttribute(parent, tag);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.err.println("Could not parse value attribute for <" + tag + ">: \"" + value + "\". Defaulting to 0.");
            return 0;
        }
    }
    /**
     * 
     * @param parent
     * @param tag
     * @return
     */
    private static int parseTextContent(Element parent, String tag) {
        String value = getTextContent(parent, tag);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.err.println("Could not parse text content for <" + tag + ">: \"" + value + "\". Defaulting to 0.");
            return 0;
        }
    }

}
