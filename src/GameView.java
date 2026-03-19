
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;

public class GameView extends JPanel {
    // Colors
    private static final Color BG_DARK      = new Color(15,  17,  26);
    private static final Color BG_CARD      = new Color(24,  28,  44);
    private static final Color ACCENT       = new Color(255, 180,  50);   // amber
    private static final Color ACCENT2      = new Color(255, 100,  60);   // coral
    private static final Color TEXT_PRIMARY = new Color(240, 238, 230);
    private static final Color TEXT_MUTED   = new Color(140, 138, 155);
    private static final Color DIVIDER      = new Color(40,  45,  65);
    private static final Color BADGE_BG     = new Color(35,  40,  60);

    // Fonts
    private static final Font FONT_TITLE    = new Font("Georgia", Font.BOLD,  28);
    private static final Font FONT_SUBTITLE = new Font("Georgia", Font.ITALIC,14);
    private static final Font FONT_LABEL    = new Font("Monospaced", Font.PLAIN, 11);
    private static final Font FONT_VALUE    = new Font("SansSerif", Font.BOLD,  15);
    private static final Font FONT_BADGE    = new Font("Monospaced", Font.BOLD,  12);
    private static final Font FONT_ID       = new Font("Monospaced", Font.PLAIN, 10);

    // State
    private BoardGame game;

    // Sub components 
    private ImagePanel imagePanel;
    private JLabel titleLabel;
    private JLabel yearLabel;
    private JLabel idLabel;
    private StatBadge playersBadge;
    private StatBadge timeBadge;
    private JLabel noGameLabel;
    private JLabel contentLabel;

}
