package chatBase.srv;

import java.util.Scanner;
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
		if (args.length == 0){
			System.out.println("Für Standardport bitte beliebigen Buchstaben eingeben.");
			System.out.print("Bitte Portnummer eingeben: ");
			Scanner sc = new Scanner(System.in);
			try {
				portNumber = sc.nextInt();
			} catch (Exception e) {
				System.err.println("Portnummer ungültig, Standardport wird genutzt.");
			}
			sc.close();
		} else {
			try {
				portNumber =Integer.parseInt(args[1]);
			} catch (Exception e) {
				System.err.println("Portnummer ungültig, Standardport wird genutzt.");
				System.err.println("Syntax: java -cp chatapp.jar chatBase.ServerMain [portnumber]");
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
