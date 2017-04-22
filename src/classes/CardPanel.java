
package classes;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author Fasogabe
 */
public class CardPanel extends JPanel {
    
    Player currentPlayer;
    JLabel currentCard;         // Label displaying user's current card
    ImageIcon treasure;         // Current treasure icon
    JLabel treasuresFound;      // Label containing user's earned treasures
    
    public CardPanel(Player curr) {
        
        currentPlayer = curr;
        
        setBackground(Color.DARK_GRAY); // set background
        setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        setPreferredSize(new Dimension(100,100));
        setLayout(new FlowLayout(FlowLayout.CENTER));
        
        initComponents();
        
    }
    
    void initComponents() {
        
        treasure = new ImageIcon("<path to image of current treasure>");
        currentCard = new JLabel(currentPlayer.currentTreasure, treasure, JLabel.CENTER);
        
        add(currentCard);
        
    }
    
    public void setCard(String card) {
        
        // update current card
        
    }

}
