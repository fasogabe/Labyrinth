package classes;

import ch.aplu.jgamegrid.*;
import ch.aplu.jgamegrid.GGMouse;
import ch.aplu.jgamegrid.GGMouseListener;
import ch.aplu.jgamegrid.Location;
import java.awt.*;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.*;

public class Labyrinth extends JFrame implements ActionListener, GGMouseListener{
	
    
	
//------------------ VARIABLES -------------------------------------------------------
        // GUI components
        public GameGrid gg;
        public SparePanel sp;
        public CardPanel ca;
        JButton button;
	
        // Game variables
	private boolean debug = true;
	private ArrayList<Piece> pieces = new ArrayList();
	private Piece[][] board = new Piece[7][7];
	private Piece spare;
	private Player player1;
	private Player player2;
	private ArrayList<String> deck = new ArrayList(); 
	//tracker for whose turn it is
	boolean tracker;
	//playing a cp?
	boolean playCP;
	boolean gameOver = false;
        //private GameGrid gg;
        //static Labyrinth labyrinth;
        //static Labyrinth labyrinthTest;
        public int[] insertLoc = new int[2];
        public int[] usersGoal = new int[2];
        boolean usersGoalEntered = false;
        AtomicBoolean insertLocEntered;
        private int[] whereToMoveCP = new int[2];
        
