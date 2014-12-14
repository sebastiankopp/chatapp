package chatBase.srv;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;
import chatBase.model.Advertisement;
import de.root1.simon.Registry;
import de.root1.simon.Simon;
import de.root1.simon.exceptions.NameBindingException;

public class WerbeSchleuder  {
//	private int updInterval;
//	private int maxVal;
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
