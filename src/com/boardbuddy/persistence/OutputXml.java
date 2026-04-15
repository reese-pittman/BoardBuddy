package com.boardbuddy.persistence;

import com.boardbuddy.model.Review;
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

    public static void saveUsers() {

    }
}