        Thread threadObject;
        
        
        public Labyrinth(){
            
                super("Labyrinth");
                //setResizable(false);
                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                //setPreferredSize(new Dimension(875, 565));
                setContentPane(new JLabel(new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/src/images/stone_background.png")));
                setLayout(new GridBagLayout()); // set layout manager
                
                this.initializePieces();    // Pieces created
                this.initializeBoard();     // Board, Spare created
		this.initializeDeck();      // Deck created
		this.initializePlayers();   // Players initialized - uses static methods for dialague
                this.initComponents();      // GameGrid, SparePanel, CardPanel, JButtons created
                
                //gg.addActor(player1, new Location(0,0));
                
		this.playGame();            // Game initiated
        }

	public Labyrinth(boolean testing){

        }
	
//----------------- SET UP THE GAME --------------------------------------------------	
	
	private void initializePieces(){
		//start by adding all the pieces that are "glued"
		pieces.add( new Piece("L",1,"null",0,6) ); // L-1
		pieces.add( new Piece("T",1,"A",2,6) ); // T-1
		pieces.add( new Piece("T",1,"B",4,6) ); // T-1
		pieces.add( new Piece("L",2,"null",6,6) ); // L-2
		pieces.add( new Piece("T",0,"C",0,4) ); // T-0
		pieces.add( new Piece("T",0,"D",2,4) ); //T-0
		pieces.add( new Piece("T",1,"E",4,4) ); //T-1
		pieces.add( new Piece("T",2,"F",6,4) ); //T-2
		pieces.add( new Piece("T",0,"G",0,2) ); //T-0
		pieces.add( new Piece("T",3,"H",2,2) ); //T-3
		pieces.add( new Piece("T",2,"I",4,2) ); //T-2
		pieces.add( new Piece("T",2,"J",6,2) ); //T-2
		pieces.add( new Piece("L",0,"null",0,0) );//L-0
		pieces.add( new Piece("T",3,"K",2,0) ); //T-3
		pieces.add( new Piece("T",3,"L",4,0) ); //T-3
		pieces.add( new Piece("L",3,"null",6,0) ); //L-3
		//now add the rest of the treasure pieces (6 L's and 6 T's)
		pieces.add( new Piece("L",0,"M",-1,-1)); // Change first 6 to L - 0 
		pieces.add( new Piece("L",0,"N",-1,-1));
		pieces.add( new Piece("L",0,"O",-1,-1));
		pieces.add( new Piece("L",0,"P",-1,-1));
		pieces.add( new Piece("L",0,"Q",-1,-1));
		pieces.add( new Piece("L",0,"R",-1,-1));
		pieces.add( new Piece("T",0,"S",-1,-1)); // Change these 6 to T - 0 
		pieces.add( new Piece("T",0,"T",-1,-1));
		pieces.add( new Piece("T",0,"U",-1,-1));
		pieces.add( new Piece("T",0,"V",-1,-1));
		pieces.add( new Piece("T",0,"W",-1,-1));
		pieces.add( new Piece("T",0,"X",-1,-1));
		//finally the last null pieces (10 L's and 12 I's)
		pieces.add( new Piece("L",0,"null",-1,-1)); // Change first 10 to L- 0 
		pieces.add( new Piece("L",0,"null",-1,-1));
		pieces.add( new Piece("L",0,"null",-1,-1));
		pieces.add( new Piece("L",0,"null",-1,-1));
		pieces.add( new Piece("L",0,"null",-1,-1));
		pieces.add( new Piece("L",0,"null",-1,-1));
		pieces.add( new Piece("L",0,"null",-1,-1));
		pieces.add( new Piece("L",0,"null",-1,-1));
		pieces.add( new Piece("L",0,"null",-1,-1));
		pieces.add( new Piece("L",0,"null",-1,-1));
		pieces.add( new Piece("I",0,"null",-1,-1)); // Change these 12 to I- 0 
		pieces.add( new Piece("I",0,"null",-1,-1));
		pieces.add( new Piece("I",0,"null",-1,-1));
		pieces.add( new Piece("I",0,"null",-1,-1));
		pieces.add( new Piece("I",0,"null",-1,-1));
		pieces.add( new Piece("I",0,"null",-1,-1));
		pieces.add( new Piece("I",0,"null",-1,-1));
		pieces.add( new Piece("I",0,"null",-1,-1));
		pieces.add( new Piece("I",0,"null",-1,-1));
		pieces.add( new Piece("I",0,"null",-1,-1));
		pieces.add( new Piece("I",0,"null",-1,-1));
		pieces.add( new Piece("I",0,"null",-1,-1));
	}
	private void initializeBoard(){
		//this for loop places all the glued pieces in the correct location
		for(int i =0; i<pieces.size(); i++){
			Piece thisPiece = pieces.get(i);
			if(thisPiece.isitGlued() == true){
				int x = thisPiece.spot[0];
				int y = thisPiece.spot[1];
				board[x][y] = thisPiece;
				pieces.remove(thisPiece);
				i= i-1;
			}
		}
		//this while loop will choose a random remaining piece, spin it to a random orientation, then put it on the board
		while(pieces.size()!=1){
			Random rand = new Random();
			int randomPiece = rand.nextInt(pieces.size());
			Piece aPiece = pieces.get(randomPiece); // refactor : change name of aPiece maybe?
			int randomSpin = rand.nextInt(4);
			aPiece.orientation = randomSpin;
			for(int i=0;i<7;i++){
				for(int j=0;j<7;j++){
					if(board[i][j]==null){
						board[i][j]= aPiece;
						pieces.remove(aPiece);
						//kill the for loops after the piece is placed
						i=7;
						j=7;
					}
				}
			}	
		}
                
                
                //the last remaining piece is the spare
		spare = pieces.get(0);
		pieces.remove(spare);
                
                
                
		
	
                
        }
	private void initializeDeck(){
		//add cards to deck
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
		//now shuffle it
		Random rand = new Random();
		while(deckTemp.size() >= 1){
			int randomCardIndex = rand.nextInt(deckTemp.size());
			String card = deckTemp.get(randomCardIndex);
			deck.add(card);
			deckTemp.remove(card);
		}
		
	}	
	private void initializePlayers(){
		//get user input from a gui that pops up at the start maybe?
		String player1Name = getPlayer1Name();
		ArrayList<String> halfDeck = new ArrayList(); 
		for(int i=0;i<deck.size()/2;i++){
			halfDeck.add(deck.get(2*i));
		}
		for(int i=0;i<halfDeck.size();i++){
			deck.remove(halfDeck.get(i));
		}
		player1 = new Player(player1Name,"blue",6,6,halfDeck);
		//user input to say, "do you want to play a cp or 1V1
		playCP = whoToPlay();
		if(playCP == true){
			player2 = new Player("CP","green",6,0,deck);
                        player2.isCP = true;
		}
		else{
			String player2Name = getPlayer2Name();
			player2 = new Player(player2Name,"green",6,0,deck);
		}
//              
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
        button.addActionListener(this);
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
        button.addActionListener(this);
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
        button.addActionListener(this);
        gbc.weightx = 0.5;
        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 40);
        getContentPane().add(button, gbc);
        
        
        // SparePanel
        sp = new SparePanel(spare);  // Create sparepanel object
        gbc.gridheight = 4;
        gbc.gridx = 9;
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 20, 20, 20);
        getContentPane().add(sp, gbc);
        
        // CardPanel
        String currTreasure = getWhoseTurn().currentTreasure;
        ca = new CardPanel(currTreasure);  // Create cardpanel object
        gbc.gridheight = 4;
        gbc.gridx = 9;
        gbc.gridy = 4;
        gbc.insets = new Insets(20, 20, 20, 20);
        getContentPane().add(ca, gbc);
        

        
        tri = new ImageIcon("/Users/Fasogabe/NetBeansProjects/Labyrinth/src/images/right_triangle.png");
        // Button 7
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("7");
        button.addActionListener(this);
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
        button.addActionListener(this);
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
        button.addActionListener(this);
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 40, 0);
        getContentPane().add(button, gbc);
        
        
        // GameGrid panel
        gg = new GameGrid();         // Create gui object (the board)
        gg.setCellSize(62);
        gg.setNbHorzCells(7);
        gg.setNbVertCells(7);
        gg.setGridColor(Color.white);
        gg.addMouseListener(this,GGMouse.lDClick);
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
        button.addActionListener(this);
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
        button.addActionListener(this);
        gbc.gridx = 8;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        getContentPane().add(button, gbc);
        
        
        // Button 12
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("12");
        button.addActionListener(this);
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
        button.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 40, 0, 0);
        getContentPane().add(button, gbc);
        
        
        // Button 4
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("4");
        button.addActionListener(this);
        gbc.gridx = 4;
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 0, 0, 0);
        getContentPane().add(button, gbc);
        
        
        // Button 6
        button = new JButton(tri);
        button.setPreferredSize(new Dimension(39,39));
        button.setBorder(null);
        button.setActionCommand("6");
        button.addActionListener(this);
        gbc.gridx = 6;
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 0, 0, 40);
        getContentPane().add(button, gbc);
        
        
        // Pack components
        pack();
        
        // Display initial board setup
        displayBoard(board);
        gg.addActor(player1, new Location(0,0));
        //gg.doRun();

    }
    
    void displayBoard(Piece[][] board) {
        for (int i=0;i<7;i++){
            for (int j=0;j<7;j++){
                Location loc = new Location(j,6-i);
                gg.removeActorsAt(loc);
                Piece piece = board[j][i];
                String type = piece.type;
                int orientation = piece.orientation;    
                String treasure = piece.treasure;

                Piece thisPiece = new Piece(type, orientation, treasure, j, 6-i);
                gg.addActor(thisPiece, loc); 
                if (!treasure.equals("null")){
                    Treasure t = new Treasure(treasure);
                    gg.addActor(t, loc);
                }

            }
        }
        show();
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
        Object[] numbers = {"1","2","3","4","5","6","7","8","9","10","11","12"};
        String s = (String)JOptionPane.showInputDialog(
                    this,
                    "Choose your insert row or column:",
                    "Choose Insert Location",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    numbers,
                    null);
//        System.out.println("Where to insert piece?");
//        String insertLocation = (String)JOptionPane.showInputDialog("Where to insert piece?");
        switch (s) {
            case "1":
                insertLoc[0] = 1;//1,6
                insertLoc[1] = 6;
            case "2":
                insertLoc[0] = 3;//3,6
                insertLoc[1] = 6;
            case "3":
                insertLoc[0] = 5;//5,6
                insertLoc[1] = 6;
            case "4":
                insertLoc[0] = 6;//6,5
                insertLoc[1] = 5;
            case "5":
                insertLoc[0] = 6;//6,3
                insertLoc[1] = 3;
            case "6":
                insertLoc[0] = 6;//6,1
                insertLoc[1] = 1;
            case "7":
                insertLoc[0] = 5;//5,0
                insertLoc[1] = 0;
            case "8":
                insertLoc[0] = 3;//3,0
                insertLoc[1] = 0;
            case "9":
                insertLoc[0] = 1;//1,0
                insertLoc[1] = 0;
            case "10":
                insertLoc[0] = 0;//0,1
                insertLoc[1] = 1;
            case "11":
                insertLoc[0] = 0;//0,3
                insertLoc[1] = 3;
            case "12":
                insertLoc[0] = 0;//0,5
                insertLoc[1] = 5;
        }
        return insertLoc;
        
        
    }
    
    public int[] wantToMoveHere() {
        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("x:"));
        myPanel.add(xField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("y:"));
        myPanel.add(yField);
        int[] loc = new int[2];
        int result = JOptionPane.showConfirmDialog(this, myPanel, 
               "To chose where to move your piece, please enter X and Y values.", JOptionPane.OK_OPTION);
        
        loc[0] = Integer.parseInt(xField.getText());
        loc[1] = Integer.parseInt(yField.getText());
        
        return loc;
        
        
