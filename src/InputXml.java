import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 * Class to load and parse the given xml input files.
 * <p>
 * This class reads an xml document which contains boardgame data, 
 * and converts each entry into a boardgame object contained in a list.
 * </p>
 */
public class InputXml {
    /**
     * Loads boardgames from xml input file.
     * 
     * @param xmlFile the xml input file 
     * @return a list of board game objects
     * @throws Exception if there is an issue reading / using the input file
     */
    public List<BoardGame> loadGames(File xmlFile) throws Exception {
        List<BoardGame> games = new ArrayList<>();

        DocumentBuilder builder = DocumentBuilderFactory
            .newInstance()
            .newDocumentBuilder();
        
        Document input = builder.parse(xmlFile);
        NodeList items = input.getElementsByTagName("item");

        for (int i = 0; i < items.getLength(); i++) {
            Element item = (Element) items.item(i);

            BoardGame game = new BoardGame();
            
            // Set all values for game object
            game.setId(item.getAttribute("id"));

            game.setName(getText(item, "name"));
            game.setMin(getInt(item, "minplayers"));
            game.setMax(getInt(item, "maxplayers"));
            game.setPlay(getInt(item, "playingtime"));
            game.setYear(getInt(item, "yearpublished"));
            game.setImage(getText(item, "image"));
            game.setThumbnail(getText(item, "thumbnail"));

            // add the game object to the arraylist
            games.add(game);
        }
        return games;
    }

    /**
     * Function to get the text of an element with a given tag name.
     * 
     * @param parent parent xml element
     * @param tag name of the child element
     * @return the text content of the element, or empty if there is no element
     */
    private String getText(Element parent, String tag) {
        Node node = parent.getElementsByTagName(tag).item(0);
        return node != null ? node.getTextContent() : "";
    }
    /**
     * Function to get an integer value of an element with a given tag name.
     * 
     * @param parent parent xml element
     * @param tag name of the child element
     * @return the integer value of the element, or 0 if there is no element
     */
    private int getInt(Element parent, String tag) {
        try {
            return Integer.parseInt(getText(parent, tag));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
