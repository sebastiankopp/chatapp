package chatBase.client;

import java.awt.Font;
import java.net.UnknownHostException;
import javax.swing.JTextArea;

import com.google.gson.Gson;

import chatBase.model.Advertisement;
import chatBase.srv.SimonWerbeServerInterface;
import chatBase.srv.WerbeSchleuder;
import de.root1.simon.Lookup;
import de.root1.simon.Simon;
import de.root1.simon.exceptions.EstablishConnectionFailed;
import de.root1.simon.exceptions.LookupFailedException;
/**
 * Wird dem Client hinzugefügt. Fpr jede Verbindung sollte die Serverportnummer aus Protokollgründen 
 * hier um 2 größer sein als für die Chatkommunikation. Wird einmal instanziiert, bei jedem Logout gestoppt,
 * Port und Server-Adresse werden bei jedem Login neu gesetzt, dann wird die Werbung wieder gestartet.
 * @author Sebastian Kopp
 *
 */
public class AdClient extends Thread {
	private JTextArea target;
	private static final int UPD_INTERVAL = 10000; // Werbeupdateintervall
	private Gson gs;
	private Lookup nlu;
	private SimonWerbeServerInterface swsint;
	/**
	 * Konstruktor wird nur bei der GUI-Instanzziierung aufgerufen
	 * @param target JTextArea, die mit Werbung versehen werden soll
	 */
	public AdClient(JTextArea target){
		this.target = target;
		gs = new Gson();
		this.target.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
		this.target.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
	}
	/**
	 * Aufruf beim Login. Fängt mit dem Werbung ziehen an
	 * @param host Der Chatserver
	 * @param adport Werbe-Port des Servers. Um 2 höher als der Chatport
	 */
	public void startAdv(String host, int adport){
		try {
			nlu = Simon.createNameLookup(host, adport);
			swsint = (SimonWerbeServerInterface) nlu.lookup(WerbeSchleuder.AD_BINDING);
			start();
		} catch (UnknownHostException | LookupFailedException | EstablishConnectionFailed e) {
			target.setText("Oh no!!");
		}
	}
	/**
	 * Wird beim Logout aufgerufen, um die Bombardierung mit Werbung zu beenden
	 */
	public void stopAdv(){
		if (isAlive()){
			interrupt();
			nlu.release(swsint);
		}
	}
	/**
	 * Zeigt alle @see UPD_INTERVAL ms neue Werbung an. Werbung ist kein reiner String, sondern JSON.
	 */
	public void run(){
		while(true){
			Advertisement ad = gs.fromJson(swsint.createAd(), Advertisement.class);
			setFont(ad, 0);
			target.setText(ad.getAdText());
			try {
				Thread.sleep(UPD_INTERVAL);
			} catch (InterruptedException e) { }
		}
	}
	/**
	 * Hilfsmethode, um für jede Werbung den Style neu zu setzen. Richtet sich nach den Vorgaben in der Werbeanzeige
	 * @param ad Werbeanzeige
	 * @param i Nummer der ausprobierten Schriftart. Werbung hat (analog zu CSS) mehrere Schriftarten
	 */
	private void setFont(Advertisement ad, int i){
		try{
			target.setFont(new Font(ad.getFontFamilies().get(i), Font.PLAIN, ad.getFontSize()));
		} catch (Exception e){
			i++;
			if (ad.getFontFamilies().size() > i) setFont(ad, i);
		}
	}
}
