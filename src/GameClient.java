import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/*
*  Threading is a class that implements Runnable which gives funcntions for threads to call and allows threading to be used
*  to allow multiple actions to happen simultaneously.
*  It has 6 class variables which are host, stub, serverName, playersTurn, board, and character.
*  @var host - holds the string necessary to initiate a GameInterface stub so that the server can be contacted
*  @var stub - is the GameInterface object that allows us to use the methods of the server that we are connecting through using
*  java rmi.
*  @var serverName - is a string that holds the name of the server that the client is in contact with using java rmi
*  @var playerTurn - is a boolean that tracks whether or not it is the clients turn to access the board, if it is true then 
*  it is this client's turn, if it is false then it isn't this client's turn
*  @var board = is an array of array of string that tracks what is on the board
*  @var character - holds the character that represents the client on the board, the choices are either X or O
*/

class threading implements Runnable
{
	String host;
	GameInterface stub;
	String serverName;
	boolean playersTurn;
	String [][] board = new String [3][3];
	String character;
	int threadID;
	
	/*
	*  The constructor for the threads
	*  @param host - holds the string for the host portion that is necessary for connecting to servers, it is mainly
	*  used in method calls to the server becasue this client's server must contact the other client's server and the 
	*  client's server does it by creating a stub to connect to the other server it the methods that the client calls
	*  @param stub - contains the stub that was initially created when the client starts up and will be used to contact the 
	*  server by one of the threads
	*  @param serverName - is the serverName of the server that this client will connect to
	*  @param turn - is for the initial start of the turn and dictates who will go first, if true then this client goes first
	*/
	
	public threading(String host, GameInterface stub, String serverName, boolean turn, int id)
	{
		this.host = host;
		this.stub = stub;
		this.serverName = serverName;
		this.playersTurn = turn;
		this.threadID = id;
		this.board[0][0] = "1";
		this.board[0][1] = "2";
		this.board[0][2] = "3";
		this.board[1][0] = "4";
		this.board[1][1] = "5";
		this.board[1][2] = "6";
		this.board[2][0] = "7";
		this.board[2][1] = "8";
		this.board[2][2] = "9";
		if(serverName.equals("Game"))
		{
			this.character = "X";
		}
		else
		{
			this.character = "O";
		}
	}
	/*
	*  This is a helper method that checks whether the game is over or not, there are 8 possible sequences that lead to 
	*  the game ending 
	*/
	private boolean checkEndOfGame()
	{
		//This is for line1 horizontal all matching, if so game will end
		
		if(this.board[0][0].equals(this.board[0][1]) && this.board[0][0].equals(this.board[0][2]))
		{
			if(this.board[0][0].equals("X"))
			{
				System.out.println("Player1 has won!!!");
			}
			else
			{
				System.out.println("Player2 has won!!!");
			}
			return true;
		}
		
		//This is for line2 horizontal all matching, if so game will end
		
		else if(this.board[1][0].equals(this.board[1][1]) && this.board[1][0].equals(this.board[1][2]))
		{
			if(this.board[1][0].equals("X"))
			{
				System.out.println("Player1 has won!!!");
			}
			else
			{
				System.out.println("Player2 has won!!!");
			}
			return true;
		}
		
		//This is for line3 horizontal all matching, if so game will end
		
		else if(this.board[2][0].equals(this.board[2][1]) && this.board[2][0].equals(this.board[2][2]))
		{
			if(this.board[2][0].equals("X"))
			{
				System.out.println("Player1 has won!!!");
			}
			else
			{
				System.out.println("Player2 has won!!!");
			}
			return true;
		}
		
		//This is for line1 vertical all matching, if so game will end
		
		else if(this.board[0][0].equals(this.board[1][0]) && this.board[0][0].equals(this.board[2][0]))
		{
			if(this.board[2][0].equals("X"))
			{
				System.out.println("Player1 has won!!!");
			}
			else
			{
				System.out.println("Player2 has won!!!");
			}
			return true;
		}
		
		//This is for line2 vertical all matching, if so game will end
		
		else if(this.board[0][1].equals(this.board[1][1]) && this.board[0][1].equals(this.board[2][1]))
		{
			if(this.board[0][1].equals("X"))
			{
				System.out.println("Player1 has won!!!");
			}
			else
			{
				System.out.println("Player2 has won!!!");
			}
			return true;
		}
		//This is for line3 vertical all matching, if so game will end
		
		else if(this.board[0][2].equals(this.board[1][2]) && this.board[0][2].equals(this.board[2][2]))
		{
			if(this.board[0][2].equals("X"))
			{
				System.out.println("Player1 has won!!!");
			}
			else
			{
				System.out.println("Player2 has won!!!");
			}
			return true;
		}
		//This is for the diagonal bottom left to top right
		
		else if(this.board[2][0].equals(this.board[1][1]) && this.board[2][0].equals(this.board[0][2]))
		{
			if(this.board[0][2].equals("X"))
			{
				System.out.println("Player1 has won!!!");
			}
			else
			{
				System.out.println("Player2 has won!!!");
			}
			return true;
		}
		
		//This is for the diagonal top left to bottom right
		
		else if(this.board[0][0].equals(this.board[1][1]) && this.board[0][0].equals(this.board[2][2]))
		{
			if(this.board[0][0].equals("X"))
			{
				System.out.println("Player1 has won!!!");
			}
			else
			{
				System.out.println("Player2 has won!!!");
			}
			return true;
		}
		//If the game is a draw and board is all full so no one can take a turn
		else if(this.board[0][0].equals("1") == false && this.board[0][1].equals("2") == false && this.board[0][2].equals("3") == false && this.board[1][0].equals("4") == false && this.board[1][1].equals("5") == false
				&& this.board[1][2].equals("6") == false && this.board[2][0].equals("7") == false && this.board[2][1].equals("8") == false && this.board[2][2].equals("9") == false)
		{
			System.out.println("Game is a draw there is no winner!");
			return true;
		}
		//If there isn't a winner decided this will happen
		else
		{
			//System.out.println("It's your move Yugi-Boy!");
			return false;
		}
	}
	
