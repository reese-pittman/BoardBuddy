package com.boardbuddy.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class LoginPanel extends JFrame   {

    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JTextField userTxt;
    private JLabel passwordLabel;
    private JPasswordField passTxt;
    public  JButton loginButton;
    private JLabel failMessage;

    public LoginPanel() {

        // creates title label
        titleLabel = new JLabel("Board Buddies", SwingConstants.CENTER);

        // creates a panel for username and passwords along with the lables and text fields 
        JPanel fieldPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        usernameLabel = new JLabel("Username");
        userTxt = new JTextField(20);
        passwordLabel = new JLabel("Password");
        passTxt = new JPasswordField(20);

        // adds the username and passwords labels and text fields to the panel
        fieldPanel.add(usernameLabel);
        fieldPanel.add(userTxt);
        fieldPanel.add(passwordLabel);
        fieldPanel.add(passTxt);

        // makes a new panel for the button and fail message if it appears 
        JPanel southPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        loginButton = new JButton("Login");
        failMessage = new JLabel("", SwingConstants.CENTER);

        // adds the button and fail message 
        southPanel.add(loginButton);
        southPanel.add(failMessage);

        // combines all of the other panals into one large panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(fieldPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // frame settings
        add(mainPanel);
        pack();                             // 1. sizes frame to fit components
        setResizable(false);     // 2. locks the size 
        setLocationRelativeTo(null);    // 3. centers on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


        // adds logic to the login button
    /**
     * @param action and object from the Action Listner class to communicate with the backend
     */
    public void setLoginAction(ActionListener action) {
        loginButton.addActionListener(action);
    }
    
    /**
     * @return what the user input in the username text box
     */
    public String getUsername(){
        return userTxt.getText();
    }
    
    /**
     * @return what the user enter in the password box
     */
    public String getPassword(){
        return new String(passTxt.getPassword());
    }
    /**
     * @param message a string that will display based on what you make the parameter 
     */
    public void setFailMessage(String message){
        failMessage.setText(message);
    }

}