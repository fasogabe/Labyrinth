
package classes;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Fasogabe
 */
public class CardPanel extends JPanel {
    
    String currentTreasure;
    JLabel currentCard;         // Label displaying user's current card
    ImageIcon treasureIcon;         // Current treasure icon
    
    public CardPanel(String t) {
        
        currentTreasure = t;
        
        setBackground(Color.DARK_GRAY); // set background
        setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        setPreferredSize(new Dimension(65,65));
        setLayout(new FlowLayout(FlowLayout.CENTER,18,18));
        
        setImage();
        
        
    }
    
    private void setImage() {
        
        removeAll();
        
        // create treasure icon
        treasureIcon = new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/src/sprites/" + currentTreasure.toLowerCase() + ".png");
        currentCard = new JLabel("", treasureIcon, JLabel.CENTER);
        
        add(currentCard);
        
    }
    
    public void setCurrentTreasure(String t) {
        // update current card
        currentTreasure = t;
        setImage();
    }

}