	/*
	*  This is a helper method to change the board and input the players character into the proper position
	*  if the user input an invalid position or the user input a position that has already been claimed
	*  then the system will inform them of the mistake and will ask the user to put in another position
	*  the function then will recursively call itself and attempt to put the character into the position the user
	*  identified again informing them if the position is filled or non existant.
	* 
	*  @param placementOnBoard = a String that is supposed to be a number 1 through 9 to indicate which position
	*  on the board the player would like to put their character and the user cannot input spaces with it, it just needs to
	*  be the single integer
	*/
	
	private void changeBoard(String placementOnBoard)
	{
		Scanner in = new Scanner(System.in);
		String input;
		/*
		*  This is what to do if the user inputs the number 1
		*/
		if(placementOnBoard.equals("1"))
		{
			if(this.board[0][0].equals("1"))
				this.board[0][0] = this.character;
			else
			{
				System.out.println("POSITION IS FILLED!!!! PLEASE CHOOSE ANOTHER POSITION!!!");
				input = in.nextLine();
				this.changeBoard(input);
			}
				
		}
		/*
		*  This is what to do if the user inputs the number 2
		*/
		else if(placementOnBoard.equals("2"))
		{
			if(this.board[0][1].equals("2"))
				this.board[0][1] = this.character;
			else
			{
				System.out.println("POSITION IS FILLED!!!! PLEASE CHOOSE ANOTHER POSITION!!!");
				input = in.nextLine();
				this.changeBoard(input);
			}
		}
		/*
		*  This is what to do if the user inputs the number 3
		*/
		else if(placementOnBoard.equals("3"))
		{
			if(this.board[0][2].equals("3"))
				this.board[0][2] = this.character;
			else
			{
				System.out.println("POSITION IS FILLED!!!! PLEASE CHOOSE ANOTHER POSITION!!!");
				input = in.nextLine();
				this.changeBoard(input);
			}
		}
		/*
		*  This is what to do if the user inputs the number 4
		*/
		else if(placementOnBoard.equals("4"))
		{
			if(this.board[1][0].equals("4"))
				this.board[1][0] = this.character;
			else
			{
				System.out.println("POSITION IS FILLED!!!! PLEASE CHOOSE ANOTHER POSITION!!!");
				input = in.nextLine();
				this.changeBoard(input);
			}
		}
		/*
		*  This is what to do if the user inputs the number 5
		*/
		else if(placementOnBoard.equals("5"))
		{
			if(this.board[1][1].equals("5"))
				this.board[1][1] = this.character;
			else
			{
				System.out.println("POSITION IS FILLED!!!! PLEASE CHOOSE ANOTHER POSITION!!!");
				input = in.nextLine();
				this.changeBoard(input);
			}
		}
		/*
		*  This is what to do if the user inputs the number 6
		*/
		else if(placementOnBoard.equals("6"))
		{
			if(this.board[1][2].equals("6"))
				this.board[1][2] = this.character;
			else
			{
				System.out.println("POSITION IS FILLED!!!! PLEASE CHOOSE ANOTHER POSITION!!!");
				input = in.nextLine();
				this.changeBoard(input);
			}
		}
		/*
		*  This is what to do if the user inputs the number 7
		*/
		else if(placementOnBoard.equals("7"))
		{
			if(this.board[2][0].equals("7"))
				this.board[2][0] = this.character;
			else
			{
				System.out.println("POSITION IS FILLED!!!! PLEASE CHOOSE ANOTHER POSITION!!!");
				input = in.nextLine();
				this.changeBoard(input);
			}
		}
		/*
		*  This is what to do if the user inputs the number 8
		*/
		else if(placementOnBoard.equals("8"))
		{
			if(this.board[2][1].equals("8"))
				this.board[2][1] = this.character;
			else
			{
				System.out.println("POSITION IS FILLED!!!! PLEASE CHOOSE ANOTHER POSITION!!!");
				input = in.nextLine();
				this.changeBoard(input);
			}
		}
		/*
		*  This is what to do if the user inputs the number 9
		*/
		else if(placementOnBoard.equals("9"))
		{
			if(this.board[2][2].equals("9"))
				this.board[2][2] = this.character;
			else
			{
				System.out.println("POSITION IS FILLED!!!! PLEASE CHOOSE ANOTHER POSITION!!!");
				input = in.nextLine();
				this.changeBoard(input);
			}
		}
		/*
		*  This is what to do if the user decides to input a value that isn't 1-9
		*/
		else
		{
			System.out.println("PLEASE INPUT AN ACTUAL POSITION ON THE BOARD!!!!!!!!");
			input = in.nextLine();
			this.changeBoard(input);
		}
	}
	/*
	*  This is the overwritten method for extending runnable.  This method is the method that is called by threads and
	*  is the only method that is directly called by the threads.  
	*/
	public void run()
	{
		boolean gameOn = true;
		/*
		 *  This if statement is the thread that will take care of the chat and inputing the players choice for the spot
		 *  on the board.
		 */
		if(this.threadID == 0)
		{
			while(gameOn)
			{
				Scanner scanner = new Scanner(System.in);
				String userInput = scanner.nextLine();
				if(userInput.equals("1") || userInput.equals("2") || userInput.equals("3") || userInput.equals("4") || userInput.equals("5") || userInput.equals("6") || userInput.equals("7") || userInput.equals("8") || userInput.equals("9"))
				{
					if(this.playersTurn)
					{
						this.changeBoard(userInput);
						try {
							this.stub.addToBoard(this.board, this.serverName, this.host, true);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						this.playersTurn = false;
						if(this.checkEndOfGame())
							gameOn = false;
						
						try {
							this.stub.passTurn(this.serverName, this.host, true);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else
					{
						System.out.println("IT IS NOT YOUR TURN!!!! YOU NEED TO BELIEVE IN THE HEART OF THE CARDS!!!!!");
					}
				}
				else
				{
					try {
						try {
							this.stub.makeComment(userInput, this.serverName, this.host, true);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (NotBoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		/*
		 * This else statement is the thread that will constantly check to see if it is this players turn,
		 */
		
		else
		{
			Registry registry = null;
			try {
				registry = LocateRegistry.getRegistry(host, 12250);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			GameInterface stub2 = null;
			
			try {
				stub2 = (GameInterface) registry.lookup(this.serverName);
			} catch (AccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(gameOn == true)
			{
				try {
					String str = stub2.readChat();
					if(str.equals(null) == false)
					System.out.println(str);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//This is so the thread doesn't just immediately start checking the other server for turns,
				//it'll wait a little between each successive check to see if it's this players turn or not.
				
				
				for(int i = 0; i < 500; i++)
				{
					
				}
				if(this.playersTurn == false)
				{
					if(this.serverName.equals("Game"))
					{
						try {
							this.playersTurn = stub2.checkTurn("Game2", this.host, true);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else
					{
						try {
							this.playersTurn = stub2.checkTurn("Game", this.host, true);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					}
					if(this.playersTurn)
					{
						try {
							this.board = stub2.updateBoard(this.serverName, this.host, true);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						for(int i = 0; i < 3; i++)
						{
							System.out.println(this.board [i][0] + " | "  + this.board[i][1] + " | "  + this.board[i][2]);
							System.out.println("_________");
						}
					}
					if(this.checkEndOfGame())
					{
						gameOn = false;
					}
				}
			}
		}
	}
}

public class GameClient {
	private GameClient(){}
	
	public static void main(String[] args) {
		String host = (args.length < 1) ? null : args[0];
		try{
			String [] [] matrix = new  String [3][3];
			int position = 1;
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 3; j++)
				{
					matrix [i][j] = Integer.toString(position);
					position++;
				}
			}
			
			Registry registry = LocateRegistry.getRegistry(host, 12250);
			String serverName = "Game";
			GameInterface stub = (GameInterface) registry.lookup("Game");
			if(stub.attachToServer().equals("Game") == false)
			{
				serverName = "Game2";
				stub = (GameInterface) registry.lookup("Game2");
			}

			for(int i = 0; i < 3; i++)
			{
				System.out.println(matrix [i][0] + " | "  + matrix[i][1] + " | "  + matrix[i][2]);
				System.out.println("_________");
			}
			boolean playersTurn = false;
			for(int i = 0; i < 2; i++)
			{
				if(serverName.equals("Game"))
				{
					playersTurn = true;
				}
				//Runnable object = new threading(host, stub, serverName);
				Thread t = new Thread(new threading(host, stub, serverName, playersTurn, i));
				t.start();
			}
		}
		
		catch (Exception e){
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
