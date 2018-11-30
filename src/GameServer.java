
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class GameServer implements GameInterface{
	
	public void makeComment(String str)
	{
		
	}
	
	public String attachToServer() throws IOException
	{
		File file = new File("ClientStartup");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String ClientServerToAttach = br.readLine();
		br.close();
		if(ClientServerToAttach.equals("Game"))
		{
			PrintWriter pw = new PrintWriter("ClientStartup");
			pw.print("");
			pw.print("Game2");
			pw.close();
		}
		return ClientServerToAttach;
	}
	
	public static void main(String args[])
	{
		try{
			//System.setSecurityManager(new RMISecurityManager());
			//set the security manager

			GameServer obj = new GameServer();
			GameInterface stub = (GameInterface) UnicastRemoteObject.exportObject(obj, 0);
			//String givenIP = args[0];
			
			//Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry(12250);
			//Registry registry = LocateRegistry.getRegistry(Integer.parseInt(givenIP));
			File file = new File("ServerStartup");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String serverName = br.readLine();
			br.close();
			if(serverName.equals("Game"))
			{
				PrintWriter pw = new PrintWriter("ServerStartup");
				pw.print("");
				pw.print("Game2");
				pw.close();
				
			}

			registry.bind(serverName, stub);
			System.err.println("Server ready");
		}

		catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

}
