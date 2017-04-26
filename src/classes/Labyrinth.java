/* Labyrinth class
CS 205 Final Project
*/

package classes;

//import gamegrid, event handlers and java functions
import ch.aplu.jgamegrid.*;
import ch.aplu.jgamegrid.GGMouse;
import ch.aplu.jgamegrid.GGMouseListener;
import ch.aplu.jgamegrid.Location;
import java.awt.*;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.*;

/**
 * Labyrinth class that drives the program and initializes the gameplay, board, pieces, 
 * Places the panels on the board, shuffles deck and get the desired treasure
 * Checks whether pathExists between tiles and spins/places pieces on the board
 * @author @sean flynn, alex ram, michael kazour, fabian gaspero-beckstrom
 */
public class Labyrinth extends JFrame implements ActionListener, GGMouseListener {

	// Variables for GUI components
	public GameGrid gg;
	public SparePanel sp;
	public CardPanel ca;
        //12 column/row buttons to insert piece
	JButton button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11,
			button12;
	// Game variables
	private boolean debug = false;
	private ArrayList<Piece> pieces = new ArrayList();
	private Piece[][] board = new Piece[7][7];
	private Piece spare;
	private Player player1;
	private Player player2;
	private ArrayList<String> deck = new ArrayList();
	// Tracker for whose turn it is
	boolean tracker;
	// Playing a cp?
	boolean playCP;
	boolean gameOver = false;
	// Private GameGrid gg;
	// Static Labyrinth labyrinth;
	// Static Labyrinth labyrinthTest;
	public int[] insertLoc = new int[2];
	public int[] usersGoal = new int[2];
	volatile boolean usersGoalEntered = false;
	volatile boolean insertLocEntered = false;
	private int[] whereToMoveCP = new int[2];
	String filePath = new File("").getAbsolutePath() + "/src";
        // Booleans for whether a path exists or not
	boolean doesPathExist;
	boolean doesCpPathExist;
        // Image Icon for the labyrinth game board
	static ImageIcon labyIcon = new ImageIcon("/images/labyrinth_icon.png");

        //Labyrinth driver that calls the game methods
	public Labyrinth() {
		super("Labyrinth");
		//Design the layout of the gamegrid
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(860, 560));
		setContentPane(new JLabel(new ImageIcon(filePath + "/images/stone_background.png")));
		setLayout(new GridBagLayout()); // set layout manager

