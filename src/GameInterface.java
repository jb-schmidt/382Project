import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameInterface extends Remote{
	public void makeComment(String comment, String serverName, String host, boolean differentiatesServers)throws RemoteException, NotBoundException, IOException;
	public String attachToServer() throws FileNotFoundException, IOException;
	public boolean checkTurn(String serverName, String host, boolean differentiatesServers);
	public String [] [] updateBoard(String serverName, String host, boolean differentiatesServers);
	public void addToBoard(String [] [] str, String serverName, String host, boolean differentiatesServers);
	public void passTurn(String serverName, String host, boolean differentiatesServers);
	public GameInterface connectToOtherServer(String serverName, String host) throws RemoteException, NotBoundException;
}
