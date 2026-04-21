package com.boardbuddy.service;


import com.boardbuddy.ui.DashPanel;
import com.boardbuddy.model.User;
import com.boardbuddy.ui.CollectionPanel;
import com.boardbuddy.model.Collection;

import java.util.ArrayList;
import javax.swing.*;



public class ProfilePanelBackend extends JFrame {
   /**
    * opens up the dashboard and closes profile panel 
    * @param user   the current user who us log into the system 
    * @param currentWindow  the profile window which is currently opened 
    */
    public void openDash(User user, JFrame currentWindow) {
        currentWindow.setVisible(false);
        Dashboard dashboard = new Dashboard(user);
        DashPanel dashPanel = new DashPanel(dashboard);
        dashPanel.setVisible(true);
    }

   /**
    * opens the collections page and closes the profile page 
    * @param userCollections   the collections the current users have
    * @param user              the current user on the system 
    * @param currentWindow     the profile panel window which is currently open 
    */

    public void openCollections(ArrayList<Collection> userCollections, User user, JFrame currentWindow){
        currentWindow.setVisible(false);
        CollectionPanel collectionPanel = new CollectionPanel(userCollections, user);
        collectionPanel.setVisible(true);
    }
   
    /**
     * Displays a dialog box prompting the user to enter a new password. Also hides the users input
     * @return the password the user entered as a string 
     */
    public String showPasswordDialog() {
        JPasswordField passwordField = new JPasswordField();
        int result = JOptionPane.showConfirmDialog(this, passwordField,"Enter new password:",JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            return new String(passwordField.getPassword());
        }
        return null;
    }
}
