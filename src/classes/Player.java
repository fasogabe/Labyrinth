/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.ArrayList;

/**
 *
 * @author seanflynn
 */
public class Player {

    String playerName;
    String playerColor;
    //Coordinates of player
    int[] location = new int[2];
    ArrayList<String> deck = new ArrayList();
    ArrayList<String> treasuresFound = new ArrayList();
    int cardsLeft;
    String currentTreasure;

    Player(String player, String color, int startX, int startY, ArrayList cardsDelt) {
        playerName = player;
        playerColor = color;
        updateLocation(startX, startY);
        deck = cardsDelt;
        cardsLeft = deck.size();
        currentTreasure = deck.get(0);
    }

    //if the player lands on their treasure
    public void flipCard() {
        cardsLeft--;
        treasuresFound.add(currentTreasure);
        deck.remove(currentTreasure);
        currentTreasure = deck.get(0);
    }

    public void updateLocation(int x, int y) {
        location[0] = x;
        location[1] = y;
    }

    public int[] getLocation() {
        return location;
    }

    public String getColor() {
        return playerColor;
    }

    public String getName() {
        return playerName;
    }

    public String getCurrentCard() {
        return currentTreasure;
    }

    public ArrayList getWhatFlipped() {
        return treasuresFound;
    }

    public int getCardsLeft() {
        return cardsLeft;
    }
}
