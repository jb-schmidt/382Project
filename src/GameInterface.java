import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * This is the interface that is used so that clients and or servers can call the methods
 * of the server through java RMI and can contact eachother through the network
 */
public interface GameInterface extends Remote{
	
	/*
	 * Client calls this method to update the chat of the other player
	 * @param comment is the string that contains what needs to be updated for the other player, this is provided by the actual client
	 * @param serverName is the name of the Server that the client is currently contacting, not the server that needs to be connected to
	 * @param host is the string for the host that is necessary to build a stub to connect to the other server
	 * @param differentiatesServers is true if the client called this method and false if a server calls this method
	 */
	public void makeComment(String comment, String serverName, String host, boolean differentiatesServers)throws RemoteException, NotBoundException, IOException;
	
	/*
	 * The client can contact the server to connect to it
	 * THIS FUNCTION IS NOT USED
	 * 
	 * @return ClientServerToAttach is a string that holds the servers name
	 * @see GameInterface#attachToServer()
	 */
	public String attachToServer() throws FileNotFoundException, IOException, RemoteException;
	
	/*
	 * checkTurn is a function that is called by the Client to read the TurnTracker to figure out if it is now the client's turn
	 * 
	 * @param serverName is the name of the server that is being contacted
	 * @param host is the host of the server
	 * @param differentiatesServers is true if a client calls this method and false otherwise
	 */
	public boolean checkTurn(String serverName, String host, boolean differentiatesServers) throws RemoteException, FileNotFoundException, IOException;
	
	/*
	 * This is a function that reads the Board and updates the client's board that called this method
	 * the client contacts its server after the turn has passed 
	 * 
	 * @param serverName is the name of the server that is being contacted
	 * @param host is the host of the server
	 * @param differentiatesServers is true if a client calls this method and false otherwise
	 * @return str returns a string that holds the data of the board
	 */
	public String [] [] updateBoard(String serverName, String host, boolean differentiatesServers) throws RemoteException, FileNotFoundException, IOException;
	
	/*
	 * This is a function to update the board after a player has made a move, it updates the board of the inactive player
	 * so when it is their turn they can make an appropriate move
	 * 
	 * @param serverName is the name of the server that is being contacted
	 * @param host is the host of the server
	 * @param differentiatesServers is true if a client calls this method and false otherwise
	 */
	public void addToBoard(String [] [] str, String serverName, String host, boolean differentiatesServers) throws RemoteException, NotBoundException;
	
	/*
	 * Is called by the client to pass the turn to the other client, this method will update the TurnTracker file of the other server
	 * that the other client is attached to
	 * 
	 * @param serverName is the name of the server that is being contacted
	 * @param host is the host of the server
	 * @param differentiatesServers is true if a client calls this method and false otherwise
	 */
	public void passTurn(String serverName, String host, boolean differentiatesServers) throws RemoteException, NotBoundException;
	
	/*
	 * This function is the function is only called by the server
	 * so that it may connect to the other server in this game
	 * @param serverName is a string that is the serverName of the other server
	 * @param host is the string host that is necessecary to build a stub
	 * 
	 * @return returns a stub for the connection to the other server
	 */
	public GameInterface connectToOtherServer(String serverName, String host) throws RemoteException, NotBoundException;
	
	/*
	 * This function is called by the client to contact its server to check on its Chat file to see if any changes have been made
	 * 
	 * @return str This is the value that was read from the Chat file
	 */
	public String readChat()throws RemoteException, FileNotFoundException, IOException;
	
	/*
	 * This is a server file that writes a given string to a given file, it is usually called by the other server to update
	 * one of the files that is necessary to play the game, i.e. the Chat, TurnTracker, Board
	 * 
	 * @param filename is the name of the file that is needed to be written to
	 * @param whatToWrite is what is needed to be written to said file
	 */
	public void writeToFile(String filename, String whatToWrite) throws RemoteException;
	
	/*
	 * This function is called by the client to turn its own TurnTracker to false
	 * 
	 */
	public void changeTurnTrackerToFalse() throws RemoteException, NotBoundException;
	
	/*
	 * This function is called by the client at the begining of the game so that
	 * the Chat, TurnTracker, and Board can all be set to the begining settings
	 * if it isn't called there can NullPointerExceptions
	 * 
	 */
	public void setToDefault()throws RemoteException, FileNotFoundException, IOException;
}

