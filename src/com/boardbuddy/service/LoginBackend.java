package com.boardbuddy.service;

import com.boardbuddy.model.User;
import com.boardbuddy.persistence.UserXml;
import com.boardbuddy.ui.DashPanel;
import com.boardbuddy.ui.LoginPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginBackend implements ActionListener {

    // Creates an instance of the loginPanel Class (GUI)
    private final LoginPanel GUI;

    // private final ArrayList<BoardGame> allDatabaseGames;

    // The constructor creates a composite relationship between the two classes. 
    // Final is used to 'force' this relationship
    public LoginBackend() { 
        this.GUI = new LoginPanel();

        this.GUI.setLoginAction(this);
        this.GUI.setVisible(true);
    }
    
    @Override
    // called automatically when button is clicked
    public void actionPerformed(ActionEvent e) { 
        checkCredentials(GUI.getUsername(), GUI.getPassword());
    }

    /**
     * 
     * @param username  The username that the user enetered and the system is checking 
     * @return         whether the username exist within the XML file 
     * @throws Exception  if there is an error with the XML file not related to wrong user input 
     */
    public boolean checkUsername(String username) throws Exception {
        User user = UserXml.loadUser(username); // returns null if username not found
        return user != null; // true if found, false if not
    }
    
    /**
     * 
     * @param user   the user object that matches with the same username
     * @param password  the password the user entered into the system
     * @return whether the correct password was returned 
     */
    public boolean checkPassword(User user, String password) {
        return user.getPasswordHash().equals(password); // compares against stored password
    }

    /**
     * this function closes out the login window and opens the dashboard
     * @param user the user object of the information that was entered 
     */
    public void openNewPage(User user) {
       GUI.setVisible(false);
        Dashboard dashboard = new Dashboard(user);
        DashPanel dashPanel = new DashPanel(dashboard); // loadGames is called automatically inside here
        dashPanel.setVisible(true);
    }

   
    /**   if the info is correct than the dashboard will open, else if
     *    there is an error with the XML a load data error will appear,
     *    if not the right credentials where put in a invalid username/ password message will appear
     * @param username the username entered 
     * @param password the password enetered 
     */
    public void checkCredentials(String username, String password) {
        try {
            User user = UserXml.loadUser(username); // load user from xml
            if (user != null && checkPassword(user, password)) {
                openNewPage(user);
            } else {
                GUI.setFailMessage("Invalid username or password");
            }
        } catch (Exception e) {
            GUI.setFailMessage("Error loading user data");
        }
    }



    // public void logOut(ActionEvent e) {
    //     if (e.getSource() == log){
    //         @SuppressWarnings("unused")
    //         LoginBackend f = new LoginBackend();
    //         setVisible(false);
    //     }
    // }

}