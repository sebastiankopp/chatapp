package chatBase.srv;

import java.util.Vector;

import com.google.gson.Gson;

import chatBase.model.Advertisement;
import de.root1.simon.annotation.SimonRemote;

@SimonRemote(value={SimonWerbeServerInterface.class})
/**
 * Serverseitige Implementierung des Werbeabrufs
 * @author Sebastian Kopp
 *
 */
class SimonWerbeServerImpl implements SimonWerbeServerInterface {
	private Vector<Advertisement> ads;
	private Gson gs;
	public SimonWerbeServerImpl(WerbeSender ws) {
		gs = new Gson();
		this.ads = ws.getAds();
	}
	@Override
	/**
	 * Schickt auf Abruf eine zuf�llig aus der �bersicht der Werbenachrichten ausgew�hlte Meldung
	 */
	public String createAd() {
		int valx = (int) (ads.size() * Math.random());
		Advertisement adx = ads.get(valx);
		
			return gs.toJson(adx, Advertisement.class);
		
	}

}
