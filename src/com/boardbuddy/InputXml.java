package com.boardbuddy;

import com.boardbuddy.model.Collection;
import com.boardbuddy.model.Review;
import com.boardbuddy.service.BoardGame;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class InputXml {

    /**
     * 
     * @param fileIn
     * @param collectionName
     * @param userID
     */
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
                case "items":
                    return handleGames(doc, collectionName, userID);
                case "reviews":
                    handleReviews(doc);
                    break;
                case "users":
                    handleUsers(doc);
                    break;
                default:
                    System.out.println("Unknown root element: <" + root + ">. No data imported.");
            }
            
        } catch (Exception e) {
            System.err.println("Fialed to parse XML file: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
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
 
            // Skip expansions and other non-boardgame types
            ///////// MAY CHANGE THIS
            /// 
            String type = el.getAttribute("type");
            if (!type.equals("boardgame")) {
                System.out.println("Skipping item of type \"" + type + "\".");
                continue;
            }
 
            String id = el.getAttribute("id");
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
     * 
     * @param doc
     */
    private static void handleReviews(Document doc) {
        NodeList reviewNodes = doc.getElementsByTagName("review");
        int count = 0;
 
        for (int i = 0; i < reviewNodes.getLength(); i++) {
            Element el = (Element) reviewNodes.item(i);
 
            String title       = getTextContent(el, "title");
            String description = getTextContent(el, "description");
            int rating         = parseTextContent(el, "rating");
            int userID         = parseTextContent(el, "userID");
            String gameName    = getTextContent(el, "gameName");
 
            Review review = new Review(title, description, rating, userID, gameName);
            Review.addReview(review);
            count++;
        }
 
        System.out.println("Imported " + count + " review(s).");
    }

    /**
     * 
     * @param doc
     */
    private static void handleUsers(Document doc) {
        // TODO: implement once the User class is created
        System.out.println("User import is not yet implemented.");
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
