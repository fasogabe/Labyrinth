
package classes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * SparePanel class is a JPanel which displays the current
 * spare piece and allows user to rotate the spare right or
 * left by using JButtons.
 * @author Fasogabe
 */
public class SparePanel extends JPanel implements ActionListener {
    
    Piece spare;
    JButton rLeft, rRight;
    JLabel l;
    ImageIcon spareIcon, rlIcon, rrIcon;
    BufferedImage spareImage = null, treasureImage = null;
    String filePath = new File("").getAbsolutePath() + "/src";
    /**
     * Constructor takes initial spare Piece as an argument
     * and instantiates all subcomponents
     * @param spare
     */
    public SparePanel(Piece spare) {
        
        this.spare = spare; // set local spare Piece object
        
        setBackground(Color.DARK_GRAY);                         // set background
        setBorder(BorderFactory.createLineBorder(Color.YELLOW));// set border
        setPreferredSize(new Dimension(250,100));               // set size
        setLayout(new FlowLayout(FlowLayout.CENTER, 25, 18));   // set layout
        
        initComponents();   // initialize components

    }
    
    public Piece getSpare() {
        return spare;
    }

    /**
     * setSpare method is used by main class
     * to reset the spare piece appropriately
     * after each insertion
     * @param spare The new spare Piece object
     */
    public void setSpare(Piece spare) {
        this.spare = spare;
        setSpareIcon();
    }
    
    private void setSpareIcon() {
        
        spareIcon = new ImageIcon(filePath + "/sprites/"+spare.type+"-"+spare.orientation+".png");
        l.setIcon(spareIcon);
        l.validate();   // update spare icon
        
    }
    
    private void initComponents() {
        
        // create spare icon label
        spareIcon = new ImageIcon(filePath + "/sprites/"+spare.type+"-"+spare.orientation+".png");
        l = new JLabel("", spareIcon, JLabel.CENTER);
        Border border = BorderFactory.createLineBorder(Color.YELLOW);
        l.setBorder(border);
        
        // Create rotate buttons
        rlIcon = new ImageIcon(filePath +"/images/rotate_left.png");
        rLeft = new JButton(rlIcon);
        rLeft.setBorder(null);
        rrIcon = new ImageIcon(filePath + "/images/rotate_right.png");
        rRight = new JButton(rrIcon);
        rRight.setBorder(null);
        // set action commands
        rLeft.setActionCommand("rl");
        rRight.setActionCommand("rr");
        // add action listeners
        rLeft.addActionListener(this);
        rRight.addActionListener(this);
        
        // add components to pane
        add(rLeft);
        add(l);
        add(rRight);    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        String Action = e.getActionCommand ();
        
        if (Action.equals("rl")) {  // rotate left pressed 
            spare.rotateLeft();
            setSpareIcon();
        }
        if (Action.equals("rr")) { // rotate right pressed
            spare.rotateRight();
            setSpareIcon();
        }
        
        
    }
    

    




}
