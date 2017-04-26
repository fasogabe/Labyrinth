
package classes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 *
 * @author Fasogabe
 */
public class SparePanel extends JPanel implements ActionListener {
    
    Piece spare;
    JButton rLeft, rRight;
    JLabel l;
    ImageIcon spareIcon, rlIcon, rrIcon;
    String filePath = new File("").getAbsolutePath() + "/src";
    
    
    public SparePanel(Piece spare) {
        
        this.spare = spare;             // set local spare Piece object
        
        setBackground(Color.DARK_GRAY); // set background
        setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        setPreferredSize(new Dimension(250,100));
        setLayout(new FlowLayout(FlowLayout.CENTER, 25, 18));
        
        initComponents();               // initialize components

    }
    
    public Piece getSpare() {
        return spare;
    }
    public void setSpare(Piece spare) {
        this.spare = spare;
        setSpareIcon();
    }
    
    private void setSpareIcon() {
        spareIcon = new ImageIcon(filePath + "/sprites/"+spare.type+"-"+spare.orientation+".png");
        l.setIcon(spareIcon);   // update spare icon
        l.validate();
        
    }

    
    
    private void initComponents() {
        
        // create spare icon label
        spareIcon = new ImageIcon(filePath + "/sprites/"+spare.type+"-"+spare.orientation+".png");
        l = new JLabel("", spareIcon, JLabel.CENTER);
        Border border = BorderFactory.createLineBorder(Color.YELLOW);
        l.setBorder(border);
        
        // Create rotate buttons
        rlIcon = new ImageIcon(filePath + "/images/rotate_left.png");
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
