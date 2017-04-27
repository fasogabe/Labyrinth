/**
 * Class to keep track of the relevant information for a Player of Labyrinth.
 * This includes their name, color, location, as well as their Treasure cards.
 * The constructor creates an Actor with the correct image based on the type
 * and orientation of the piece. This can easily be added to the GameGrid object
 * in Labyrinth (the board).
 * 
 */
package classes;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import java.util.ArrayList;

/**
 *
 * @author seanflynn
 */
public class Player extends Actor {

    String playerName;
    String playerColor;
    //Coordinates of player
    int[] location = new int[2];
    ArrayList<String> deck = new ArrayList();
    ArrayList<String> treasuresFound = new ArrayList();
    int cardsLeft;
    String currentTreasure;
    Boolean isCP = false;

    Player(String player, String color, int startX, int startY, ArrayList cardsDelt) {
        super("sprites/"+ color+ ".png");

        playerName = player;
        playerColor = color;
        updateLocation(startX, startY);
        deck = cardsDelt;
        cardsLeft = deck.size();
        currentTreasure = deck.get(0);
    }

    /**
     * Gets a new treasure card from the deck
     * and sets it as the currentTreasure
     */
    public void flipCard() {
        cardsLeft--;
        treasuresFound.add(currentTreasure);
        deck.remove(currentTreasure);
        currentTreasure = deck.get(0);
    }

    /**
     * Updates the players location to the x and y 
     * values sent to the function.
     * @param x The new x value 
     * @param y The new y value
     */
    public void updateLocation(int x, int y) {
        location[0] = x;
        location[1] = y;
        this.setLocation(new Location(x,y));
    }

    /**
     * Getter for the player's current location as a Location object.
     * This makes it easy to add the Player to the GameGrid in Labyrinth.
     * @return The player's current location
     */
    @Override
    public Location getLocation() {
        Location loc = new Location(location[0],location[1]);
        return loc ;
    }

    /**
     * Getter for the player's color
     * @return The color of the player
     */
    public String getColor() {
        return playerColor;
    }

    /**
     * Getter for player's name
     * @return The player's name
     */
    public String getName() {
        return playerName;
    }

    /**
     * Getter for the player's treasure
     * @return The 
     */
    public String getCurrentCard() {
        return currentTreasure;
    }

    /**
     *
     * @return
     */
    public ArrayList getWhatFlipped() {
        return treasuresFound;
    }

    /**
     *
     * @return
     */
    public int getCardsLeft() {
        return cardsLeft;
    }
}
