package chatBase.srv;

import javax.swing.JOptionPane;
/**
 * Hauptklasse des Servers. WerbeSchleuder wird vorerst hier instanziiert
 * @author Sebastian
 *
 */
public class ServerMain {
	private static int portNumber;
	/**
	 * Mainmethode des Servers: Schickt provisorisch noch vordefinierte Werbemeldungen
	 * @param args
	 */
	public static void main(String[] args){
		portNumber = Server.DEFAULT_PORT;
		portNumber = Integer.parseInt(JOptionPane.showInputDialog("Please enter a port number"));
		if (args.length > 0){
			try {
				portNumber =Integer.parseInt(args[1]);
			} catch (Exception e) {
				System.err.println("Port number might be invalid");
				System.err.println("Usage: java -cp chatapp.jar chatBase.ServerMain [portnumber]");
			}
		}
		Server srv = Server.getInstance(portNumber, System.err);
		
		// War nur ein Test
//		new Thread(() -> {
//			JTextArea jta = new JTextArea(5, 100);
//			AdClient ac = new AdClient(jta);
//			ac.startAdv("localhost", portNumber+2);
//			JOptionPane.showMessageDialog(null, jta);
//		}).start();		
		srv.doRun();
	}
}
