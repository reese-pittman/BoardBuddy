package com.boardbuddy.service;

import com.boardbuddy.ui.DashPanel;

import javax.swing.*;

import com.boardbuddy.model.User;


public class ProfilePanelBackend extends JFrame {
    

    public void openDash(User user, JFrame currentWindow) {
        currentWindow.setVisible(false); 
        Dashboard dashboard = new Dashboard(user);
        DashPanel dashPanel = new DashPanel(dashboard);
        dashPanel.setVisible(true);
    }

    public void openCollections(){
        // TODO: Make and go to a collections screen.
        System.out.println("Collection menu not made yet");
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
