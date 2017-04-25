
package classes;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author Fasogabe
 */
public class CardPanel extends JPanel {
    
    String currentTreasure;
    JLabel currentCard;         // Label displaying user's current card
    ImageIcon treasure;         // Current treasure icon
    JLabel treasuresFound;      // Label containing user's earned treasures
    
    public CardPanel(String curr) {
        
        currentTreasure = curr;
        
        setBackground(Color.DARK_GRAY); // set background
        setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        setPreferredSize(new Dimension(75,75));
        setLayout(new FlowLayout(FlowLayout.CENTER));
        
        setImage();
        
    }
    
    public void setImage() {
        
        // create treasure icon
        treasure = new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/src/sprites/" + currentTreasure.toLowerCase() + ".png");
        currentCard = new JLabel("", treasure, JLabel.CENTER);
        
        add(currentCard);
        
    }
    
    public void setCurrentTreasure(String t) {
        
        // update current card
        currentTreasure = t;
    }

}
