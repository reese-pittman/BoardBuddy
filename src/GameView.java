
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameView extends JPanel {

    private JLabel imageLabel;
    private JLabel nameLabel;
    private JLabel yearLabel;
    private JLabel playersLabel;
    private JLabel timeLabel;
    private JLabel idLabel;

    public GameView() {
        setLayout(new BorderLayout(12, 0));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
 
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setText("No Image");
 
        JPanel infoPanel = new JPanel(new GridLayout(5, 1, 0, 4));
        nameLabel    = new JLabel("Name: —");
        yearLabel    = new JLabel("Year: —");
        playersLabel = new JLabel("Players: —");
        timeLabel    = new JLabel("Play Time: —");
        idLabel      = new JLabel("ID: —");
 
        infoPanel.add(nameLabel);
        infoPanel.add(yearLabel);
        infoPanel.add(playersLabel);
        infoPanel.add(timeLabel);
        infoPanel.add(idLabel);
 
        add(imageLabel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);
    }

    public void setGame(BoardGame g) {
        if (g == null) {
            nameLabel.setText("Name: —");
            yearLabel.setText("Year: —");
            playersLabel.setText("Players: —");
            timeLabel.setText("Play Time: —");
            idLabel.setText("ID: —");
            imageLabel.setIcon(null);
            imageLabel.setText("No Image");
            return;
        }
 
        nameLabel.setText("Name: " + (g.getName() != null ? g.getName() : "—"));
        yearLabel.setText("Year: " + (g.getYear() > 0 ? g.getYear() : "—"));
 
        String players = (g.getMinPlayers() == g.getMaxPlayers())
                ? String.valueOf(g.getMinPlayers())
                : g.getMinPlayers() + " – " + g.getMaxPlayers();
        playersLabel.setText("Players: " + players);
        timeLabel.setText("Play Time: " + (g.getPlayTime() > 0 ? g.getPlayTime() + " min" : "—"));
        idLabel.setText("ID: " + (g.getId() != null ? g.getId() : "—"));
 
        imageLabel.setIcon(null);
        imageLabel.setText("Loading...");
        if (g.getImage() != null && !g.getImage().isBlank()) {
            new Thread(() -> {
                try {
                    BufferedImage img = ImageIO.read(new URL(g.getImage()));
                    if (img != null) {
                        Image scaled = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        SwingUtilities.invokeLater(() -> {
                            imageLabel.setIcon(new ImageIcon(scaled));
                            imageLabel.setText(null);
                        });
                    }
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> imageLabel.setText("No Image"));
                }
            }).start();
        } else {
            imageLabel.setText("No Image");
        }
    }
    
        public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // REMOVE
            BoardGame catan = new BoardGame("13", "Catan", 3, 4, 120, 1995,
                    "https://cf.geekdo-images.com/W3Bsga_uLP9kO91gZ7H8yw__original/img/M_3Vg1j2HlNgkDhwFbMsB9HxSUI=/0x0/filters:format(jpeg)/pic2419375.jpg", null);
 
            BoardGame pandemic = new BoardGame("30549", "Pandemic", 2, 4, 45, 2008,
                    "https://cf.geekdo-images.com/S3ybV1LAp-8wnvkITGJU-A__original/img/k0Lk5JKp5TtTsI0yAJHHfVFrgho=/0x0/filters:format(jpeg)/pic1534148.jpg", null);
 
            JFrame frame = new JFrame("Board Game Details");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
            GameView panel = new GameView();
            panel.setGame(catan);
 
            JPanel buttons = new JPanel();
            JButton b1 = new JButton("Catan");
            JButton b2 = new JButton("Pandemic");
            JButton b3 = new JButton("Clear");
            b1.addActionListener(e -> panel.setGame(catan));
            b2.addActionListener(e -> panel.setGame(pandemic));
            b3.addActionListener(e -> panel.setGame(null));
            buttons.add(b1); buttons.add(b2); buttons.add(b3);

            // REMOVE
 
            frame.add(panel, BorderLayout.CENTER);
            frame.add(buttons, BorderLayout.SOUTH);
            frame.setSize(380, 160);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
    
}
