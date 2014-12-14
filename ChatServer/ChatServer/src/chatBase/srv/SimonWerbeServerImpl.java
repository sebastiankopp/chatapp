package chatBase.srv;

import java.util.Vector;

import com.google.gson.Gson;

import chatBase.model.Advertisement;
import de.root1.simon.annotation.SimonRemote;

@SimonRemote(value={SimonWerbeServerInterface.class})
class SimonWerbeServerImpl implements SimonWerbeServerInterface {
	private Vector<Advertisement> ads;
	private Gson gs;
	public SimonWerbeServerImpl(WerbeSchleuder ws) {
		gs = new Gson();
		this.ads = ws.getAds();
	}
	@Override
	public String createAd() {
		int valx = (int) (ads.size() * Math.random());
		Advertisement adx = ads.get(valx);
		
			return gs.toJson(adx, Advertisement.class);
		
	}

}
