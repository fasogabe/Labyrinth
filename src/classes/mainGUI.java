
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
    public CardPanel ca;
    public Labyrinth lab;
    
    // Initialize variables
    Piece[][] board;
    JButton button;
    Piece spare;
    int[] insertLoc;
    String player1;
    String player2;
    Player currentPlayer;
    Boolean vsCP;
    
    
    
    public mainGUI(Labyrinth lab, Piece[][] board, Piece spare) {
        
        super("Labyrinth");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(860, 550));
        setContentPane(new JLabel(new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/src/images/stone_background.png")));
        
        setLayout(new GridBagLayout()); // set layout manager
        
        this.lab = lab;         // set main class reference
        this.currentPlayer = lab.getWhoseTurn();
        
        this.board = board;     // set initial board setup
        this.spare = spare;     // set initial spare piece
        
        
        
        initComponents();       // initiate components
    }
    
    private void initComponents() {
        
        // Create layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Create buttons - load new triangle every 3 buttons
        ImageIcon tri = new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/src/images/down_triangle.png");
        // Button 1
        button = new JButton(tri); 
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("1");
        gbc.weightx = 0.5;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 40, 0, 0);
        getContentPane().add(button, gbc);
        
        
        // Button 3
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("3");
        gbc.weightx = 0.5;
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        getContentPane().add(button, gbc);
        
        
        // Button 5
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("5");
        gbc.weightx = 0.5;
        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 40);
        getContentPane().add(button, gbc);
        
        
        // SparePanel
        sp = new SparePanel(spare);  // Create sparepanel object
        gbc.gridheight = 4;
        gbc.gridx = 9;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        getContentPane().add(sp, gbc);
        
        // CardPanel
        ca = new CardPanel(currentPlayer);  // Create cardpanel object
        gbc.gridheight = 4;
        gbc.gridx = 9;
        gbc.gridy = 5;
        gbc.insets = new Insets(20, 20, 20, 20);
        getContentPane().add(ca, gbc);
        

        
        tri = new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/src/images/right_triangle.png");
        // Button 7
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("7");
        gbc.gridheight = 1;
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(40, 0, 0, 0);
        getContentPane().add(button, gbc);
        
        
        // Button 9
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("9");
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        getContentPane().add(button, gbc);
        
        
        // Button 11
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("11");
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 40, 0);
        getContentPane().add(button, gbc);
        
        
        // GameGrid panel
        gg = new GUI();         // Create gui object (the board)
        gbc.gridwidth = 7;
        gbc.gridheight = 7;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        getContentPane().add(gg, gbc);
        
        
        tri = new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/src/images/left_triangle.png");
        // Button 8
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("8");
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 8;
        gbc.gridy = 2;
        gbc.insets = new Insets(40, 0, 0, 0);
        getContentPane().add(button, gbc);
        
        
        // Button 10
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("10");
        gbc.gridx = 8;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        getContentPane().add(button, gbc);
        
        
        // Button 12
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("12");
        gbc.gridx = 8;
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 40, 0);
        getContentPane().add(button, gbc);
        
        
        tri = new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/src/images/up_triangle.png");
        // Button 2
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("2");
        gbc.gridx = 2;
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 40, 0, 0);
        getContentPane().add(button, gbc);
        
        
        // Button 4
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("4");
        gbc.gridx = 4;
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 0, 0, 0);
        getContentPane().add(button, gbc);
        
        
        // Button 6
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("6");
        gbc.gridx = 6;
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 0, 0, 40);
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
    
    
    public static boolean whoToPlay() {
        //maybe radio buttons here?
        String[] options = {"Computer", "Human"};
        int n = JOptionPane.showOptionDialog(null, "CHOOSE YOUR OPPONENT", "",
                                                JOptionPane.YES_NO_OPTION,
                                                JOptionPane.QUESTION_MESSAGE,
                                                null, options, options[0]);
        //if user chooses to play a cp return true
        return n == 0;
        
    }
    
    public static String getPlayer1Name() {
        //text box input
        String name = (String)JOptionPane.showInputDialog("Welcome to Labyrinth!\n\nEnter your name:");
        return name;
    }
    public static String getPlayer2Name() {
        //text box input
        String name = (String)JOptionPane.showInputDialog("Human #2, enter your name:");
        return name;
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
        if (insertLoc[0] < 0) {
            return insertLoc;
        } else {
            return convertToGuiLoc(insertLoc);
        }
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String Action = e.getActionCommand();
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
        }
        this.spare = sp.spare;
    }
    

    
}
