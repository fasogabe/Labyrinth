
package classes;

import ch.aplu.jgamegrid.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    ArrayList<JButton> buttons;
    Piece spare;
    String player1;
    String player2;
    Boolean vsCP;
    
    
    
    public mainGUI(Piece[][] board, Piece spare) {
        
        super("Labyrinth");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        
        this.board = board;
        this.spare = spare;
        
        getUserInfo();
        initComponents();
    }
    
    void initComponents() {
        
        gg = new GUI();         // Create gui object (the board)
        sp = new SparePanel(spare);  // Create sparepanel object
        
//        buttons = new ArrayList<JButton>();
//        String[] points = {"down", "right", "up", "left"};
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; i < 3; j++) {
//                JButton btn = new JButton();
//                buttons.add(btn);
//                btn.setIcon(new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/test/"+points[i]+"_triangle.png"));
//                btn.setBorder(null);
//                btn.setActionCommand(points[i]+j);
//                btn.addActionListener(this);
//                getContentPane().add(btn);   
//            }
//            
//            
//        }
        
        getContentPane().add(gg, BorderLayout.WEST);
        
        
        getContentPane().add(sp, BorderLayout.EAST);
        
        
        
        pack();
        
        gg.displayBoard(board);
        gg.doRun();

    }
    
    public void updateBoard(Piece[][] board) {
        this.board = board;
        gg.displayBoard(board);
        gg.doRun();
    }
    
    private void getUserInfo() {
        
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
    
//    public int[] getInsertLocation() {
//        System.out.println("Where to insert piece?");
//        while (insertLoc[0] < 0){ 
//              //wait until mouse is double clicked
//              System.out.print(""); // Hacked!
//        }
//        
//        return convertToGuiLoc(insertLoc);
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String Action = e.getActionCommand ();
        
        
    }
    

    
}
