
package classes;

import ch.aplu.jgamegrid.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Fasogabe
 */
public class mainGUI extends JFrame implements ActionListener {
    // Initialize components
    public GUI gg;
    public SparePanel sp;
    
    // Initialize variables
    Piece[][] board;
    JButton button;
    Piece spare;
    int[] insertLoc;
    String player1;
    String player2;
    Boolean vsCP;
    
    
    
    public mainGUI(Piece[][] board, Piece spare) {
        
        super("Labyrinth");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        setContentPane(new JLabel(new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/src/triangles/stone_background.png")));
        
        setLayout(new GridBagLayout()); // set layout manager
        
        this.board = board;     // set initial board
        this.spare = spare;     // set initial spare
        
        getUserInfo();          // initiate user prompt
        initComponents();       // initiate components
    }
    
    private void initComponents() {
        
        // Create layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Create buttons - load new triangle every 3 buttons
        ImageIcon tri = new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/src/triangles/down_triangle.png");
        // Button 1
        button = new JButton(tri); 
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("1");
        gbc.weightx = 0.5;
        gbc.gridx = 1;
        gbc.gridy = 0;
        getContentPane().add(button, gbc);
        
        // Button 3
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("3");
        gbc.weightx = 0.5;
        gbc.gridx = 2;
        gbc.gridy = 0;
        getContentPane().add(button, gbc);
        
        // Button 5
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("5");
        gbc.weightx = 0.5;
        gbc.gridx = 3;
        gbc.gridy = 0;
        getContentPane().add(button, gbc);
        
        // SparePanel
        sp = new SparePanel(spare);  // Create sparepanel object
        gbc.gridheight = 3;
        gbc.gridx = 5;
        gbc.gridy = 0;
        getContentPane().add(sp, gbc);
        
        tri = new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/src/triangles/right_triangle.png");
        // Button 7
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("7");
        gbc.gridheight = 1;
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 1;
        getContentPane().add(button, gbc);
        
        // Button 9
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("9");
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 2;
        getContentPane().add(button, gbc);
        
        // Button 11
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("11");
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 3;
        getContentPane().add(button, gbc);
        
        // GameGrid panel
        gg = new GUI();         // Create gui object (the board)
        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        gbc.gridx = 1;
        gbc.gridy = 1;
        getContentPane().add(gg, gbc);
        
        tri = new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/src/triangles/left_triangle.png");
        // Button 8
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("8");
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 4;
        gbc.gridy = 1;
        getContentPane().add(button, gbc);
        
        // Button 10
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("10");
        gbc.gridx = 4;
        gbc.gridy = 2;
        getContentPane().add(button, gbc);
        
        // Button 12
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("12");
        gbc.gridx = 4;
        gbc.gridy = 3;
        getContentPane().add(button, gbc);
        
        tri = new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/src/triangles/up_triangle.png");
        // Button 2
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("2");
        gbc.gridx = 1;
        gbc.gridy = 4;
        getContentPane().add(button, gbc);
        
        // Button 4
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("4");
        gbc.gridx = 2;
        gbc.gridy = 4;
        getContentPane().add(button, gbc);
        
        // Button 6
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("6");
        gbc.gridx = 3;
        gbc.gridy = 4;
        getContentPane().add(button, gbc);
        
        // Pack components
        pack();
        
        // Display initial board setup
        gg.displayBoard(board);
        gg.doRun();

    }
    
    /**
     * updateBoard method sets local variable board and calls displayBoard method
     * @param board The updated board for each move
     */
    public void updateBoard(Piece[][] board) {
        this.board = board;
        gg.displayBoard(board);
        gg.doRun();
    }
    
    private void getUserInfo() {
//        String s = (String)JOptionPane.showInputDialogue(this,
//                                                        "Please enter your name:",
//                                                        "Customized Dialogue",
//                                                        JOptionPane.PLAIN_MESSAGE,
//                                                        null, "");
    }
    
    public boolean whoToPlay() {
        //maybe radio buttons here?

        //if user chooses to play a cp return true
        return true;
    }
    
    public String getPlayer1Name() {
        //text box input
        return "ram";
    }
    public String getPlayer2Name() {
        //text box input
        return "sean";
    }
    
    public int[] convertToGuiLoc(int[] loc){
        System.out.println(" Location " + Arrays.toString(loc));
        int x = 6-loc[1];
        int y = loc[0];
        
        loc[0]=y;
        loc[1]=x;
        
        System.out.println("converted to " + x +","+ y);
        return loc;
    }
    
    public int[] getInsertLocation() {
        System.out.println("Where to insert piece?");
        while (insertLoc[0] < 0){ /* Wait for button pressed */ }
        
        return convertToGuiLoc(insertLoc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String Action = e.getActionCommand ();
        insertLoc = new int[2];
        switch (Action) {
            case "1":
                insertLoc[0] = 1;
                insertLoc[1] = 0;
            case "2":
                insertLoc[0] = 1;
                insertLoc[1] = 6;
            case "3":
                insertLoc[0] = 3;
                insertLoc[1] = 0;
            case "4":
                insertLoc[0] = 3;
                insertLoc[1] = 6;
            case "5":
                insertLoc[0] = 5;
                insertLoc[1] = 0;
            case "6":
                insertLoc[0] = 5;
                insertLoc[1] = 6;
            case "7":
                insertLoc[0] = 0;
                insertLoc[1] = 1;
            case "8":
                insertLoc[0] = 6;
                insertLoc[1] = 1;
            case "9":
                insertLoc[0] = 0;
                insertLoc[1] = 3;
            case "10":
                insertLoc[0] = 6;
                insertLoc[1] = 3;
            case "11":
                insertLoc[0] = 0;
                insertLoc[1] = 5;
            case "12":
                insertLoc[0] = 6;
                insertLoc[1] = 5;
            default:
                insertLoc[0] = -1;
                insertLoc[1] = -1;
        }
        
    }
    

    
}
