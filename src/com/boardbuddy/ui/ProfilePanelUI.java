package com.boardbuddy.ui;


import com.boardbuddy.model.User;
import com.boardbuddy.service.LoginBackend;
import com.boardbuddy.service.ProfilePanelBackend;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class ProfilePanelUI extends JFrame {
    private User currentUser;
    private JButton collectionButton;
    private JButton dashboardButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel profileLabel;
    private JButton passwordButton;
    private JButton usernameButton;
    private JButton logoutButton;
    private Font font;
    private ProfilePanelBackend backend = new ProfilePanelBackend();


    public ProfilePanelUI(User currentUser){
        this.currentUser = currentUser;
       
        setTitle("My Profile");
        setSize(900,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        insideComponents();
    }


    public void insideComponents(){
        font = new Font("Arial", Font.BOLD, 40);
        setLayout(new BorderLayout());


//------------------NORTH Bar---------------------------------------------//
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));


        collectionButton = new JButton("Collections");
        dashboardButton = new JButton("Dashboard");
        logoutButton = new JButton("Logout");

        buttonPanel.add(collectionButton, BorderLayout.NORTH);
        buttonPanel.add(dashboardButton);
        buttonPanel.add(logoutButton);


        topPanel.add(buttonPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);


//-------------------CENTER Bar----------------------------------------//
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));


        usernameLabel = new JLabel("Current Username: " + currentUser.getUsername());
        usernameButton = new JButton("Change UserName");
        passwordLabel = new JLabel("Current Password: " + currentUser.maskPassword(currentUser.getPasswordHash().length()));
        passwordButton = new JButton("Change Password");
        profileLabel = new JLabel("My Profile");


        usernameLabel.setFont(font);
        passwordLabel.setFont(font);
        profileLabel.setFont(font);


//---------------Row 1: Profile label (centered)--------------------------//
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        profilePanel.add(profileLabel);


//-----------------Row 2: Username label----------------------------------//
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        usernamePanel.add(usernameLabel);


//-----------------Row 3: Username button--------------------------------//
        JPanel usernamePanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        usernamePanel2.add(usernameButton);


//------------------Row 4: Password label + button------------------------//
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordPanel.add(passwordLabel);


//-----------------Row 5: Password Button--------------------------------//
        JPanel passwordPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordPanel2.add(passwordButton);


//---------------------Combine all panels---------------------------------//
        centerPanel.add(profilePanel);
        centerPanel.add(usernamePanel);
        centerPanel.add(usernamePanel2);
        centerPanel.add(passwordPanel);
        centerPanel.add(passwordPanel2);
        add(centerPanel, BorderLayout.CENTER);
//--------------------Event Handlers---------------------------------------//
    collectionButton.addActionListener(e -> {
        backend.openCollections(currentUser.getUsersCollections(), currentUser, this);
    });
   
    dashboardButton.addActionListener(e -> {
        backend.openDash(currentUser,this);
    });

    usernameButton.addActionListener(e -> {
        String newUsername = JOptionPane.showInputDialog(this, "Enter new Username");
        if (newUsername != null && !newUsername.isBlank() && !currentUser.getUsername().equals(newUsername)){
                currentUser.setUsername(newUsername);
                usernameLabel.setText("Current Username: " + newUsername); }
        else{
                JOptionPane.showMessageDialog(null, "Invalid Username", "Error Title", JOptionPane.ERROR_MESSAGE);
        }
    });

    passwordButton.addActionListener(e -> {
        String newPassword = backend.showPasswordDialog();
        if (newPassword != null && !newPassword.isBlank() && !currentUser.getPasswordHash().equals(newPassword)) {
                currentUser.setPasswordHash(newPassword);
                passwordLabel.setText("Current Password: " + currentUser.maskPassword(newPassword.length()));

        }
        else{
                JOptionPane.showMessageDialog(null, "Invalid Password", "Error Title", JOptionPane.ERROR_MESSAGE);
        }
    });

    logoutButton.addActionListener(e -> {
        LoginBackend.LogOut(backend);
        setVisible(false);
        dispose();
    });


   
    }
     public static void main(String[] args) {
        User testU = new User("test", "pass", 101010);
          new ProfilePanelUI(testU).setVisible(true);
      }
}
