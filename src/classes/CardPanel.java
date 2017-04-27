
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
    
    // FOR JAR FILE
	//String filePath = System.getProperty("user.dir");
    
    String filePath = new File("").getAbsolutePath() + "/src";
    
    String currentTreasure;
    JLabel currentCard;         // Label displaying user's current card
    ImageIcon treasureIcon;         // Current treasure icon
    
    public CardPanel(String t) {
        
        currentTreasure = t;
        
        setBackground(Color.DARK_GRAY); // set background
        setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        setPreferredSize(new Dimension(65,65));
        setLayout(new FlowLayout(FlowLayout.CENTER,18,10));
        
        setImage();
        
        
    }
    
    private void setImage() {
        
        
        
        // create treasure icon
        treasureIcon = new ImageIcon(filePath + "/sprites/" + currentTreasure.toLowerCase() + ".png");
        currentCard = new JLabel("", treasureIcon, JLabel.CENTER);
        
        add(currentCard);
        
    }
    
    public void setCurrentTreasure(String t) {
        removeIcon();
        // update current card
        currentTreasure = t;
        setImage();
    }
    public void removeIcon() {
        removeAll();
    }
    
}