//        while (!usersGoalEntered ){ 
//            System.out.print("");
//        }
//        usersGoalEntered=false;
//        return convertToGuiLoc(loc);
    }
	
	
//------------------- GAME PLAY METHODS -----------------------------------------------	
	
	//to change the turn
	private void nextTurn(){
                insertLocEntered = new AtomicBoolean(false);
            
                // reset userGoal and insertLoc
                usersGoal[0] = -1;
                usersGoal[1] = -1;
                insertLoc[0] = -1;
                insertLoc[1] = -1;
                
                if (playCP) {
                    ca.setCurrentTreasure(player1.currentTreasure);
                } else {
                    ca.setCurrentTreasure(getWhoseTurn().currentTreasure);
                }
                
                
		if(tracker==true){
			tracker=false;
		}else{
			tracker=true;
		}
	}
	//to check whose turn it is
	public Player getWhoseTurn(){
		if(tracker==true){
			return player1;
		}
		return player2;
	}
	public Piece[][] getBoard(){
		return board;
	}
        public Piece getSpare(){
		return spare;
	}
        public void setSpare(Piece spare) {
                this.spare = spare;
        }
	
	private void isTheGameOver(){
		if(player1.cardsLeft == 0 || player2.cardsLeft == 0){
			gameOver = true;
		}
	}
	
	//----HAVEN'T TESTED CANmOVE() OR PATHeXISTS() YET, WILL BE EASIER WITH GUI ----------------------------------------
	private boolean canMove(int x, int y, String dir){
		if("N".equals(dir)){
			if (board[x][y].getPaths()[0] == true && board[x][y+1].getPaths()[2] == true){
				return true;
			}
		}
		if("E".equals(dir)){
			if (board[x][y].getPaths()[1] == true && board[x+1][y].getPaths()[3] == true){
				return true;
			}
		}
		if("S".equals(dir)){
			if (board[x][y].getPaths()[2] == true && board[x][y-1].getPaths()[0] == true){
				return true;
			}
		}
		if("W".equals(dir)){
			if (board[x][y].getPaths()[3] == true && board[x-1][y].getPaths()[1] == true){
				return true;
			}
		}
		return false;
	}
	private boolean pathExists(Boolean[][] visited, int x1, int y1, int x2, int y2){
            //set up the visited array list and how to check it
                Boolean[][] newVisited = visited;
		newVisited[x1][y1]=true;
                
//                for(int i=0;i<7;i++){
//                    System.out.println(Arrays.toString(newVisited[i]));
//                }

		//kill recursion and a path exists
		if(x1==x2 && y1==y2){
			return true;
		}
                
		//check for edge of board, a proper path, and if it hasn't been visited
		if(y1<6 && canMove(x1,y1,"N") && newVisited[x1][y1+1]==false){
                    System.out.println("hellon");
			return pathExists(newVisited,x1,y1+1,x2,y2);
		}
		if(x1<6 && canMove(x1,y1,"E") && newVisited[x1+1][y1]==false){
                    System.out.println("helloe");
			return pathExists(newVisited,x1+1,y1,x2,y2);
		}
		if(y1>0 && canMove(x1,y1,"S") && newVisited[x1][y1-1]==false){
                    System.out.println("hellos");
			return pathExists(newVisited,x1,y1-1,x2,y2);
		}
		if(x1>0 && canMove(x1,y1,"W") && newVisited[x1-1][y1]==false){
                    System.out.println("hellow");
			return pathExists(newVisited,x1-1,y1,x2,y2);
                        
		}		
		//If there's nowhere left to check and you haven't made it to the {x2,y2}
		return false;
	}
        	private Boolean pathExistsCP(Boolean[][] visited, int x1, int y1){
            //set up the visited array list and how to check it
                Boolean[][] newVisited = visited;
		newVisited[x1][y1]=true;
            
                if(board[x1][y1].getTreasure().equals(player2.getCurrentCard())){
                    whereToMoveCP[0] = x1;
                    whereToMoveCP[1] = y1;
                    return true;
                }
            
                
		//check for edge of board, a proper path, and if it hasn't been visited
		if(y1<6 && canMove(x1,y1,"N") && newVisited[x1][y1+1]==false){
                    System.out.println("CPn");
			return pathExistsCP(newVisited,x1,y1+1);
                        
                    }
		if(x1<6 && canMove(x1,y1,"E") && newVisited[x1+1][y1]==false){
                    System.out.println("CPe");
			return pathExistsCP(newVisited,x1+1,y1);
        }
		if(y1>0 && canMove(x1,y1,"S") && newVisited[x1][y1-1]==false){
                    System.out.println("CPs");
			return pathExistsCP(newVisited,x1,y1-1);
		}
		if(x1>0 && canMove(x1,y1,"W") && newVisited[x1-1][y1]==false){
                    System.out.println("CPw");
			return pathExistsCP(newVisited,x1-1,y1);
		
	}
		//If there's nowhere left to check and you haven't made it to the {x2,y2}
                return false;
	}
	private boolean checkForTreasure(Player player){
		//checks if the piece the player moved to has the treasure they need
		Location position = player.getLocation();
		int x = position.x;
		int y = position.y;
            //if the piece the player is on has the treasure they need, return true
		return board[x][y].getTreasure().equals(player.getCurrentCard());
	}
	//method updates board and new spare piece
	private void insertPiece(int[] location){
		//the orange triangles are the location
		int x = location[0];
		int y = location[1];
		ArrayList<Piece> shiftArea = new ArrayList();
		if(y==6){
			for(int i =0;i<7;i++){
				shiftArea.add(board[x][y-i]);
			}
			for(int i = 0; i<7; i++){
				if(i==0){
					board[x][y] = spare;
				}else if(i==6){
					board[x][y-i] = shiftArea.get(0);
					shiftArea.remove(0);
					spare = shiftArea.get(0);
					shiftArea.remove(0);
				}else{
					board[x][y-i] = shiftArea.get(0);
					shiftArea.remove(0);
				}
			}
		}
		if(x==6){
			for(int i =0;i<7;i++){
				shiftArea.add(board[x-i][y]);
			}
			for(int i = 0; i<7; i++){
				if(i==0){
					board[x][y] = spare;
				}else if(i==6){
					board[x-i][y] = shiftArea.get(0);
					shiftArea.remove(0);
					spare = shiftArea.get(0);
					shiftArea.remove(0);
				}else{
					board[x-i][y] = shiftArea.get(0);
					shiftArea.remove(0);
				}
			}
		}
		if(y==0){
			for(int i =0;i<7;i++){
				shiftArea.add(board[x][y+i]);
			}
			for(int i = 0; i<7; i++){
				if(i==0){
					board[x][y] = spare;
				}else if(i==6){
					board[x][y+i] = shiftArea.get(0);
					shiftArea.remove(0);
					spare = shiftArea.get(0);
					shiftArea.remove(0);
				}else{
					board[x][y+i] = shiftArea.get(0);
					shiftArea.remove(0);
				}
			}
		}
		if(x==0){
			for(int i =0;i<7;i++){
				shiftArea.add(board[x+i][y]);
			}
			for(int i = 0; i<7; i++){
				if(i==0){
					board[x][y] = spare;
				}else if(i==6){
					board[x+i][y] = shiftArea.get(0);
					shiftArea.remove(0);
					spare = shiftArea.get(0);
					shiftArea.remove(0);
				}else{
					board[x+i][y] = shiftArea.get(0);
					shiftArea.remove(0);
				}
			}
		}
	}
        //needs to be tested
	//will move the player with the pieces that slide when a piece is inserted and
	//the player will wrap around if it goes off the board
	private void shiftPlayerLocation(Player player, int[] loc){
		int x = loc[0];
		int y = loc[1];
		int playerx = player.location[0];
		int playery = player.location[1];
		if(x==0){
			if(y==playery){
				if(playery<6){
					player.updateLocation(playerx, playery+1);
				} else {
					player.updateLocation(playerx, 0);	
				}
			}
		}
		if(y==0){
			if(x==playerx){
				if(playerx<6){
					player.updateLocation(playerx+1, playery);
				} else {
					player.updateLocation(0, playery);	
				}
			}
		}
		if(x==6){
			if(y==playery){
				if(playery>0){
					player.updateLocation(playerx, playery-1);
				} else {
					player.updateLocation(playerx, 6);	
				}
			}
		}
		if(y==6){
			if(x==playerx){
				if(playerx>0){
					player.updateLocation(playerx-1, playery);
				} else {
					player.updateLocation(6, playery);	
				}
			}
		}
	}
        
	
	public void playGame(){

		while(gameOver == false){
			nextTurn();
			Player player = getWhoseTurn();
                        
                        System.out.println("Spare= " +spare.type+"-"+spare.orientation);
                        
                        // get user input on where to insert piece
                        insertLoc = getInsertLocation();
                        
                        
                        //System.out.println("button before here^^^^");
			insertPiece(insertLoc);
                        
                        
                        //check if the play shifts with the board
			shiftPlayerLocation(player1,insertLoc);
			shiftPlayerLocation(player2,insertLoc);
			
			//NEED TO UPDATE THE gui HERE BECAUSE THE BOARD SHIFTED
			displayBoard(board);
			//get where the player wants to move and where they are

                    int moveX=6;
                    int moveY=6;
                    if(player.isCP == false){    
			int hereX = player.getLocation().x;
			int hereY = player.getLocation().y;
			int[] moveTo = wantToMoveHere();
			moveX = moveTo[0];
			moveY = moveTo[1];

                        
                        
			//some sort of recursive canMove in pathExists
			Boolean[][] visited = new Boolean[7][7];
                        for(int i =0;i<7;i++){
                             for(int j =0;j<7;j++){
                                 visited[i][j]= false;
                             }
                        }
                        
			while(pathExists(visited,hereX,hereY,moveX,moveY)==false){
				//tell user to try a new spot
				moveTo = wantToMoveHere();
                                usersGoalEntered=false;
				moveX = moveTo[0];
				moveY = moveTo[1];
                                System.out.println("herex: " + hereX);
                                System.out.println("herey: " + hereY);
                                System.out.println("movex: " + moveX);
                                System.out.println("movey: " + moveY);
                                			
                            for(int i =0;i<7;i++){
                                 for(int j =0;j<7;j++){
                                     visited[i][j]= false;
			}
                        }
                                //System.out.println("moveX and moveY =  " + moveX + " ,"+ moveY);
			}
                    }
                    else{
                        Boolean[][] visited = new Boolean[7][7];
                        int hereX = player.getLocation().x;
			int hereY = player.getLocation().y;
                        if(pathExistsCP(visited,hereX,hereY)==true){
                            moveX = whereToMoveCP[0];
                            moveY = whereToMoveCP[1];
                        }
                        else{
                            //move to some random location!!!!!!!!!!!!!1
                        }
                    }
			
			//if path exists move the player to the piece
			player.updateLocation(moveX,moveY);
			
                        //System.out.println(Arrays.toString(player.location));
                        
			//then check for treasure for that player
			if(checkForTreasure(player)==true){
				System.out.println("You found treasure");
				player.flipCard();
			}
			
			//NEED TO UPDATE THE gui HERE BECAUSE THE PLAYER MOVED
			displayBoard(board);
			//lastly check if the game is over
			isTheGameOver();
			//just to go through one round when debug is set to true so while loop can end
			if(debug==true){
				gameOver=true;
			}
		}
	}
