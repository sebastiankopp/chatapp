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
				WerbeSender ws = new WerbeSender(portNumber+2);
				ws.addAdvertisement(new Advertisement("Handytarif: CONGSTAR   Vertrag abschließen unter http://www.congstar.de/22507",
						ffms, 16, WerbeSender.DEFAULT_UPD_INTERVAL));
				ws.addAdvertisement(new Advertisement("privates Weekend-Bahnhofs-Shuttle mit Großraumlimosine: Fahrt ab 1,20€/Person*\n" +
						"*Preis gilt für 6 Pers. / 1,40€ bei 5 Pers. / 1,70€ bei 4 Pers. / 2,20€ bei 3 Pers.", ffms, 14, WerbeSender.DEFAULT_UPD_INTERVAL));
				ws.addAdvertisement(new Advertisement("Spenden Sie für dieses Projekt oder für die Stiftung \"mehr Geld für mich\"\n"+
						"Barzahlung, Überweisung, Paypal akzeptiert.", ffms, 14, WerbeSender.DEFAULT_UPD_INTERVAL));
			} catch (Exception e) {return;}
		}).start();
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
