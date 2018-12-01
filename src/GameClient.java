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
	
	public void run()
	{
		boolean gameOn = true;
		/*
		 * This if statement is the thread that will take care of the chat and inputing the players choice for the spot.
		 */
		if(Thread.currentThread().getId() == 0)
		{
			Scanner scanner = new Scanner(System.in);
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
						this.playersTurn = stub2.checkTurn("Game2");
					}
					else
					{
						this.playersTurn = stub2.checkTurn("Game");
						
					}
					if(this.playersTurn)
					{
						this.board = stub2.updateBoard(this.serverName);
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
