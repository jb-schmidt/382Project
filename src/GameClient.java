import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

class threading implements Runnable
{
	String host;
	GameInterface stub;
	String serverName;
	boolean playersTurn;
	String [][] board = new String [3][3];
	String character;
	
	public threading(String host, GameInterface stub, String serverName, boolean turn)
	{
		this.host = host;
		this.stub = stub;
		this.serverName = serverName;
		this.playersTurn = turn;
		if(serverName.equals("Game"))
		{
			this.character = "X";
		}
		else
		{
			this.character = "O";
		}
	}
	
	//This is a helper method that checks whether the game is over or not
	
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
		//If there isn't a winner decided this will happen
		else
		{
			System.out.println("It's your move Yugi-Boy!");
			return false;
		}
	}
	
	//This is a helper method to change the board and input the players character into the proper position
	
	private void changeBoard(String placementOnBoard)
	{
		if(placementOnBoard.equals("1"))
			this.board[0][0] = this.character;
		else if(placementOnBoard.equals("2"))
			this.board[0][1] = this.character;
		else if(placementOnBoard.equals("3"))
			this.board[0][2] = this.character;
		else if(placementOnBoard.equals("4"))
			this.board[1][0] = this.character;
		else if(placementOnBoard.equals("5"))
			this.board[1][1] = this.character;
		else if(placementOnBoard.equals("6"))
			this.board[1][2] = this.character;
		else if(placementOnBoard.equals("7"))
			this.board[2][0] = this.character;
		else if(placementOnBoard.equals("8"))
			this.board[2][1] = this.character;
		else 
			this.board[2][2] = this.character;
	}
	
	
	
	public void run()
	{
		boolean gameOn = true;
		/*
		 * This if statement is the thread that will take care of the chat and inputing the players choice for the spot.
		 */
		if(Thread.currentThread().getId() == 0)
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
						this.stub.addToBoard(this.board, this.serverName, this.host, true);
						this.playersTurn = false;
						if(this.checkEndOfGame())
							gameOn = false;
						
						this.stub.passTurn(this.serverName, this.host, true);
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
				for(int i = 0; i < 500; i++)
				{
					
				}
				if(this.playersTurn == false)
				{
					if(this.serverName.equals("Game"))
					{
						this.playersTurn = stub2.checkTurn("Game2", this.host, true);
					}
					else
					{
						this.playersTurn = stub2.checkTurn("Game", this.host, true);
						
					}
					if(this.playersTurn)
					{
						this.board = stub2.updateBoard(this.serverName, this.host, true);
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
				Thread t = new Thread(new threading(host, stub, serverName, playersTurn));
				t.start();
			}
		}
		
		catch (Exception e){
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
