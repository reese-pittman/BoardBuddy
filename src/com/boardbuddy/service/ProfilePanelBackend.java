package com.boardbuddy.service;


import com.boardbuddy.ui.DashPanel;
import com.boardbuddy.model.User;
import com.boardbuddy.ui.CollectionPanel;
import com.boardbuddy.model.Collection;

import java.util.ArrayList;
import javax.swing.*;



public class ProfilePanelBackend extends JFrame {
   
    public void openDash(User user, JFrame currentWindow) {
        currentWindow.setVisible(false);
        Dashboard dashboard = new Dashboard(user);
        DashPanel dashPanel = new DashPanel(dashboard);
        dashPanel.setVisible(true);
    }


    public void openCollections(ArrayList<Collection> userCollections, User user, JFrame currentWindow){
        currentWindow.setVisible(false);
        CollectionPanel collectionPanel = new CollectionPanel(userCollections, user);
        collectionPanel.setVisible(true);
    }
   
    public String showPasswordDialog() {
        JPasswordField passwordField = new JPasswordField();
        int result = JOptionPane.showConfirmDialog(this, passwordField,"Enter new password:",JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            return new String(passwordField.getPassword());
        }
        return null;
    }
}
