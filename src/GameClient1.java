import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	BooleanHolder playersTurn;
	StringMatrix board;
	String character;
	int threadID;
	BooleanHolder gameBeingPlayed;
	
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
	
	public threading(String host, GameInterface stub, String serverName, BooleanHolder turn, int id, StringMatrix board, BooleanHolder gameBeingPlayed)
	{
		this.host = host;
		this.stub = stub;
		this.serverName = serverName;
		playersTurn = turn;
		this.threadID = id;
		this.board = board;
		this.gameBeingPlayed = gameBeingPlayed;
		
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
		
		if(this.board.board[0][0].equals(this.board.board[0][1]) && this.board.board[0][0].equals(this.board.board[0][2]))
		{
			if(this.board.board[0][0].equals("X"))
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
		
		else if(this.board.board[1][0].equals(this.board.board[1][1]) && this.board.board[1][0].equals(this.board.board[1][2]))
		{
			if(this.board.board[1][0].equals("X"))
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
		
		else if(this.board.board[2][0].equals(this.board.board[2][1]) && this.board.board[2][0].equals(this.board.board[2][2]))
		{
			if(this.board.board[2][0].equals("X"))
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
		
		else if(this.board.board[0][0].equals(this.board.board[1][0]) && this.board.board[0][0].equals(this.board.board[2][0]))
		{
			if(this.board.board[2][0].equals("X"))
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
		
		else if(this.board.board[0][1].equals(this.board.board[1][1]) && this.board.board[0][1].equals(this.board.board[2][1]))
		{
			if(this.board.board[0][1].equals("X"))
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
		
		else if(this.board.board[0][2].equals(this.board.board[1][2]) && this.board.board[0][2].equals(this.board.board[2][2]))
		{
			if(this.board.board[0][2].equals("X"))
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
		
		else if(this.board.board[2][0].equals(this.board.board[1][1]) && this.board.board[2][0].equals(this.board.board[0][2]))
		{
			if(this.board.board[0][2].equals("X"))
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
		
		else if(this.board.board[0][0].equals(this.board.board[1][1]) && this.board.board[0][0].equals(this.board.board[2][2]))
		{
			if(this.board.board[0][0].equals("X"))
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
		else if(this.board.board[0][0].equals("1") == false && this.board.board[0][1].equals("2") == false && this.board.board[0][2].equals("3") == false && this.board.board[1][0].equals("4") == false && this.board.board[1][1].equals("5") == false
				&& this.board.board[1][2].equals("6") == false && this.board.board[2][0].equals("7") == false && this.board.board[2][1].equals("8") == false && this.board.board[2][2].equals("9") == false)
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
			if(this.board.board[0][0].equals("1"))
				this.board.board[0][0] = this.character;
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
			if(this.board.board[0][1].equals("2"))
				this.board.board[0][1] = this.character;
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
			if(this.board.board[0][2].equals("3"))
				this.board.board[0][2] = this.character;
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
			if(this.board.board[1][0].equals("4"))
				this.board.board[1][0] = this.character;
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
			if(this.board.board[1][1].equals("5"))
				this.board.board[1][1] = this.character;
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
			if(this.board.board[1][2].equals("6"))
				this.board.board[1][2] = this.character;
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
			if(this.board.board[2][0].equals("7"))
				this.board.board[2][0] = this.character;
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
			if(this.board.board[2][1].equals("8"))
				this.board.board[2][1] = this.character;
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
			if(this.board.board[2][2].equals("9"))
				this.board.board[2][2] = this.character;
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
	@Override
	public void run() {
		String commentLastRead = "";
		while(this.gameBeingPlayed.bool)
		{
			//This thread is in charge of user input from the system.  So it is in charge of updating the board for 
			//both players and is in charge of updating the chat and distributing the chat
			if(this.threadID == 1)
			{
				for(int i = 0; i < 10000; i++)
				{
					for(int j = 0; j < 10000; j++)
					{
						for(int q = 0; q < 10000; q++)
						{
							
						}
					}
				}
				Scanner scanner = new Scanner(System.in);
				String userInput = scanner.nextLine();
				if(userInput.equals("1") || userInput.equals("2") || userInput.equals("3") || userInput.equals("4") || userInput.equals("5") || userInput.equals("6") || userInput.equals("7") || userInput.equals("8") || userInput.equals("9"))
				{
					if(this.playersTurn.bool)
					{
						//Updates this client's personal board
						this.changeBoard(userInput);
						//Updates the board on the opposing players side 
						try {
							this.stub.addToBoard(this.board.board, this.serverName, this.host, true);
						} 
						catch (RemoteException e) {
							e.printStackTrace();
						} 
						catch (NotBoundException e) {
							e.printStackTrace();
						}
						
						//Checks to see if the game is over after the last move
						if(this.checkEndOfGame())
						{
							this.gameBeingPlayed.bool = false;
						}
						//Makes this player the non-active player
						this.playersTurn.bool = false;
						
						//makes this clients TurnTracker file state False
						try {
							this.stub.changeTurnTrackerToFalse();
						} catch (RemoteException e) {

							e.printStackTrace();
						} catch (NotBoundException e) {

							e.printStackTrace();
						}
						//passes the turn to the other client by making their TurnTracker
						//file state True
						try {
							this.stub.passTurn(this.serverName, this.host, true);
						} catch (RemoteException e) {

							e.printStackTrace();
						} catch (NotBoundException e) {

							e.printStackTrace();
						}
					}
					else
					{
						//If 1-9 are inputed but it isn't your turn you will get this
						//message
						System.out.println("IT IS NOT YOUR TURN");
					}
				}
				else
				{
					//This branch of try catch writes the comments from this client
					//to the chat of the other client the only input it won't take is 1-9
					try {
						this.stub.makeComment(userInput, this.serverName, this.host, true);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NotBoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
			//This thread is in charge of keeping up to date with the chat and checks on it regularly and prints it 
			//to the user when it has changed
			if(this.threadID == 2)
			{
				//gives the thread time between reading the chat file
				for(int i = 0; i < 10000; i++)
				{
					for(int j = 0; j < 10000; j++)
					{
						for(int q = 0; q < 10000; q++)
						{
							
						}
					}
				}
				//Reads the chat, it will compare what was read this time with what was read last time
				//and if they are different it will print it to the screen
				String currentlyRead = "";
				try {
					 currentlyRead = this.stub.readChat();
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
				//checks to make sure an actual update happend and if it has it prints it to user
				//if and only if the chat file wasn't completely empty becuase if it was it will be null
				
				if(currentlyRead != null && commentLastRead != null)
				{
					if(currentlyRead.equals(commentLastRead) == false)
					{
						System.out.println(currentlyRead);
					}
				}
				commentLastRead = currentlyRead;
			}
			
			//This thread is in charge of keeping up with the board and making sure it stays up to date.
			if(this.threadID == 3)
			{
				for(int i = 0; i < 10000; i++)
				{
					for(int j = 0; j < 10000; j++)
					{
						for(int q = 0; q < 10000; q++)
						{
							
						}
					}
				}
				
				//checks whether or not it should check to see if it is this players turn, and will do the check if it isn't this players turn
				if(this.playersTurn.bool == false)
				{
					try {
						this.playersTurn.bool = this.stub.checkTurn(this.serverName, this.host, true);
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
					
					//If it is the players turn after the check it will update the board becuase it means the other player
					//placed their move
					if(this.playersTurn.bool == true)
					{
						try {
							this.board.board = this.stub.updateBoard(this.serverName, this.host, true);
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
						//This is the sequence to print out the board to the screen so the player can see it before they place their move
						System.out.println(this.board.board [0][0] + " | "  + this.board.board[0][1] + " | "  + this.board.board[0][2]);
						System.out.println("_________");
						System.out.println(this.board.board [1][0] + " | "  + this.board.board[1][1] + " | "  + this.board.board[1][2]);
						System.out.println("_________");
						System.out.println(this.board.board [2][0] + " | "  + this.board.board[2][1] + " | "  + this.board.board[2][2]);
						System.out.println("_________");
						if(this.checkEndOfGame())
						{
							this.gameBeingPlayed.bool = false;
						}
					}
				}
			}
		}
	}
}

//this is a class i developed to maintain the players turn. I used an object because all the threads need access
//to the same data
class BooleanHolder {
	boolean bool;
	
	public BooleanHolder(boolean bool)
	{
		this.bool = bool;
	}
}

//This is the same as above but for the board
class StringMatrix {
	String [] [] board;
	
	public StringMatrix()
	{
		board = new String [3][3];
		this.board[0][0] = "1";
		this.board[0][1] = "2";
		this.board[0][2] = "3";
		this.board[1][0] = "4";
		this.board[1][1] = "5";
		this.board[1][2] = "6";
		this.board[2][0] = "7";
		this.board[2][1] = "8";
		this.board[2][2] = "9";
	}
}

public class GameClient1 {
private GameClient1(){}

	public static void main(String[] args) throws NotBoundException {
		//String host = (args.length < 1) ? null : args[0];
		String host = "localhost";
		try{
			StringMatrix board = new StringMatrix();
			
			Registry registry = LocateRegistry.getRegistry(host, 12250);

			File file = new File("ClientStartup");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String serverName = br.readLine();
			br.close();
			
			BooleanHolder playersTurn = new BooleanHolder(false);
			
			if(serverName.equals("Game"))
			{
				//thisPlayersTurn = true;
				playersTurn.bool = true;
			}
			
			GameInterface stub1 = (GameInterface) registry.lookup(serverName);
			GameInterface stub2 = (GameInterface) registry.lookup(serverName);
			GameInterface stub3 = (GameInterface) registry.lookup(serverName);
			
			for(int i = 0; i < 3; i++)
			{
				System.out.println(board.board [i][0] + " | "  + board.board[i][1] + " | "  + board.board[i][2]);
				System.out.println("_________");
			}
			
			BooleanHolder gameBeingPlayed = new BooleanHolder(true);
			
			Thread t1 = new Thread(new threading(host, stub1, serverName, playersTurn, 1, board, gameBeingPlayed));
			Thread t2 = new Thread(new threading(host, stub2, serverName, playersTurn, 2, board, gameBeingPlayed));
			Thread t3 = new Thread(new threading(host, stub3, serverName, playersTurn, 3, board, gameBeingPlayed));
			
			//This is to set all of the files to the default statuses that they need 
			//to be
			try {
				stub1.setToDefault();
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
			
			t1.start();
			t2.start();
			t3.start();
		}
			
		catch (Exception e){
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
