package chatBase.srv;

import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import chatBase.model.Advertisement;
/**
 * Hauptklasse des Servers. WerbeSchleuder wird vorerst hier instanziiert
 * @author Sebastian
 *
 */
public class ServerMain {
	private static int portNumber;
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
		new Thread(() ->{
			try {
				List<String> ffms = Arrays.asList("Helvetica", "Sans-serif", "Sans Serif", "Times New Roman");
				WerbeSchleuder ws = new WerbeSchleuder(portNumber+2);
				ws.addAdvertisement(new Advertisement("XX -- Das Auto", ffms, 16));
				ws.addAdvertisement(new Advertisement("Periel Color -- Das beste Waschmittel", ffms, 14));
			} catch (Exception e) {return;}
		}).start();
		// War nur ein Test
//		new Thread(() -> {
//			JTextArea jta = new JTextArea(5, 100);
//			AdClient ac = new AdClient(jta);
//			ac.startAdv("localhost", portNumber+2);
//			JOptionPane.showMessageDialog(null, jta);
//		}).start();		
		srv.performInfiniteLoop();
	}
}
