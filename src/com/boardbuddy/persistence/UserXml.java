package com.boardbuddy.persistence;

import com.boardbuddy.model.User;
import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;


public class UserXml {
    private static final String FILE_PATH = "users.xml";

    /**
     * Method that either makes a new xml file for user data or appends to it based off user inputs
     * on registration screen
     * @param user User object parameter
     * @throws Exception
     */
    public void saveUser(User user) throws Exception{

        File xmlFile=new File(FILE_PATH);


        // If an xml file doesnt already exist (first time use for example)
        if(!xmlFile.exists()){

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.newDocument();

            // Users main dir
            Element root = doc.createElement("users");
            doc.appendChild(root);

            // User sub dir
            Element userElement = doc.createElement("user");
            root.appendChild(userElement);


            // Username sub sub dir under user
            Element username = doc.createElement("username");
            username.setTextContent(user.getUsername());
            userElement.appendChild(username);


            // Password sub sub dir under user
            Element password = doc.createElement("password");
            // TODO: hash password before storing in production
            password.setTextContent(user.getPasswordHash());
            userElement.appendChild(password);


            Transformer transformer = TransformerFactory.newInstance().newTransformer();  // Transformer writes the the xml
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            
            transformer.transform(source, result);
        }


        // If the file already exists
        else{
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            Element root = (Element) doc.getElementsByTagName("users").item(0);

            Element userElement = doc.createElement("user");
            root.appendChild(userElement);

            Element username = doc.createElement("username");
            username.setTextContent(user.getUsername());
            userElement.appendChild(username);

            Element password = doc.createElement("password");


            // TODO: hash password before storing in production


            password.setTextContent(user.getPasswordHash());
            userElement.appendChild(password);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);

            transformer.transform(source, result);

        }
        // end elseif
    }
    // end saveUser


    /**
     * Login will use this method to load user data from the xml
     * @param username
     * @return null if username wasn't found in the file
     * @throws Exception
     */
    public User loadUser(String username) throws Exception {
        File xmlFile = new File(FILE_PATH);

        if (!xmlFile.exists()) {
            return null;
        }

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(xmlFile);

        NodeList userList = doc.getElementsByTagName("user");

        for (int i = 0; i < userList.getLength(); i++) {
            Element userElement = (Element) userList.item(i);

            String savedUsername = userElement.getElementsByTagName("username").item(0).getTextContent();
            String savedPassword = userElement.getElementsByTagName("password").item(0).getTextContent();

            if (savedUsername.equals(username)) {
                return new User(savedUsername, savedPassword, 0);
            }
        }

        return null;
    }
    // end loadUser


    
}
