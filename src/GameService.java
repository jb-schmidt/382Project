import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * GameService.java
 * Interface for Game
 * @author jbschmi3
 *
 */
public interface GameService extends Remote {
	//output board
	String boardOutput() throws RemoteException;
	//make a move on board
	boolean move(int move, int player) throws RemoteException;
	//check for win/tie
	int gameOver() throws RemoteException;
	//chat
	String chat(String message) throws RemoteException;
}
