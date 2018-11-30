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
	
	public threading(String host, GameInterface stub, String serverName, boolean turn)
	{
		this.host = host;
		this.stub = stub;
		this.serverName = serverName;
		this.playersTurn = turn;
	}
	public void run()
	{
		boolean gameOn = true;
		
		if(Thread.currentThread().getId() == 0)
		{
			
		}
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
			//String givenIP = args[0];
			//Registry registry = LocateRegistry.getRegistry(host, Integer.parseInt(givenIP));
			Scanner scanner = new Scanner(System.in);
			Registry registry = LocateRegistry.getRegistry(host, 12250);
			String serverName = "Game";
			GameInterface stub = (GameInterface) registry.lookup("Game");
			if(stub.attachToServer().equals("Game") == false)
			{
				serverName = "Game2";
				stub = (GameInterface) registry.lookup("Game2");
			}
			//System.out.println("Server connected");
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
