import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameInterface extends Remote{
	public void makeComment(String str);
	public String attachToServer() throws FileNotFoundException, IOException;
	public boolean checkTurn(String serverName);
	public String [] [] updateBoard(String serverName);

}
