
package classes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Fasogabe
 */
public class SparePanel extends JPanel implements ActionListener {
    
    Piece spare;
    JButton rLeft;
    JButton rRight;
    JLabel l;
    Image b;
    ImageIcon spareIcon;
    
    
    public SparePanel(Piece spare) {
        
        this.spare = spare;             // set local spare Piece object
        
        setBackground(Color.DARK_GRAY); // set background
        
        initComponents();               // initialize components

    }
    
    void initComponents() {
        
        // create spare icon label
        spareIcon = new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/src/sprites/"+spare.type+"-"+spare.orientation+".png");
        l = new JLabel("", spareIcon, JLabel.CENTER);
        
        // Create rotate buttons
        rLeft = new JButton("Rotate Left");
        rRight = new JButton("Rotate Right");
        // set action commands
        rLeft.setActionCommand("rl");
        rRight.setActionCommand("rr");
        // add action listeners
        rLeft.addActionListener(this);
        rRight.addActionListener(this);
        
        // add components to pane
        add(rLeft, BorderLayout.EAST);
        add(l, BorderLayout.CENTER);
        add(rRight, BorderLayout.WEST);    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        String Action = e.getActionCommand ();
        
        if (Action.equals("rl")) {  // rotate left pressed 
            spare.rotateLeft();
            spareIcon = new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/src/sprites/"+spare.type+"-"+spare.orientation+".png");
            l.setIcon(spareIcon);   // update spare icon
            add(l, BorderLayout.CENTER);
        }
        if (Action.equals("rr")) { // rotate right pressed
            spare.rotateRight();
            spareIcon = new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/src/sprites/"+spare.type+"-"+spare.orientation+".png");
            l.setIcon(spareIcon);   // update spare icon
            add(l, BorderLayout.CENTER);
        }
        
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(b, 0, 0, null);
    }
    




}
