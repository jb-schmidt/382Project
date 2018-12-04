import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameInterface extends Remote{
	public void makeComment(String comment, String serverName, String host, boolean differentiatesServers)throws RemoteException, NotBoundException, IOException;
	public String attachToServer() throws FileNotFoundException, IOException, RemoteException;
	public boolean checkTurn(String serverName, String host, boolean differentiatesServers) throws RemoteException, FileNotFoundException, IOException;
	public String [] [] updateBoard(String serverName, String host, boolean differentiatesServers) throws RemoteException, FileNotFoundException, IOException;
	public void addToBoard(String [] [] str, String serverName, String host, boolean differentiatesServers) throws RemoteException;
	public void passTurn(String serverName, String host, boolean differentiatesServers) throws RemoteException;
	public GameInterface connectToOtherServer(String serverName, String host) throws RemoteException, NotBoundException;
	public String readChat()throws RemoteException, FileNotFoundException, IOException;
}