                //Create and call pieces
		this.initializePieces();
                //Create and call board
                this.initializeBoard();
                //Create and call deck
		this.initializeDeck();
                //Create and call players, uses static methods for dialog
		this.initializePlayers();
		//Create and call GameGrid, SparePanel, CardPanel and JButtons
		this.initComponents();			
		//Get whose turn it is next
		this.nextTurn();	
        }
        
        //Create all the pieces that go on the board
	private void initializePieces() {
		//Start by adding all the pieces that are "glued"
		pieces.add(new Piece("L", 1, "null", 0, 6));
		pieces.add(new Piece("T", 1, "A", 2, 6));
		pieces.add(new Piece("T", 1, "B", 4, 6));
		pieces.add(new Piece("L", 2, "null", 6, 6));
		pieces.add(new Piece("T", 0, "C", 0, 4));
		pieces.add(new Piece("T", 0, "D", 2, 4));
		pieces.add(new Piece("T", 1, "E", 4, 4));
                pieces.add(new Piece("T", 2, "F", 6, 4));
		pieces.add(new Piece("T", 0, "G", 0, 2));
		pieces.add(new Piece("T", 3, "H", 2, 2));
		pieces.add(new Piece("T", 2, "I", 4, 2));
		pieces.add(new Piece("T", 2, "J", 6, 2));
		pieces.add(new Piece("L", 0, "null", 0, 0));
		pieces.add(new Piece("T", 3, "K", 2, 0));
		pieces.add(new Piece("T", 3, "L", 4, 0));
		pieces.add(new Piece("L", 3, "null", 6, 0)); // L-3
		//Now add the rest of the treasure pieces (6 L's and 6 T's)
		pieces.add(new Piece("L", 0, "M", -1, -1));
		pieces.add(new Piece("L", 0, "N", -1, -1));
		pieces.add(new Piece("L", 0, "O", -1, -1));
		pieces.add(new Piece("L", 0, "P", -1, -1));
		pieces.add(new Piece("L", 0, "Q", -1, -1));
		pieces.add(new Piece("L", 0, "R", -1, -1));
		pieces.add(new Piece("T", 0, "S", -1, -1));
		pieces.add(new Piece("T", 0, "T", -1, -1));
		pieces.add(new Piece("T", 0, "U", -1, -1));
		pieces.add(new Piece("T", 0, "V", -1, -1));
		pieces.add(new Piece("T", 0, "W", -1, -1));
		pieces.add(new Piece("T", 0, "X", -1, -1));
		//Finally the last null pieces (10 L's and 12 I's)
		pieces.add(new Piece("L", 0, "null", -1, -1));						
		pieces.add(new Piece("L", 0, "null", -1, -1));
		pieces.add(new Piece("L", 0, "null", -1, -1));
		pieces.add(new Piece("L", 0, "null", -1, -1));
		pieces.add(new Piece("L", 0, "null", -1, -1));
		pieces.add(new Piece("L", 0, "null", -1, -1));
		pieces.add(new Piece("L", 0, "null", -1, -1));
		pieces.add(new Piece("L", 0, "null", -1, -1));
		pieces.add(new Piece("L", 0, "null", -1, -1));
		pieces.add(new Piece("L", 0, "null", -1, -1));
		pieces.add(new Piece("I", 0, "null", -1, -1));												
		pieces.add(new Piece("I", 0, "null", -1, -1));
		pieces.add(new Piece("I", 0, "null", -1, -1));
		pieces.add(new Piece("I", 0, "null", -1, -1));
		pieces.add(new Piece("I", 0, "null", -1, -1));
		pieces.add(new Piece("I", 0, "null", -1, -1));
		pieces.add(new Piece("I", 0, "null", -1, -1));
		pieces.add(new Piece("I", 0, "null", -1, -1));
		pieces.add(new Piece("I", 0, "null", -1, -1));
		pieces.add(new Piece("I", 0, "null", -1, -1));
		pieces.add(new Piece("I", 0, "null", -1, -1));
		pieces.add(new Piece("I", 0, "null", -1, -1));
	}

        //Place all the pieces on the board
	private void initializeBoard() {
		//This for loop places all the glued pieces in the correct location
		for (int i = 0; i < pieces.size(); i++) {
			Piece thisPiece = pieces.get(i);
                        //If they should be glued, place them down on the correct spot on the board and get the orientation
			if (thisPiece.isitGlued() == true) {
				int x = thisPiece.spot[0];
				int y = thisPiece.spot[1];
				board[x][y] = thisPiece;
				pieces.remove(thisPiece);
				i = i - 1;
			}
		}
		//This while loop will choose a random remaining piece, spin it to a
		//Random orientation, then put it on the board
		while (pieces.size() != 1) {
			Random rand = new Random();
			int randomPiece = rand.nextInt(pieces.size());
			Piece aPiece = pieces.get(randomPiece);
                        //Spin the piece 0-3
			int randomSpin = rand.nextInt(4);
                        //Get the orientation of the Piece
			aPiece.orientation = randomSpin;
                        //This for loop places these randomly spun pieces on the board and kills both loops after the piece is placed
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 7; j++) {
					if (board[i][j] == null) {
						board[i][j] = aPiece;
						board[i][j].updatePaths();
						board[i][j].orientation = randomSpin;
						pieces.remove(aPiece);
						i = 7;
						j = 7;
					}
				}
			}
		}

		//Get the remaining spare piece
		spare = pieces.get(0);
		pieces.remove(spare);

	}
        //Create the deck of cards
	private void initializeDeck() {
		//Add cards to deck
		ArrayList<String> deckTemp = new ArrayList();
		deckTemp.add("A");
		deckTemp.add("B");
		deckTemp.add("C");
		deckTemp.add("D");
		deckTemp.add("E");
		deckTemp.add("F");
		deckTemp.add("G");
		deckTemp.add("H");
		deckTemp.add("I");
		deckTemp.add("J");
		deckTemp.add("K");
		deckTemp.add("L");
		deckTemp.add("M");
		deckTemp.add("N");
		deckTemp.add("O");
		deckTemp.add("P");
		deckTemp.add("Q");
		deckTemp.add("R");
		deckTemp.add("S");
		deckTemp.add("T");
		deckTemp.add("U");
		deckTemp.add("V");
		deckTemp.add("W");
		deckTemp.add("X");
		//Shuffle the deck and get the top card
		Random rand = new Random();
		while (deckTemp.size() >= 1) {
			int randomCardIndex = rand.nextInt(deckTemp.size());
			String card = deckTemp.get(randomCardIndex);
			deck.add(card);
			deckTemp.remove(card);
		}

	}

        //Initialize the players based on if its a user or cp
	private void initializePlayers() {
		//Get the users name
		String player1Name = getPlayer1Name();
                //Get treasure cards for the first player
		ArrayList<String> halfDeck = new ArrayList();
		for (int i = 0; i < deck.size() / 2; i++) {
			halfDeck.add(deck.get(2 * i));
		}
                //Get treasure cards for the second player
		for (int i = 0; i < halfDeck.size(); i++) {
			deck.remove(halfDeck.get(i));
		}
                //Player 1 as blue and in the top right of the grid
		player1 = new Player(player1Name, "blue", 0, 6, halfDeck);
		//Get whether the 2nd player is CPU or Human
                //Player 2 as green and in the bottom left of the grid
		playCP = whoToPlay();
		if (playCP == true) {
			player2 = new Player("CP", "green", 6, 0, deck);
			player2.isCP = true;
		} else {
			String player2Name = getPlayer2Name();
			player2 = new Player(player2Name, "green", 6, 0, deck);
		}
	}

        //Create the layout of the gamegrid
	private void initComponents() {

		//Create layout constraints
		GridBagConstraints gbc = new GridBagConstraints();

		//Create buttons - load new triangle every 3 buttons
		ImageIcon tri = new ImageIcon(filePath + "/images/down_triangle.png");
		//First insert button that starts in the top left of the gamegrid
                //Get the image of the button              
		button1 = new JButton(tri);
		button1.setPreferredSize(new Dimension(39, 39));
		button1.setBorder(null);
		button1.setActionCommand("1");
		button1.addActionListener(this);
		gbc.weightx = 0.5;
                //Location of the button
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 40, 0, 0);
		getContentPane().add(button1, gbc);

		//Third insert button that is in the top middle of the gamegrid
		button3 = new JButton(tri);
		button3.setPreferredSize(new Dimension(39, 39));
		button3.setBorder(null);
		button3.setActionCommand("3");
		button3.addActionListener(this);
		gbc.weightx = 0.5;
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 0);
		getContentPane().add(button3, gbc);

		//Fifth insert button that is in the top right of the gamegrid
		button5 = new JButton(tri);
		button5.setPreferredSize(new Dimension(39, 39));
		button5.setBorder(null);
		button5.setActionCommand("5");
		button5.addActionListener(this);
		gbc.weightx = 0.5;
		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 40);
		getContentPane().add(button5, gbc);

		//The Panel for the spare piece
		sp = new SparePanel(spare);
		gbc.gridheight = 4;
		gbc.gridx = 9;
		gbc.gridy = 2;
		gbc.insets = new Insets(20, 20, 20, 20);
		getContentPane().add(sp, gbc);

		//The Panel for the treasure card
		String currTreasure = getWhoseTurn().currentTreasure;
		ca = new CardPanel(currTreasure);
		gbc.gridheight = 4;
		gbc.gridx = 9;
		gbc.gridy = 4;
		gbc.insets = new Insets(20, 20, 20, 20);
		getContentPane().add(ca, gbc);

		tri = new ImageIcon(filePath + "/images/right_triangle.png");
		//Seventh insert button
		button7 = new JButton(tri);
		button7.setPreferredSize(new Dimension(39, 39));
		button7.setBorder(null);
		button7.setActionCommand("7");
		button7.addActionListener(this);
		gbc.gridheight = 1;
		gbc.weighty = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(40, 0, 0, 0);
		getContentPane().add(button7, gbc);

		//Ninth insert button
		button9 = new JButton(tri);
		button9.setPreferredSize(new Dimension(39, 39));
		button9.setBorder(null);
		button9.setActionCommand("9");
		button9.addActionListener(this);
		gbc.weighty = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(0, 0, 0, 0);
		getContentPane().add(button9, gbc);

		//Eleventh insert button
		button11 = new JButton(tri);
		button11.setPreferredSize(new Dimension(39, 39));
		button11.setBorder(null);
		button11.setActionCommand("11");
		button11.addActionListener(this);
		gbc.weighty = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.insets = new Insets(0, 0, 40, 0);
		getContentPane().add(button11, gbc);

		//GameGrid panel
		gg = new GameGrid();
		gg.setCellSize(62);
		gg.setNbHorzCells(7);
		gg.setNbVertCells(7);
		gg.setGridColor(Color.white);
		gg.addMouseListener(this, GGMouse.lDClick);
		gbc.gridwidth = 7;
		gbc.gridheight = 7;
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		getContentPane().add(gg, gbc);

		tri = new ImageIcon(filePath + "/images/left_triangle.png");
		//Eigth insert button
		button8 = new JButton(tri);
		button8.setPreferredSize(new Dimension(39, 39));
		button8.setBorder(null);
		button8.setActionCommand("8");
		button8.addActionListener(this);
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.gridx = 8;
		gbc.gridy = 2;
		gbc.insets = new Insets(40, 0, 0, 0);
		getContentPane().add(button8, gbc);

		//Tenth insert button
		button10 = new JButton(tri);
		button10.setPreferredSize(new Dimension(39, 39));
		button10.setBorder(null);
		button10.setActionCommand("10");
		button10.addActionListener(this);
		gbc.gridx = 8;
		gbc.gridy = 4;
		gbc.insets = new Insets(0, 0, 0, 0);
		getContentPane().add(button10, gbc);

		//Twelfth insert button
		button12 = new JButton(tri);
		button12.setPreferredSize(new Dimension(39, 39));
		button12.setBorder(null);
		button12.setActionCommand("12");
		button12.addActionListener(this);
		gbc.gridx = 8;
		gbc.gridy = 6;
		gbc.insets = new Insets(0, 0, 40, 0);
		getContentPane().add(button12, gbc);

		tri = new ImageIcon(filePath + "/images/up_triangle.png");
		//Second insert button
		button2 = new JButton(tri);
		button2.setPreferredSize(new Dimension(39, 39));
		button2.setBorder(null);
		button2.setActionCommand("2");
		button2.addActionListener(this);
		gbc.gridx = 2;
		gbc.gridy = 8;
		gbc.insets = new Insets(0, 40, 0, 0);
		getContentPane().add(button2, gbc);

		//Fourth insert button
		button4 = new JButton(tri);
		button4.setPreferredSize(new Dimension(39, 39));
		button4.setBorder(null);
		button4.setActionCommand("4");
		button4.addActionListener(this);
		gbc.gridx = 4;
		gbc.gridy = 8;
		gbc.insets = new Insets(0, 0, 0, 0);
		getContentPane().add(button4, gbc);

		//Sixth insert button
		button6 = new JButton(tri);
		button6.setPreferredSize(new Dimension(39, 39));
		button6.setBorder(null);
		button6.setActionCommand("6");
		button6.addActionListener(this);
		gbc.gridx = 6;
		gbc.gridy = 8;
		gbc.insets = new Insets(0, 0, 0, 40);
		getContentPane().add(button6, gbc);

		//Pack components
		pack();

		//Display initial board setup
		displayBoard();
	}

        //Display the board with the pieces and treasures
	void displayBoard() {
		gg.removeAllActors();
                //Loop through the entire board
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
                                //Set loc variable
				Location loc = new Location(j, 6 - i);
				gg.removeActorsAt(loc);
                                //Set the pieces down
				Piece piece = board[j][i];
				String type = piece.type;
                                //Get the orienation of each piece
				int orientation = piece.orientation;
				String treasure = piece.treasure;
				//Set thisPiece variable
				Piece thisPiece = new Piece(type, orientation, treasure, j, 6 - i);
                                gg.addActor(thisPiece, loc);
                                //Set the treasures down
				if (!treasure.equals("null")) {
					Treasure t = new Treasure(treasure);
					gg.addActor(t, loc);
				}

			}
		}
                //Add players to the board and show the board
		gg.addActor(player1, new Location(player1.location[0], 6 - player1.location[1]));
		gg.addActor(player2, new Location(player2.location[0], 6 - player2.location[1]));
		show();
	}

        //User selects whether to play a CP or Human
	public static boolean whoToPlay() {
		//If user chooses to play a cp return true
		String[] options = { "Computer", "Human" };
		int n = JOptionPane.showOptionDialog(null, "CHOOSE YOUR OPPONENT", "", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, labyIcon, options, options[0]);
		return n == 0;

	}

        //Returns name of the first player
	public static String getPlayer1Name() {
                //Text input box
		String name = (String) JOptionPane.showInputDialog(null, "Welcome to Labyrinth!\n\nEnter your name:", "",
				JOptionPane.OK_OPTION, labyIcon, null, null);
		return name;
	}
        
        //Returns the name of the second player
	public static String getPlayer2Name() {
		//Text input box
		String name = (String) JOptionPane.showInputDialog(null, "Human #2, enter your name:", "",
				JOptionPane.OK_OPTION, labyIcon, null, null);
		return name;
	}

        //Convert the location of the player to the GUI and return the loc
	public static int[] convertToGuiLoc(int[] loc) {
		int x = loc[0];
		int y = 6 - loc[1];
		loc[0] = x;
		loc[1] = y;

		return loc;
	}

	//------------- GAMEPLAY METHODS --------------------
	//Change the turns during gameplay
	private void nextTurn() {
		sp.setSpare(spare);
		//Reset userGoal and insertLoc
		usersGoal[0] = -1;
		usersGoal[1] = -1;
		insertLoc[0] = -1;
		insertLoc[1] = -1;

                //Track whose turn it is
		if (tracker == true) {
			tracker = false;
		} else {
			tracker = true;
		}

                //Get the treasure card for each player if playing a CP
		if (playCP) {
			ca.setCurrentTreasure(player1.currentTreasure);
		} else {
			ca.setCurrentTreasure(getWhoseTurn().currentTreasure);
		}
	}

	//Check whose turn it is
	public Player getWhoseTurn() {
		if (tracker == true) {
			return player1;
		}
		return player2;
	}

        //Get board
	public Piece[][] getBoard() {
		return board;
	}

        //Get Spare
	public Piece getSpare() {
		return spare;
	}

        //Get whether there are any treasure cards left or not
	private void isTheGameOver() {
		if (player1.cardsLeft == 0 || player2.cardsLeft == 0) {
			gameOver = true;
		}
	}

	//Get the direction of the pieces
	private boolean canMove(int x, int y, String dir) {
                //If a piece has a direction of north set the third location in the getPaths array equal to true
		if ("N".equals(dir)) {
			if (board[x][y].getPaths()[0] == true && board[x][y + 1].getPaths()[2] == true) {
				return true;
			}
		}
                //If a piece has a direction of east set the fourth location in the getPaths array equal to true
		if ("E".equals(dir)) {
			if (board[x][y].getPaths()[1] == true && board[x + 1][y].getPaths()[3] == true) {
				return true;
			}
		}
                //If a piece has a direction of south set the first location in the getPaths array equal to true
		if ("S".equals(dir)) {
			if (board[x][y].getPaths()[2] == true && board[x][y - 1].getPaths()[0] == true) {
				return true;
			}
		}
                //If a piece has a direction of west set the second location in the getPaths array equal to true
		if ("W".equals(dir)) {
			if (board[x][y].getPaths()[3] == true && board[x - 1][y].getPaths()[1] == true) {
				return true;
			}
		}
		return false;
	}

        //pathExists that takes the parameters of where the user has visited, where the user is and where they to go
	private boolean pathExists(Boolean[][] visited, int x1, int y1, int x2, int y2) {
		//Set up the visited array list and how to check it
		Boolean[][] newVisited = visited;
		newVisited[x1][y1] = true;

		//Kill recursion and a path exists
		if (x1 == x2 && y1 == y2) {
			doesPathExist=true;
		}

		//Check for edge of board, a proper path, and if it hasn't been visited
                //Check if there is a path in the north direction
		if (y1 < 6 && canMove(x1, y1, "N") && newVisited[x1][y1 + 1] == false) {
			pathExists(newVisited, x1, y1 + 1, x2, y2);
		}
		if (x1 < 6 && canMove(x1, y1, "E") && newVisited[x1 + 1][y1] == false) {
			pathExists(newVisited, x1 + 1, y1, x2, y2);
		}
		if (y1 > 0 && canMove(x1, y1, "S") && newVisited[x1][y1 - 1] == false) {
			pathExists(newVisited, x1, y1 - 1, x2, y2);
		}
		if (x1 > 0 && canMove(x1, y1, "W") && newVisited[x1 - 1][y1] == false) {
			pathExists(newVisited, x1 - 1, y1, x2, y2);

		}
		//Return true or false whether a pathExists
		return doesPathExist;
	}

        //Check if pathExists for CP that accepts where they want to go
	private Boolean pathExistsCP(Boolean[][] visited, int x1, int y1) {
		//Set up the visited array list and how to check it
		Boolean[][] newVisited = visited;
		newVisited[x1][y1] = true;

                //Have the CP move to their treasure card if possible
		if (board[x1][y1].getTreasure().equals(player2.getCurrentCard())) {
			whereToMoveCP[0] = x1;
			whereToMoveCP[1] = y1;
			doesCpPathExist = true;
		}

		//Check for edge of board, a proper path, and if it hasn't been visited
		if (y1 < 6 && canMove(x1, y1, "N") && newVisited[x1][y1 + 1] == false) {
			pathExistsCP(newVisited, x1, y1 + 1);

		}
		if (x1 < 6 && canMove(x1, y1, "E") && newVisited[x1 + 1][y1] == false) {
			pathExistsCP(newVisited, x1 + 1, y1);
		}
		if (y1 > 0 && canMove(x1, y1, "S") && newVisited[x1][y1 - 1] == false) {
			pathExistsCP(newVisited, x1, y1 - 1);
		}
		if (x1 > 0 && canMove(x1, y1, "W") && newVisited[x1 - 1][y1] == false) {
			pathExistsCP(newVisited, x1 - 1, y1);

		}
		//Return true or false whether a pathExists
		return doesCpPathExist;
	}

        //Check if user has reached their treasure
	private boolean checkForTreasure(Player player) {
		//Checks if the piece the player moved to has the treasure they need
		Location position = player.getLocation();
		int x = position.x;
		int y = position.y;
		//If the piece the player is on has the treasure they need, return true
		return board[x][y].getTreasure().equals(player.getCurrentCard());
	}

	//Method updates board and new spare piece
	private void insertPiece(int[] location) {
		//The orange triangles are the location
		int x = location[0];
		int y = location[1];
		ArrayList<Piece> shiftArea = new ArrayList();
                //Shift the board if the spare piece is inserted on the top of the board
		if (y == 6) {
			for (int i = 0; i < 7; i++) {
				shiftArea.add(board[x][y - i]);
			}
			for (int i = 0; i < 7; i++) {
				if (i == 0) {
					board[x][y] = spare;
				} else if (i == 6) {
					board[x][y - i] = shiftArea.get(0);
					shiftArea.remove(0);
					spare = shiftArea.get(0);
					shiftArea.remove(0);
				} else {
					board[x][y - i] = shiftArea.get(0);
					shiftArea.remove(0);
				}
			}
		}
                //Shift the board if the spare piece is inserted on the right side of the board
		if (x == 6) {
			for (int i = 0; i < 7; i++) {
				shiftArea.add(board[x - i][y]);
			}
			for (int i = 0; i < 7; i++) {
				if (i == 0) {
					board[x][y] = spare;
				} else if (i == 6) {
					board[x - i][y] = shiftArea.get(0);
					shiftArea.remove(0);
					spare = shiftArea.get(0);
					shiftArea.remove(0);
				} else {
					board[x - i][y] = shiftArea.get(0);
					shiftArea.remove(0);
				}
			}
		}
                //Shift the board if the spare piece is inserted on the bottom of the board
		if (y == 0) {
			for (int i = 0; i < 7; i++) {
				shiftArea.add(board[x][y + i]);
			}
			for (int i = 0; i < 7; i++) {
				if (i == 0) {
					board[x][y] = spare;
				} else if (i == 6) {
					board[x][y + i] = shiftArea.get(0);
					shiftArea.remove(0);
					spare = shiftArea.get(0);
					shiftArea.remove(0);
				} else {
					board[x][y + i] = shiftArea.get(0);
					shiftArea.remove(0);
				}
			}
		}
                //Shift the board if the spare piece is inserted on the left side of the board
		if (x == 0) {
			for (int i = 0; i < 7; i++) {
				shiftArea.add(board[x + i][y]);
			}
			for (int i = 0; i < 7; i++) {
				if (i == 0) {
					board[x][y] = spare;
				} else if (i == 6) {
					board[x + i][y] = shiftArea.get(0);
					shiftArea.remove(0);
					spare = shiftArea.get(0);
					shiftArea.remove(0);
				} else {
					board[x + i][y] = shiftArea.get(0);
					shiftArea.remove(0);
				}
			}
		}
	}

        //Method that accepts the player location and returns the updated location
	private void shiftPlayerLocation(Player player, int[] loc) {
		int x = loc[0];
		int y = loc[1];
		int playerx = player.location[0];
		int playery = player.location[1];
		//Update player location in the left direction
                if (x == 0) {
			if (y == playery) {
				if (playerx < 6) {
					player.updateLocation(playerx+1, playery);
				} else {
					player.updateLocation(0, playery);
				}
			}
		}
                //Update player location in the up direction
		if (y == 0) {
			if (x == playerx) {
				if (playery < 6) {
					player.updateLocation(playerx, playery+1);
				} else {
					player.updateLocation(playerx, 0);
				}
			}
		}
                //Update player location in the left direction
		if (x == 6) {
			if (y == playery) {
				if (playerx > 0) {
					player.updateLocation(playerx-1, playery);
				} else {
					player.updateLocation(6, playery);
				}
			}
		}
                //Update plyaer location in the down direction
		if (y == 6) {
			if (x == playerx) {
				if (playery > 0) {
					player.updateLocation(playerx, playery-1);
				} else {
					player.updateLocation(playerx, 6);
				}
			}
		}
	}

        //Driver method to play the game
	public void playGame(int[] moveTo) {
		//Get whose turn
		Player player = getWhoseTurn();

                //Set where the players are and initate the visited array that is false for every node on the board
		int moveX = 6;
		int moveY = 6;
		Boolean[][] visited = new Boolean[7][7];
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				visited[i][j] = false;
			}
		}
                
                //Get the location of player 2 and where they want to move
		if (player.isCP == false) {
			int hereX = player.getLocation().x;
			int hereY = player.getLocation().y;
			moveX = moveTo[0];
			moveY = moveTo[1];
			doesPathExist = false;
                        
                        //Visited is kept as false if the player cannot move there
			if (pathExists(visited, hereX, hereY, moveX, moveY) == false) {
				for (int i = 0; i < 7; i++) {
					for (int j = 0; j < 7; j++) {
						visited[i][j] = false;
					}
				}
			//If they can move there, update their location to the desired location
			} else {
				usersGoalEntered = true;
				player.updateLocation(moveX, moveY);
			}
                        //AI for the location of the CP
		}       else {
			//Random location for the CP to insert piece
			int[] cpInsert = new int[2];
			cpInsert[0]=5;
			cpInsert[1]=0;
			insertPiece(cpInsert);
			//Now player can insert a piece where ever
			insertLocEntered = false;
			int hereX = player.getLocation().x;
			int hereY = player.getLocation().y;
			doesCpPathExist = false;
                        //Update CPs location if possible
			if (pathExistsCP(visited, hereX, hereY) == true) {
				moveX = whereToMoveCP[0];
				moveY = whereToMoveCP[1];
				player.updateLocation(moveX, moveY);
			} else {
				//Move the CP to a random location on the board
				doesPathExist = false;
				moveX = hereX;
				moveY = hereY;
				Random rand = new Random();
				for(int i =0;i<50;i++){
					int randX = rand.nextInt(7);
					int randY = rand.nextInt(7);
					//Continue to iterate through the board until a pathExists for the CP
                                        for (int m = 0; m < 7; m++) {
						for (int j = 0; j < 7; j++) {
							visited[m][j] = false;
						}
					}
					if(pathExists(visited,hereX,hereY,randX,randY)){
						moveX = randX;
						moveY = randY;
						i=50;
					}
				}
				player.updateLocation(moveX, moveY);
			}
		}

		//If path exists move the player to the piece
		//Then check for treasure for that player
		if (checkForTreasure(player) == true) {
			System.out.println("You found treasure");
			player.flipCard();
		}

		displayBoard();
		//Lastly check if the game is over
		isTheGameOver();
		//Just to go through one round when debug is set to true so while loop
		//Can end
		if (!gameOver && usersGoalEntered) {
			System.out.println("Next Turn");
			nextTurn();
			if(!player.isCP){
				int[] empty = new int[2];
				empty[0] = 0;
				empty[1] = 0;
				playGame(empty);
			}
		}
	}
	//-------------Action Listeners-----------------
        //Insert Piece into column/row listener
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!insertLocEntered) {
                        //If the radio button is pushed for each column/row, insert piece
			String Action = e.getActionCommand();
			System.out.println(Action);
			switch (Action) {
			case "1":
				insertLoc[0] = 1;
				insertLoc[1] = 6;
				break;
			case "2":
				insertLoc[0] = 1;
				insertLoc[1] = 0;
				break;
			case "3":
				insertLoc[0] = 3;
				insertLoc[1] = 6;
				break;
			case "4":
				insertLoc[0] = 3;
				insertLoc[1] = 0;
				break;
			case "5":
				insertLoc[0] = 5;
				insertLoc[1] = 6;
				break;
			case "6":
				insertLoc[0] = 5;
				insertLoc[1] = 0;
				break;
			case "7":
				insertLoc[0] = 0;
				insertLoc[1] = 5;
				break;
			case "8":
				insertLoc[0] = 6;
				insertLoc[1] = 5;
				break;
			case "9":
				insertLoc[0] = 0;
				insertLoc[1] = 3;
				break;
			case "10":
				insertLoc[0] = 6;
				insertLoc[1] = 3;
				break;
			case "11":
				insertLoc[0] = 0;
				insertLoc[1] = 1;
				break;
			case "12":
				insertLoc[0] = 6;
				insertLoc[1] = 1;
				break;
			}
			this.spare = sp.spare;
			System.out.println(Arrays.toString(insertLoc));
			insertPiece(insertLoc);
                        
                        //Shift the player location if the piece shifts the board
			shiftPlayerLocation(player1, insertLoc);
			shiftPlayerLocation(player2, insertLoc);

			sp.setSpare(spare);

			displayBoard();

			insertLocEntered = true;
			usersGoalEntered = false;
		}
	}

        //Clicking the mouse on the GUI events
	public boolean mouseEvent(GGMouse mouse) {
		if (!usersGoalEntered) {
                        //Get the Gui location 
			Location location = gg.toLocationInGrid(mouse.getX(), mouse.getY());

			int[] loc = { location.x, location.y };

			switch (mouse.getEvent()) {

                        //If the mouse is clicked, tell the GUI where the desired location is
			case GGMouse.lDClick:
				System.out.println("Mouse double clicked");
				loc = convertToGuiLoc(loc);
				System.out.println(Arrays.toString(loc));
				playGame(loc);

				break;

			default:
				break;
			}

		}

		return false;

	}
        //------------Main Method---------------------
        //Run Labyrinth
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Labyrinth labyrinth = new Labyrinth();

			}
		});
	}
}
