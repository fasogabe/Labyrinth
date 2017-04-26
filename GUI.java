
package classes;

import ch.aplu.jgamegrid.*;
import java.awt.*;
import java.util.Arrays;
import javax.swing.JPanel;
import java.util.Scanner;

/**
 * GUI class that displays the layout of the board
 * Inserts pieces onto the GUI board
 * Also listens to double mouse clicks for desired player location
 * @author Sean Flynn
 */
public class GUI extends GameGrid implements GGMouseListener {
    
    //Variables for the GUI class 
    public Actor user;
    public int[] usersGoal = new int[2];
    public int[] insertLoc = new int[2];
    boolean usersGoalEntered = false;
    boolean insertLocEntered = false;
    Scanner input;
    
    //Tile map for Spare piece
    public static GGTileMap spareMap;
    
    //GUI class that has action lisenters and accepts user input
    public  GUI(){
        super();
        //Display tiles and grid color
        setCellSize(62);
        setNbHorzCells(7);
        setNbVertCells(7);
        setGridColor(java.awt.Color.white);
        //User input
        this.setActEnabled(false);
        this.input = new Scanner(System.in);
        //Where user wants to move and where they insert pieces
        addMouseListener(this, GGMouse.lDClick);
        super.setTitle("Labyrinth");
        usersGoal[0]=-1;
        usersGoal[1]=-1;
        insertLoc[0]=-1;
        insertLoc[1]=-1;
        show();
        
    }
    
    public void showGui(){
        show();
    }    
    
    //To get x location subtract 6 from the current location to convert to GUI
    public int[] convertToGuiLoc(int[] loc){
        
        int x = 6-loc[1];
        int y = loc[0];
        int[] newLoc = {y,x};
        loc[0]=y;
        loc[1]=x;
        
        return newLoc;
    }
    
    //Display the pieces on the board
    void displayBoard(Piece[][] board) {
        //Iterate through the board and place the pieces down on the GUI
        for (int i=0;i<7;i++){
            for (int j=0;j<7;j++){
                //Convert the location to GUI
                Location loc = new Location(j,6-i);
                this.removeActorsAt(loc);
                //Place pieces down
                Piece piece = board[j][i];
                String type = piece.type;
                //Get orientation of the pieces and place the treasures on them
                int orientation = piece.orientation;    
                String treasure = piece.treasure;
                Piece thisPiece = new Piece(type, orientation, treasure, j, 6-i);
                //Set the treasures down
                this.addActor(thisPiece, loc); 
                if (!treasure.equals("null")){
                    Treasure t = new Treasure(treasure);
                    this.addActor(t, loc);
                }

            }
        }
        show();
    }

    @Override
    //Clicking the mouse on the GUI events
    public boolean mouseEvent(GGMouse mouse) {
        //Get the Gui location 
        Location location = toLocationInGrid(mouse.getX(), mouse.getY());
    
        int[] loc = {location.x,location.y};
    
        usersGoal[0]=loc[0];
        usersGoal[1]=loc[1];
            //If the mouse is clicked, tell the GUI where the desired location is
            switch (mouse.getEvent()) {
                
                case GGMouse.lDClick:
                    System.out.println("Mouse double clicked");
                    insertLoc[0]=loc[0];
                    insertLoc[1]=loc[1];
                    usersGoal[0]=loc[0];
                    usersGoal[1]=loc[1];
                    usersGoalEntered=true;
                    break;


                default:
                    break;
            }

        return true; 
     
        }
}
