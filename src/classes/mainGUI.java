
package classes;

import ch.aplu.jgamegrid.*;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Fasogabe
 */
public class mainGUI extends JFrame {
    
    public GUI gg;
    
    public mainGUI(Piece[][] board) {
        
        super("Labyrinth");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponents(board);
    }
    
    void initComponents(Piece[][] board) {
        
        gg = new GUI();
        
        getContentPane().add(gg, BorderLayout.WEST);
        JTextField t = new JTextField("<----- dicks");
        getContentPane().add(t, BorderLayout.EAST);
        pack();
        gg.displayBoard(board);
        gg.doRun();
//        GameGrid gg = new GameGrid();
//        gg.setCellSize(40);
//        gg.setGridColor(Color.red);
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        getContentPane().add(gg, BorderLayout.WEST);
//        JTextField f = new JTextField("Hello, I am Nemo in a custom window");
//        JTextField h = new JTextField("SmokeDicks420");
//        getContentPane().add(f, BorderLayout.EAST);
//        getContentPane().add(h, BorderLayout.EAST);
//        pack();  // Must be called before actors are added!
//        Piece fish = new Piece("L", 0, null, 0,0);
//        gg.addActor(fish, new Location(2, 4));
//        gg.doRun();
    }
    
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new mainGUI().setVisible(true);
//            }
//        });
//    }
    
}
