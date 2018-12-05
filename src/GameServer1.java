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

/*
 * This is a servers class that the client contacts so the game can be played.
 * The server holds the chat, the board, the TurnTracker and the client constantly contacts
 * the server to update what is in these files, or to check what the values are that are
 * in these folders
 */
public class GameServer1 implements GameInterface{
	/*
	 * This function is the function is only called by the server
	 * so that it may connect to the other server in this game
	 * @param serverName is a string that is the serverName of the other server
	 * @param host is the string host that is necessecary to build a stub
	 * 
	 * @return returns a stub for the connection to the other server
	 */
	public GameInterface connectToOtherServer(String serverName, String host) throws RemoteException, NotBoundException
	{
		Registry registry = LocateRegistry.getRegistry(host, 12250);
		GameInterface stub = (GameInterface) registry.lookup(serverName);
		return stub;
	}
	
	/*
	 * Client calls this method to update the chat of the other player
	 * @param comment is the string that contains what needs to be updated for the other player, this is provided by the actual client
	 * @param serverName is the name of the Server that the client is currently contacting, not the server that needs to be connected to
	 * @param host is the string for the host that is necessary to build a stub to connect to the other server
	 * @param differentiatesServers is true if the client called this method and false if a server calls this method
	 */
	public void makeComment(String comment, String serverName, String host, boolean differentiatesServers) throws NotBoundException, IOException, RemoteException
	{
		
		String string = "";
		if(serverName.equals("Game"))
			string = "Game2";
		else
			string = "Game";
		GameInterface stub = this.connectToOtherServer(string, host);
		stub.writeToFile("Chat", comment);
	}
	
	/*
	 * The client can contact the server to connect to it
	 * THIS FUNCTION IS NOT USED
	 * 
	 * @return ClientServerToAttach is a string that holds the servers name
	 * @see GameInterface#attachToServer()
	 */
	public String attachToServer() throws IOException, RemoteException
	{
		File file = new File("ClientStartup");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String ClientServerToAttach = br.readLine();
		br.close();
		return ClientServerToAttach;
	}

	/*
	 * checkTurn is a function that is called by the Client to read the TurnTracker to figure out if it is now the client's turn
	 * 
	 * @param serverName is the name of the server that is being contacted
	 * @param host is the host of the server
	 * @param differentiatesServers is true if a client calls this method and false otherwise
	 */
	public boolean checkTurn(String serverName, String host, boolean differentiatesServers)
			throws RemoteException, FileNotFoundException, IOException {
		File file = new File("TurnTracker");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str = br.readLine();
		br.close();
		if(str.equals("True"))
			return true;
		else
			return false;
	}

	/*
	 * This is a function that reads the Board and updates the client's board that called this method
	 * the client contacts its server after the turn has passed 
	 * 
	 * @param serverName is the name of the server that is being contacted
	 * @param host is the host of the server
	 * @param differentiatesServers is true if a client calls this method and false otherwise
	 * @return str returns a string that holds the data of the board
	 */
	public String[][] updateBoard(String serverName, String host, boolean differentiatesServers)
			throws RemoteException, FileNotFoundException, IOException {
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
	 * This is a function to update the board after a player has made a move, it updates the board of the inactive player
	 * so when it is their turn they can make an appropriate move
	 * 
	 * @param serverName is the name of the server that is being contacted
	 * @param host is the host of the server
	 * @param differentiatesServers is true if a client calls this method and false otherwise
	 */
	public void addToBoard(String[][] str, String serverName, String host, boolean differentiatesServers)
			throws RemoteException, NotBoundException {
		String string = "";
		if(serverName.equals("Game"))
			string = "Game2";
		else
			string = "Game";
		GameInterface stub = this.connectToOtherServer(string, host);
		String comment = str[0][0] + str[0][1] + str[0][2] + str[1][0] + str[1][1] + str[1][2] + str[2][0] + str[2][1] + str[2][2];
		stub.writeToFile("Board", comment);
		
	}

	/*
	 * Is called by the client to pass the turn to the other client, this method will update the TurnTracker file of the other server
	 * that the other client is attached to
	 * 
	 * @param serverName is the name of the server that is being contacted
	 * @param host is the host of the server
	 * @param differentiatesServers is true if a client calls this method and false otherwise
	 */
	public void passTurn(String serverName, String host, boolean differentiatesServers) throws RemoteException, NotBoundException {
		
		String string = "";
		if(serverName.equals("Game"))
			string = "Game2";
		else
			string = "Game";
		GameInterface stub = this.connectToOtherServer(string, host);
		stub.writeToFile("TurnTracker", "True");
	}

	/*
	 * This function is called by the client to contact its server to check on its Chat file to see if any changes have been made
	 * 
	 * @return str This is the value that was read from the Chat file
	 */
	public String readChat() throws RemoteException, FileNotFoundException, IOException {
		File file = new File("Chat");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str = br.readLine();
		br.close();
		return str;
	}
	
	/*
	 * This is a server file that writes a given string to a given file, it is usually called by the other server to update
	 * one of the files that is necessary to play the game, i.e. the Chat, TurnTracker, Board
	 * 
	 * @param filename is the name of the file that is needed to be written to
	 * @param whatToWrite is what is needed to be written to said file
	 */
	public void writeToFile(String filename, String whatToWrite) throws RemoteException
	{
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			fw = new FileWriter(filename);
			bw = new BufferedWriter(fw);
			bw.write(whatToWrite);
			bw.close();
			fw.close();
			System.out.println(filename + " " + whatToWrite);

		} catch (IOException e) {

			e.printStackTrace();

		}
		
	}
	
	/*
	 * This function is called by the client to turn its own TurnTracker to false
	 * 
	 */
	public void changeTurnTrackerToFalse() throws RemoteException, NotBoundException
	{
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
	}
	
	/*
	 * This function is called by the client at the begining of the game so that
	 * the Chat, TurnTracker, and Board can all be set to the begining settings
	 * if it isn't called there can NullPointerExceptions
	 * 
	 */
	public void setToDefault() throws IOException {
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
		
		try {
			fw = new FileWriter("Chat");
			bw = new BufferedWriter(fw);
			bw.write("");
			bw.close();
			fw.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
			
		try {
			fw = new FileWriter("Board");
			bw = new BufferedWriter(fw);
			bw.write("123456789");
			bw.close();
			fw.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[])
	{
		try{
			//System.setSecurityManager(new RMISecurityManager());
			//set the security manager

			GameServer1 obj = new GameServer1();
			GameInterface stub = (GameInterface) UnicastRemoteObject.exportObject(obj, 0);
			//String givenIP = args[0];
			
			//Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry(12250);
			File file = new File("ServerStartup");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String serverName = br.readLine();
			br.close();
			System.out.println(serverName);
			registry.bind(serverName, stub);
			System.err.println("Server ready");
		}

		catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

	
}
