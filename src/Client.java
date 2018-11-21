import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Client.java
 * Client file for running the Game/chat
 * Usage: java Client <hostname> <port>
 * @author jbschmi3
 *
 */
public class Client {
	GameService game;	
	
	public Client() throws RemoteException, NotBoundException, MalformedURLException {		
		game = (GameService) Naming.lookup("localhost");
	}
	
	public void play() throws RemoteException {
		//TODO: logic for playing game
	}
	
	public static void main(String[] args) {
		try {
			Client client = new Client();
			client.play();
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException n) {
			n.printStackTrace();
		} catch (MalformedURLException m) {
			m.printStackTrace();
		}
	}
}
