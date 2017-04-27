//Piece class
package classes;

import ch.aplu.jgamegrid.Actor;

/**
 * Piece class that maps the orientation of each piece to the picture associated
 * Defines glued pieces and may rotate each piece to the right or left
 * while also setting treasures on the pieces.
 * @author Sean Flynn
 */
public class Piece extends Actor{
    //Array of which directions on the piece has an opening in the order {N,E,S,W}
    boolean[] paths = new boolean[4];
    String treasure;
    int orientation;
    String type;
    //Pass in -1 for x and y if the piece doesn't have a designated spot on the board
    //So it can get randomly placed later
    int[] spot = new int[2];
    
    //Create the Pieces
    Piece(String type, int orientation, String treasure, int x, int y) {
        //Import pictures and set their orientation
        super(true,"sprites/"+type+"-"+orientation+".png");
        //Put treasures on the them and get their location on the board
        this.treasure = treasure;
        this.orientation = orientation;
        this.type = type;
        spot[0] = x;
        spot[1] = y;
        setPaths(this);
    }
    //If piece cannot move from inserted column/row, set piece x value to -1
    public boolean isitGlued() {
        if (spot[0] == -1) {
            return false;
        }
        return true;
    }
    
    public boolean[] getPaths() {
        return paths;
    }

    public String getTreasure() {
        return treasure.toLowerCase();
    }

    public void removeTreasure(){
        this.treasure="null";
    }

    public int[] getSpot() {
        return spot;
    }
    //Get current paths(N,E,S,W) of the piece
    public void updatePaths() {
        //For loop that gets the orientation of the piece
        for (int i = 0; i < orientation;i++) {
            boolean temp = paths[3];
            for (int j = 3; j > 0; j--) {
                paths[j] = paths[j-1];
                paths[j-1] = temp;
            }
        }
    }
    //Rotate the piece counter clockwise
    public void rotateLeft() { 
    
        boolean temp = paths[0];
        //For loop that turns the piece towards the left
        for (int i=0; i<3 ; i++){
             paths[i]=paths[i+1];
        }
        paths[3]=temp;

        if (!isitGlued()) {
            if (orientation == 0) {
                orientation = 3;
            } else {
                orientation--;
            }

        }
    }
    //Rotate the piece clockwise
    public void rotateRight() {

        boolean temp = paths[3];
        //For loop that turns the piece to the right
        for (int i=3; i>0 ; i--){
             
             paths[i]=paths[i-1];
             paths[i-1]=temp;
        }
        
       if (!isitGlued()) {
           orientation++;
           orientation%=4;
       }
    }
    
    //Set the paths for the 3 types of pieces
    public final void setPaths(Piece piece){
        if ("I".equals(piece.type)){
            paths[0]=true; //North
            paths[1]=false; //East
            paths[2]=true; //South
            paths[3]=false; //West
        }
        else if ("L".equals(piece.type)){
            paths[0]=true;
            paths[1]=true;
            paths[2]=false;
            paths[3]=false;
            
        }
        else if ("T".equals(piece.type)){
            paths[0]=true;
            paths[1]=true;
            paths[2]=true;
            paths[3]=false;
            
        }
        else if ("X".equals(this.type)){
            paths[0]=true;
            paths[1]=true;
            paths[2]=true;
            paths[3]=true;
        }
        //Doesn't rotate if orientation is 0 otherwise makes paths match based on 
        int rotations = piece.orientation;
        for (int i=0;i<rotations;i++){
            piece.rotateRight(); 
        }
    }
}
