import javax.swing.*;


public class ProfilePanel extends JPanel {
    public ProfilePanel(User user, Profile profile) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel("Name: " + profile.getDisplayName());
        JLabel userLabel = new JLabel("Username: " + user.getUsername());

        add(nameLabel);
        add(userLabel);
    }
}