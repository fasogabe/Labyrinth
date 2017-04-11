/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import ch.aplu.jgamegrid.*;
import java.awt.*;
import java.util.Arrays;
import javax.swing.JPanel;

/**
 *
 * @author seanflynn
 */
public class GUI extends GameGrid implements GGMouseListener {
    
    public int[] usersGoal = new int[2];
    public int[] insertLoc = new int[2];
    
//    public GGTileMap tileMap;
    public static GGTileMap spareMap;
    //Piece spare = Labyrinth.labyrinth.getSpare();
    //--------------------------- GUI CALLS THESE ------------------------------------------------
    //GUI CLASS CAN ALSO CALL METHODS LISTED HERE:
    //REGULAR USE:
    //getWhoseTurn()
    //getBoard()
    //MUST USE player.method():	(getWhoseTurn() returns player)
    //getLocation()			-returns coordinates of players location
    //getColor()			-returns color of players token
    //getName()			-returns players name
    //getCurrentCard()		-returns the treasure they're looking for
    //getWhatFlipped()		-returns an ArrayList of all the cards the player has flipped over (found treasures for)
    //getCardsLeft()		-returns how many cards in the deck
    //MUST USE piece.method():   (getBoard() returns Piece[][], so let x = getBoard(), then x[i][j] is a piece)
    //getPaths()			-returns boolean[] of {north,east,south,west}, when true then you can move that direction
    //getTreasure()			-returns what treasure is on the piece, if null there is no treasure
    //getSpot()			-returns int[] of {x,y} coordinates of where the piece is
    //all the methods bellow must be changed by Sean
    //these methods will be called throughout the code above written by Alex and should provoke methods in Sean's GUI class

    
    public  GUI(){
        super();
        setCellSize(62);
        setNbHorzCells(7);
        setNbVertCells(7);
        setGridColor(java.awt.Color.white);

        addMouseListener(this, GGMouse.lPress | GGMouse.lRelease | GGMouse.lDClick);
        super.setTitle("Labyrinth");
        usersGoal[0]=-1;
        usersGoal[1]=-1;
        insertLoc[0]=-1;
        insertLoc[1]=-1;
        show();
        
    }
        public  GUI(Piece spare, Piece[][] board){
        super();
        setCellSize(62);
        setNbHorzCells(7);
        setNbVertCells(7);
        setGridColor(java.awt.Color.white);
        addMouseListener(this, GGMouse.lPress);
        GUI.spareMap = createTileMap(1,1,62,62);
        GUI.spareMap.setPosition(new Point(500,500));
        
        super.setTitle("Labyrinth");

        show();
        
    }
   
    void addTile(String type, int orientation, String treasure, int x, int y) {
        String imageName = type+"-"+orientation+".png";
//        tileMap.setImage(imageName, 6-x, y); // haven't tested that coordinates work
        
        
    }
    public void showGui(){
        show();
    }

    void rotateLeft(Piece piece) { 
        int x = piece.spot[0];
        int y = piece.spot[1];
        String imageName = piece.type+"-"+piece.orientation+".png";
        
//        tileMap.setImage(imageName, new Location(x,y));
       
    }

    void rotateRight( Piece piece) {
        int x = piece.spot[0];
        int y = piece.spot[1];
        String imageName = piece.type+"-"+piece.orientation+".png";
        
//        tileMap.setImage(imageName, new Location(x,y));
        
        
    }

    public String getPlayer1Name() {
        //text box input
        return "ram";
    }

    public boolean whoToPlay() {
        //maybe radio buttons here?

        //if user chooses to play a cp return true
        return true;
    }
    //only called when whoToPlay() returns false

    public String getPlayer2Name() {
        //text box input
        return "sean";
    }
    //click on one of the orange triangles around the board to insert piece here
    //label them based on the closest board spot to the triangle

    public int[] getInsertLocation() {
        System.out.println("Where to insert piece?");
        while (insertLoc[0] < 0){ 
              //wait until mouse is double clicked
              System.out.print(""); // Hacked!
        }
        
        return convertToGuiLoc(insertLoc);
    }
    //this method will get called again if the user chooses a path that doesn't work

    public int[] wantToMoveHere() {
        System.out.println("Drag/release actor to move it");
        while (usersGoal[0] < 0){ 
              //wait until mouse is double clicked
              System.out.print(""); // Hacked!
        }
        System.out.println("Returning "+ Arrays.toString(insertLoc));
        return insertLoc;
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

     public int[] convertToMathLoc(int[] loc){
        int x = loc[1]+6;  
        int y = loc[0];

        loc[0]=y;
        loc[1]=x;
        return loc;
    }
    //these methods need a button that calls them
    public void rotateSpareRight() {
//        spare.rotateRight();
    }

    public void rotateSpareLeft() {
//        spare.rotateLeft();
    }


    void displayBoard(Piece[][] board) {
        for (int i=0;i<7;i++){
            for (int j=0;j<7;j++){
                Piece piece = board[j][i];
                int[] loc = {j,i};
                String type = piece.type;
                int orientation = piece.orientation;    
                String treasure = piece.treasure;
                String imageName = "sprites/"+type+"-"+orientation+".png";
                Piece thisPiece = new Piece(type, orientation, treasure, j, 6-i);
                this.addActor(thisPiece, new Location (j,6-i));
//                tileMap.setImage(imageName, j, 6-i );//because the kind people of Czech Republic made origin top left instead of bottom left
                
            }
        }
        show();
    }

    @Override
    
    public boolean mouseEvent(GGMouse mouse) {
        
    Location location = toLocationInGrid(mouse.getX(), mouse.getY());
    
    int[] loc = {location.x,location.y};
    
    usersGoal[0]=loc[0];
    usersGoal[1]=loc[1];
    
        switch (mouse.getEvent()) {
            case GGMouse.lPress:
//                user = getOneActorAt(location);
                break;
            case GGMouse.lRelease:
                insertLoc[0]=-1;
                insertLoc[1]=-1;
                usersGoal[0]=loc[0];
                usersGoal[1]=loc[1];
                break;
            case GGMouse.lDClick:
                usersGoal[0]=-1;
                usersGoal[1]=-1;
                insertLoc[0]=loc[0];
                insertLoc[1]=loc[1];
                break;
            default:
                break;
        }

    return false; 
        
    }
    
    
}
