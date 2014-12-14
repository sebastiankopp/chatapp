package chatBase.srv;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;
import chatBase.model.Advertisement;
import de.root1.simon.Registry;
import de.root1.simon.Simon;
import de.root1.simon.exceptions.NameBindingException;
/**
 * Stellt das Objekt im Server dar, das die Werbung absondert
 * @author Sebastian Kopp
 *
 */
public class WerbeSchleuder  {
//	private int updInterval;
//	private int maxVal;
	public static final int DEFAULT_UPD_INTERVAL = 10000;
	private Vector<Advertisement> ads;
	public static final String AD_BINDING = "werbeserver";
	private Registry reg;
//	private SimonWerbeServerImpl swsim;
	public WerbeSchleuder(int port) throws UnknownHostException, IOException, NameBindingException{
//		updInterval = interval;
		ads = new Vector<Advertisement>();
		reg = Simon.createRegistry(port);
		reg.bind(AD_BINDING, new SimonWerbeServerImpl(this));
	}
	public void addAdvertisement(Advertisement ad){
		ads.add(ad);
	}
	Vector<Advertisement> getAds(){
		return this.ads;
	}
	
}
