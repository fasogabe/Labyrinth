package classes;

import ch.aplu.jgamegrid.Location;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Labyrinth {
	
//------------------ VARIABLES -------------------------------------------------------
	
	private boolean debug = true;
	private ArrayList<Piece> pieces = new ArrayList();
	private Piece[][] board = new Piece[
                7][7];
	private Piece spare;
	private Player player1;
	private Player player2;
	private ArrayList<String> deck = new ArrayList(); 
	//tracker for whose turn it is
	boolean tracker;
	//playing a cp?
	boolean playCP;
	boolean gameOver = false;
        private mainGUI gui;
        static Labyrinth labyrinth;
        
        public Labyrinth(){
                //gui = new GUI();
                this.initializePieces();
                this.initializeBoard();
		this.initializeDeck();
		this.initializePlayers();
		this.playGame();
        }
	//only used for testing so the board can be manipulated for testing easily (i.e. all I pieces, T pieces etc) 
	public Labyrinth(boolean testing){
                //gui = new GUI();
//                this.initializePieces();
//                this.initializeBoard();
//		this.initializeDeck();
//		this.initializePlayers();
//		this.playGame();
        }
	
//----------------- SET UP THE GAME --------------------------------------------------	
	
	private void initializePieces(){
		//start by adding all the pieces that are "glued"
		pieces.add( new Piece("L",1,"blueStart",0,6) ); // L-1
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
		pieces.add( new Piece("L",3,"greenStart",6,0) ); //L-3
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
                //gui.displayBoard(board);
                
                // Invoke EventQueue
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new mainGUI(board).setVisible(true);
                        
                    }
                });
                //gui.initComponents(board);
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
		String player1Name = gui.gg.getPlayer1Name();
		ArrayList<String> halfDeck = new ArrayList(); 
		for(int i=0;i<deck.size()/2;i++){
			halfDeck.add(deck.get(2*i));
		}
		for(int i=0;i<halfDeck.size();i++){
			deck.remove(halfDeck.get(i));
		}
		player1 = new Player(player1Name,"blue",0,6,halfDeck);
		//user input to say, "do you want to play a cp or 1V1
		playCP = gui.gg.whoToPlay();
		if(playCP == true){
			player2 = new Player("CP","green",6,0,deck);
		}
		else{
			String player2Name = gui.gg.getPlayer2Name();
			player2 = new Player(player2Name,"green",6,0,deck);
		}
	}
	
//------------------- GAME PLAY METHODS -----------------------------------------------	
	
	//to change the turn
	private void nextTurn(){
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
	private boolean pathExists(ArrayList<int[]> visited, int x1, int y1, int x2, int y2){
		//kill recursion and a path exists
		if(x1==x2 && y1==y2){
			return true;
		}
		//set up the visited array list and how to check it
		int[] here = {x1,y1};
		ArrayList newVisited = visited;
		newVisited.add(here);
		int[] oneN = {x1,y1+1};
		int[] oneE = {x1+1,y1};
		int[] oneS = {x1,y1-1};
		int[] oneW = {x1-1,y1};
		//check for edge of board, a proper path, and if it hasn't been visited
		if(x1<6 && canMove(x1,y1,"N") && newVisited.contains(oneN)==false){
			return pathExists(newVisited,x1,y1+1,x2,y2);
		}
		if(y1<6 && canMove(x1,y1,"E") && newVisited.contains(oneE)==false){
			return pathExists(newVisited,x1+1,y1,x2,y2);
		}
		if(x1>0 && canMove(x1,y1,"S") && newVisited.contains(oneS)==false){
			return pathExists(newVisited,x1,y1-1,x2,y2);
		}
		if(y1>0 && canMove(x1,y1,"W") && newVisited.contains(oneW)==false){
			return pathExists(newVisited,x1+1,y1,x2,y2);
		}		
		//If there's nowhere left to check and you haven't made it to the {x2,y2}
		return false;
	}
	public static void testPathExists(int startX, int startY, int endX, int endY){
            
            Labyrinth labyrinthTest = new Labyrinth(true);
            
              for (int i =0; i<7; i++){
                    for (int j =0; j<7; j++){
                         // An x-piece is only for testing but all paths are
                         // true so a path always exists for every combination
                        labyrinthTest.board[i][j]=new Piece("X",0,"A",j,i);
                    }
        }
		
                ArrayList<int[]> visited = new ArrayList();
                System.out.println(labyrinthTest.pathExists(visited,startX,startY,endX,endY));
	}
	
	
	private boolean checkForTreasure(Player player){
		//checks if the piece the player moved to has the treasure they need
		int[] position = player.getLocation();
		int x = position[0];
		int y = position[1];
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
        
	
	private void playGame(){
		while(gameOver == false){
			nextTurn();
			Player player = getWhoseTurn();
                        
                        System.out.println("Spare= " +spare.type+"-"+spare.orientation);
                        
			//get user input on where to put in their spare piece
			int[] location = gui.gg.getInsertLocation();
//                        int[] guiLoc = gui.convertToGuiLoc(location);

			insertPiece(location);
                        
                        //check if the play shifts with the board
			
			shiftPlayerLocation(player1,location);
			shiftPlayerLocation(player2,location);
			
			//NEED TO UPDATE THE gui HERE BECAUSE THE BOARD SHIFTED
			gui.gg.displayBoard(board);
			//get where the player wants to move and where they are
			int hereX = player.getLocation()[0];
			int hereY = player.getLocation()[1];
			int[] moveTo = gui.gg.wantToMoveHere();
			int moveX = moveTo[0];
			int moveY = moveTo[1];
			
			//some sort of recursive canMove in pathExists
			ArrayList<int[]> visited = new ArrayList();
			while(pathExists(visited,hereX,hereY,moveX,moveY)==false){
				//tell user to try a new spot
				moveTo = gui.gg.wantToMoveHere();
				moveX = moveTo[0];
				moveY = moveTo[1];
			}
			
                        
                        
			//if path exists move the player to the piece
			player.updateLocation(moveX,moveY);
			
			//then check for treasure for that player
			if(checkForTreasure(player)==true){
				System.out.println("You found treasure");
				player.flipCard();
			}
			
			//NEED TO UPDATE THE gui HERE BECAUSE THE PLAYER MOVED
			
			//lastly check if the game is over
			isTheGameOver();
			//just to go through one round when debug is set to true so while loop can end
			if(debug==true){
				gameOver=true;
			}
		}
	}
	

//--------------------------- MAIN METHOD ----------------------------------------------------
	
	public static void main(String[] args) {
		
		//testPathExists(0,2,4,0); //Function to test path exists 
                
		labyrinth = new Labyrinth();
		                
		if(labyrinth.debug == true){
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
	
                }
        }
}
