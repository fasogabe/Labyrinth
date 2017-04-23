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
import java.util.Scanner;

/**
 *
 * @author seanflynn
 */
public class GUI extends GameGrid implements GGMouseListener {
    
    public Actor user;
    public int[] usersGoal = new int[2];
    public int[] insertLoc = new int[2];
    boolean usersGoalEntered = false;
    boolean insertLocEntered = false;
    Scanner input;
    
    
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
        
        this.setActEnabled(false);
        this.input = new Scanner(System.in);

        addMouseListener(this, GGMouse.lDClick);
        super.setTitle("Labyrinth");
        usersGoal[0]=-1;
        usersGoal[1]=-1;
        insertLoc[0]=-1;
        insertLoc[1]=-1;
        show();
        
    }
//        public  GUI(Piece spare, Piece[][] board){
//        super();
//        setCellSize(62);
//        setNbHorzCells(7);
//        setNbVertCells(7);
//        setGridColor(java.awt.Color.white);
//        addMouseListener(this, GGMouse.lPress);
////        GUI.spareMap = createTileMap(1,1,62,62);
////        GUI.spareMap.setPosition(new Point(500,500));
////        
//        super.setTitle("Labyrinth");
//
//        show();
//        
//    }
   
//    void addTile(String type, int orientation, String treasure, int x, int y) {
//        String imageName = type+"-"+orientation+".png";
//        tileMap.setImage(imageName, 6-x, y); // haven't tested that coordinates work
        
        
//    }
    public void showGui(){
        show();
    }

//    void rotateLeft(Piece piece) { 
//        int x = piece.spot[0];
//        int y = piece.spot[1];
//        String imageName = piece.type+"-"+piece.orientation+".png";
//        
////        tileMap.setImage(imageName, new Location(x,y));
//       
//    }
//
//    void rotateRight( Piece piece) {
//        int x = piece.spot[0];
//        int y = piece.spot[1];
//        String imageName = piece.type+"-"+piece.orientation+".png";
//        
////        tileMap.setImage(imageName, new Location(x,y));
//        
//        
//    }

//    public String getPlayer1Name() {
//        //text box input
//        return "ram";
//    }
//
//    public boolean whoToPlay() {
//        //maybe radio buttons here?
//
//        //if user chooses to play a cp return true
//        return true;
//    }
//    //only called when whoToPlay() returns false
//
//    public String getPlayer2Name() {
//        //text box input
//        return "sean";
//    }
    //click on one of the orange triangles around the board to insert piece here
    //label them based on the closest board spot to the triangle

//    public int[] getInsertLocation() {
//       
//        int[] loc = new int[2];
//        while (!insertLocEntered ){ 
//            System.out.print("Enter an x value: ");
//            loc[0] = input.nextInt();
//            System.out.print("Enter a y value: ");
//            loc[1] = input.nextInt();
//            insertLocEntered=true;
//        }
//        return convertToGuiLoc(loc);
//    }
    //this method will get called again if the user chooses a path that doesn't work

    public int[] wantToMoveHere() {
        System.out.println("Where to move user to?");
        int[] loc = new int[2];
        while (!usersGoalEntered ){ 
            System.out.print("");
        }
        usersGoalEntered=false;
        return convertToGuiLoc(loc);
    }
    
    
    public int[] convertToGuiLoc(int[] loc){
        
        int x = 6-loc[1];
        int y = loc[0];
        int[] newLoc = {y,x};
        loc[0]=y;
        loc[1]=x;
        
        return newLoc;
    }

     public int[] convertToMathLoc(int[] loc){
        int x = loc[1]+6;  
        int y = loc[0];

        loc[0]=y;
        loc[1]=x;
        return loc;
    }


    void displayBoard(Piece[][] board) {
        for (int i=0;i<7;i++){
            for (int j=0;j<7;j++){
                Location loc = new Location(j,6-i);
                this.removeActorsAt(loc);
                Piece piece = board[j][i];
                String type = piece.type;
                int orientation = piece.orientation;    
                String treasure = piece.treasure;
//                String imageName = "sprites/"+type+"-"+orientation+".png";
                Piece thisPiece = new Piece(type, orientation, treasure, j, 6-i);
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
    
    public boolean mouseEvent(GGMouse mouse) {
        
    Location location = toLocationInGrid(mouse.getX(), mouse.getY());
    
    int[] loc = {location.x,location.y};
    
    usersGoal[0]=loc[0];
    usersGoal[1]=loc[1];
    
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
