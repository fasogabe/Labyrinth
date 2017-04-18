
package classes;

import apple.laf.JRSUIUtils.Images;
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
    ImageIcon spareIcon;
    GridBagConstraints gbc;
    
    
    public SparePanel(Piece spare) {
        
        this.spare = spare;
//        
//        setLayout(new GridBagLayout());
//        gbc = new GridBagConstraints();
        
        initComponents();

    }
    
    
    
    void initComponents() {
        
        spareIcon = new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/src/sprites/"+spare.type+"-"+spare.orientation+".png");
        //spareIcon = new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/src/sprites/T-0.png");
        l = new JLabel("", spareIcon, JLabel.CENTER);
        
//        gbc.fill = GridBagConstraints.BOTH;
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.gridwidth = 3;
//        gbc.gridheight = 3;
        
        
        rLeft = new JButton("Rotate Left");
        rRight = new JButton("Rotate Right");
        
        rLeft.setActionCommand("rl");
        rRight.setActionCommand("rr");
        
        rLeft.addActionListener(this);
        rRight.addActionListener(this);
        
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 0;
//        gbc.gridy = 2;
        add(rLeft, BorderLayout.EAST);
        
        
        add(l, BorderLayout.CENTER);
        
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 2;
//        gbc.gridy = 2;
        add(rRight, BorderLayout.WEST);
        
        
        
    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        String Action = e.getActionCommand ();
        
        if (Action.equals("rl")) {
            spare.rotateLeft();
            spareIcon = new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/src/sprites/"+spare.type+"-"+spare.orientation+".png");
            l.setIcon(spareIcon);
//            gbc.fill = GridBagConstraints.HORIZONTAL;
//            gbc.gridx = 0;
//            gbc.gridy = 0;
//            gbc.gridwidth = 3;
            add(l, BorderLayout.CENTER);
        }
        if (Action.equals("rr")) {
            spare.rotateRight();
            spareIcon = new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/src/sprites/"+spare.type+"-"+spare.orientation+".png");
            l.setIcon(spareIcon);
//            gbc.fill = GridBagConstraints.HORIZONTAL;
//            gbc.gridx = 0;
//            gbc.gridy = 0;
//            gbc.gridwidth = 3;
            add(l, BorderLayout.CENTER);
        }
        
        
    }
    
//    protected ImageIcon updateImageIcon(String path) {
//        
//    
//        return new ImageIcon("sprites/"+spare.type+"-"+spare.orientation+".png");
//    
//    }



}
