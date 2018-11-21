import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Game.java
 * Used for Tic-Tac-Toe game
 * @author jbschmi3
 *
 */
public class Game extends UnicastRemoteObject implements GameService {	
	
	private int currentPlayer = 0; //next player
	private int turns = 0;  //number of turns played

	protected Game() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Compiler computed serial
	 */
	private static final long serialVersionUID = -3260397192178428394L;
	
	/**
	 * Construction of the Game Board
	 * Each number is a reference number for the space that a client will select
	 */
	private char board[][] = {
			{'1', '2', '3' },
			{'4', '5', '6' },
			{'7', '8', '9' }
	};
	
	/**
	 * Build the current board out to the terminal
	 * @return String of current game board using StringBuilder
	 */
	public String boardOutput() throws RemoteException {
		StringBuilder sb = new StringBuilder();
		//get lock
		synchronized (this) {
		//add board elements to string
			sb.append("\nMake your selection:\n");
			sb.append(board[0][0]).append(" | ");  //represents first tile from board array
			sb.append(board[0][1]).append(" | ");
			sb.append(board[0][2]).append(" | ");
			sb.append("\n------------\n");
			sb.append(board[1][0]).append(" | ");
			sb.append(board[1][1]).append(" | ");
			sb.append(board[1][2]).append(" | ");
			sb.append("\n------------\n");
			sb.append(board[2][0]).append(" | ");
			sb.append(board[2][1]).append(" | ");
			sb.append(board[2][2]).append(" | ");
		}//release lock
		//return the board
		return sb.toString();
	}
	
	/**
	 * Make plays on the board
	 */
	public boolean move(int move, int player) throws RemoteException {		
		//get lock
		synchronized (this) {
			//check for invalid move
			if(move > 9) 
				return false;
			//check for player turn
			if(player != currentPlayer)
				return false;
			//only 9 total plays in a game of Tic-Tac-Toe
			if(turns == 9)
				return false;
			
			//otherwise do the turn and insert X/O
			//find row and column for the board from the player move
			int row = 0, col = 0;
			switch(move)  {
				case 1: row = 0; col = 0;
				case 2: row = 0; col = 1;
				case 3: row = 0; col = 2;
				case 4: row = 1; col = 0;
				case 5: row = 1; col = 1;
				case 6: row = 1; col = 2;
				case 7: row = 2; col = 0;
				case 8: row = 2; col = 1;
				case 9: row = 2; col = 2;
			}			
			//if player 1 put 'X', else 'O'
			if(player == 1) {
				board[row][col] = 'X';
			} else {
				board[row][col] = 'O';
			}
			//set current player
			currentPlayer = (currentPlayer + 1) % 2;
			//increase turn counter
			turns++;
			return true;				
		}//unlock				
	}
	
	/**
	 * Chat service
	 * @param message received
	 * @return String
	 */
	@Override
	public String chat(String message) throws RemoteException {
		// TODO Logic for chat
		return null;
	}
	
	/**
	 * Check for game end/win
	 */
	@Override
	public int gameOver() throws RemoteException {
		// TODO Check for game win/tie
		return 0;
	}
}