//        public static void testPathExists(){
//              for (int i =0; i<7; i++){
//                    for (int j =0; j<7; j++){
//                         // An x-piece is only for testing but all paths are true so a path always exists for every combination
//                        labyrinthTest.board[i][j]=new Piece("X",0,"A",j,i);
//	
//                        System.out.println(i+ " , " + j + "=" +Arrays.toString(labyrinthTest.board[i][j].paths));
//                    }
//
//        }
//                ArrayList<int[]> visited = new ArrayList();
//                //System.out.println(labyrinthTest.pathExists(visited,3,3,4,4));
//
//	}
        
//--------------------------- ACTION LISTENERS ------------------------------------------------
        
        @Override
    public void actionPerformed(ActionEvent e) {
        String Action = e.getActionCommand();
        System.out.println(Arrays.toString(insertLoc));
        switch (Action) {
            case "1":
                insertLoc[0] = 1;
                insertLoc[1] = 6;
            case "2":
                insertLoc[0] = 1;
                insertLoc[1] = 0;
            case "3":
                insertLoc[0] = 3;
                insertLoc[1] = 6;
            case "4":
                insertLoc[0] = 3;
                insertLoc[1] = 0;
            case "5":
                insertLoc[0] = 5;
                insertLoc[1] = 6;
            case "6":
                insertLoc[0] = 5;
                insertLoc[1] = 0;
            case "7":
                insertLoc[0] = 0;
                insertLoc[1] = 5;
            case "8":
                insertLoc[0] = 6;
                insertLoc[1] = 5;
            case "9":
                insertLoc[0] = 0;
                insertLoc[1] = 3;
            case "10":
                insertLoc[0] = 6;
                insertLoc[1] = 3;
            case "11":
                insertLoc[0] = 0;
                insertLoc[1] = 1;
            case "12":
                insertLoc[0] = 6;
                insertLoc[1] = 1;
        }
        this.spare = sp.spare;
        System.out.println(Arrays.toString(insertLoc));
//        insertPiece(insertLoc);
//        
//        displayBoard(board);
    }
        
        public boolean mouseEvent(GGMouse mouse) {
        
            Location location = gg.toLocationInGrid(mouse.getX(), mouse.getY());
    
            int[] loc = {location.x,location.y};
    

            switch (mouse.getEvent()) {
            
                case GGMouse.lDClick:
                    System.out.println("Mouse double clicked");
                    System.out.println(location);
                
                    break;


                default:
                    break;
        }

    return true; 
        
    }
        

	


//--------------------------- MAIN METHOD ----------------------------------------------------
	
	public static void main(String[] args) {
		
                
		EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        Labyrinth labyrinth = new Labyrinth();

                    }
                });
		                
//		if(labyrinth.debug == true){
			//print out the board pieces treasure.
			//you can see any of the variables for the piece by changing labyrinth.board[i][j].???? <-here
//			for(int i=0;i<7;i++){
//				for(int j=0;j<7;j++){
//					System.out.print(labyrinth.board[j][6-i].orientation+"   ");
//				}
//                                System.out.print("\n");
//                                System.out.print("\n");
//			}
//			System.out.println("Spare: " + labyrinth.spare.treasure);
//			
//			System.out.println("Player1 deck: " + labyrinth.player1.deck);
//			
//			System.out.println("Player2 deck: " + labyrinth.player2.deck);
//		}
//
//	}
	
//                }
        }
}
