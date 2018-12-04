
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class GameServer implements GameInterface{
	
	public GameInterface connectToOtherServer(String serverName, String host) throws RemoteException, NotBoundException
	{
		Registry registry = LocateRegistry.getRegistry(host, 12250);
		GameInterface stub = (GameInterface) registry.lookup(serverName);
		return stub;
	}
	
	public void makeComment(String comment, String serverName, String host, boolean differentiatesServers) throws NotBoundException, IOException, RemoteException
	{
		if(differentiatesServers == true)
		{
			String string = "";
			if(serverName.equals("Game"))
				string = "Game2";
			else
				string = "Game";
			GameInterface stub = this.connectToOtherServer(string, host);
			stub.makeComment(comment, serverName, host, false);
		}
		else
		{
			//BufferedWriter out = new BufferedWriter(new FileWriter("Chat", true));
			//out.write(comment);
			//out.close();
			PrintWriter pw = null;
			BufferedWriter bw = null;
			FileWriter fw = null;
			
			try{
				fw = new FileWriter("Chat");
				bw = new BufferedWriter(fw);
				bw.write(comment);
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * passTurn will update the file associated with the other players server and itself, this is important because then the checkTurn method only needs
	 * to check the file associated with itself
	 * (non-Javadoc)
	 * @see GameInterface#passTurn(java.lang.String, java.lang.String, boolean)
	 */
	
	public void passTurn(String serverName, String host, boolean differentiatesServers) throws RemoteException
	{
		if(differentiatesServers == true)
		{//Turn tracker false
			String string = "";
			PrintWriter pw = null; 
			BufferedWriter bw = null;
			FileWriter fw = null;

			try {
				fw = new FileWriter("TurnTracker");
				bw = new BufferedWriter(fw);
				bw.write("False");
				bw.close();
				fw.close();

			} catch (IOException e) {

				e.printStackTrace();

			}
			if(serverName.equals("Game"))
				string = "Game2";
			else
				string = "Game";
			
			GameInterface stub = null;
			try {
				stub = this.connectToOtherServer(string, host);
				stub.passTurn(serverName, host, false);
			} catch (RemoteException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			BufferedWriter bw = null;
			FileWriter fw = null;

			try {
				fw = new FileWriter("TurnTracker");
				bw = new BufferedWriter(fw);
				bw.write("True");
				bw.close();
				fw.close();

			} catch (IOException e) {

				e.printStackTrace();

			}
		}
	}
	
	/*
	 * The addToBoard will update the board of the other server that the other client is connected to, it does not need to update itself because 
	 * updateBoard only occurs when the change of players occurs and update board will only look at the file that is associated with its own board.
	 * (non-Javadoc)
	 * @see GameInterface#addToBoard(java.lang.String[][], java.lang.String, java.lang.String, boolean)
	 */
	
	public void addToBoard(String [] [] str, String serverName, String host, boolean differentiatesServers) throws RemoteException
	{
		if(differentiatesServers == true)
		{
			String string = "";
			if(serverName.equals("Game"))
				string = "Game2";
			else
				string = "Game";
			GameInterface stub = null;
			try {
				stub = this.connectToOtherServer(string, host);
				stub.addToBoard(str, serverName, host, false);
			} catch (RemoteException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{//Board
			//str[0][0] + str[0][1] + str[0][2] + str[1][0] + str[1][1] + str[1][2] + str[2][0] + str[2][1] + str[2][2]
			BufferedWriter bw = null;
			FileWriter fw = null;

			try {
				fw = new FileWriter("Board");
				bw = new BufferedWriter(fw);
				bw.write(str[0][0] + str[0][1] + str[0][2] + str[1][0] + str[1][1] + str[1][2] + str[2][0] + str[2][1] + str[2][2]);
				bw.close();
				fw.close();

			} catch (IOException e) {

				e.printStackTrace();

			}
		}
	}
	
	/*
	 * UpdateBoard only needs to check the board associated with itself because whenever the other player makes their move they update the board of the other players file.  So the file
	 * associated with its own server will be correct
	 * (non-Javadoc)
	 * @see GameInterface#updateBoard(java.lang.String, java.lang.String, boolean)
	 */
	
	public String [] [] updateBoard(String serverName, String host, boolean differentiatesServers)throws IOException
	{//Board
		File file = new File("Board");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String boardState = br.readLine();
		br.close();
		String [] [] str = new String [3] [3];
		str[0][0] = String.valueOf(boardState.charAt(0));
		str[0][1] = String.valueOf(boardState.charAt(1));
		str[0][2] = String.valueOf(boardState.charAt(2));
		str[1][0] = String.valueOf(boardState.charAt(3));
		str[1][1] = String.valueOf(boardState.charAt(4));
		str[1][2] = String.valueOf(boardState.charAt(5));
		str[2][0] = String.valueOf(boardState.charAt(6));
		str[2][1] = String.valueOf(boardState.charAt(7));
		str[2][2] = String.valueOf(boardState.charAt(8));
		return str;
	}
	
	/*
	 * checkTurn only needs to check the file associated with itself, this is because the passTurn method updates the file associated with the other
	 * Server and Client's file not its own
	 */
	
	public boolean checkTurn(String serverName, String host, boolean differentiatesServers)throws IOException
	{
		File file = new File("TurnTracker");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String turn = br.readLine();
		br.close();
		if(br.equals("True"))
		   return true;
		else
			return false;
	}
	
	public String attachToServer() throws IOException, RemoteException
	{
		File file = new File("ClientStartup");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String ClientServerToAttach = br.readLine();
		br.close();
		//if(ClientServerToAttach.equals("Game"))
		//{
		//	PrintWriter pw = new PrintWriter("ClientStartup");
		//	pw.print("");
		//	pw.print("Game2");
		//	pw.close();
		//}
		return ClientServerToAttach;
	}
	
	@Override
	public String readChat() throws IOException {
		File file = new File("Chat");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str = br.readLine();
		br.close();
		return str;
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
			registry.bind(serverName, stub);
			System.err.println("Server ready");
		}

		catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

	
}
