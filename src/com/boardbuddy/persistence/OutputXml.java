package com.boardbuddy.persistence;

import com.boardbuddy.model.Review;
import com.boardbuddy.model.User;
import java.io.*;
import java.util.ArrayList;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public class OutputXml {
    private static final String REVIEW_PATH = "reviews.xml";
    private static final String USER_PATH = "users.xml";


    /**
     * Function to run all other save functions.
     */
    public static void saveAll() {
        saveReviews();
        saveUsers();
    }

    /**
     * Function to write reviews to an xml file.
     * 
     * This one had a lot of trial and error because of exception handling, I hate it.
     * 
     */
    public static void saveReviews() {

        try {
            File reviewFile = new File(REVIEW_PATH);
            
            ArrayList<Review> reviewList = Review.getReviews();
            
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.newDocument();
            
            // Reviews root dir
            Element root = doc.createElement("reviews");
            doc.appendChild(root);
            
            for (Review review : reviewList) {
                if (review == null) return;
                // Review sub dir
                Element reviewElement = doc.createElement("review");
                root.appendChild(reviewElement);
                
                // Review title sub dir under review
                Element title = doc.createElement("title");
                title.setTextContent(review.getTitle());
                reviewElement.appendChild(title);
                // Review description sub dir under review
                Element description = doc.createElement("description");
                description.setTextContent(review.getDescription());
                reviewElement.appendChild(description);
                // Review rating sub dir under review
                Element rating = doc.createElement("rating");
                rating.setTextContent(String.valueOf(review.getRating()));
                reviewElement.appendChild(rating);
                // Review userID sub dir under review
                Element userID = doc.createElement("userID");
                userID.setTextContent(String.valueOf(review.getUserID()));
                reviewElement.appendChild(userID);
                // Review gameID sub dir under review
                Element gameID = doc.createElement("gameID");
                gameID.setTextContent(String.valueOf(review.getGameID()));
                reviewElement.appendChild(gameID);
            }
            
            // Transformer to write the xml
            Transformer transformer = null;
            try {
                transformer = TransformerFactory.newInstance().newTransformer();
            } catch (TransformerConfigurationException ex) {
                System.getLogger(OutputXml.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            if (transformer == null) return;
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(reviewFile);
            
            try {
                transformer.transform(source, result);
            } catch (TransformerException ex) {
                System.getLogger(OutputXml.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        } catch (ParserConfigurationException ex) {
            System.getLogger(OutputXml.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    /**
     * Function to save the userList to an xml File
     */
    public static void saveUsers() {
        
        try {
            File userFile = new File(USER_PATH);
            
            ArrayList<User> userList = User.getUserList();
            
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.newDocument();
            
            // Users root dir
            Element root = doc.createElement("users");
            doc.appendChild(root);
            
            for (User user : userList) {
                if (user == null) return;
                // Review sub dir
                Element userElement = doc.createElement("user");
                root.appendChild(userElement);
                
                // Review username sub dir under review
                Element username = doc.createElement("username");
                username.setTextContent(user.getUsername());
                userElement.appendChild(username);
                // Review description sub dir under review
                Element password = doc.createElement("password");   // TODO: hash password before storing in production (this is a maybe, for now it is just a string)
                password.setTextContent(user.getPasswordHash());
                userElement.appendChild(password);
                // User userID sub dir under review
                Element UID = doc.createElement("UID");
                UID.setTextContent(String.valueOf(user.getUID()));
                userElement.appendChild(UID);
            }
            
            // Transformer to write the xml
            Transformer transformer = null;
            try {
                transformer = TransformerFactory.newInstance().newTransformer();
            } catch (TransformerConfigurationException ex) {
                System.getLogger(OutputXml.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            if (transformer == null) return;
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(userFile);
            
            try {
                transformer.transform(source, result);
            } catch (TransformerException ex) {
                System.getLogger(OutputXml.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        } catch (ParserConfigurationException ex) {
            System.getLogger(OutputXml.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
