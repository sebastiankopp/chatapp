package chatBase.srv;

import java.util.concurrent.ConcurrentMap;

import chatBase.model.Advertisement;

public class WerbeSchleuder extends Thread {
	private int updInterval;
	private int maxVal;
	private ConcurrentMap<Integer,Advertisement> ads;
	public WerbeSchleuder(int interval){
		updInterval = interval;
		maxVal = 0;
	}
	public int addAdvertisement(Advertisement ad){
		ads.put(maxVal, ad);
		return maxVal++;
	}
}
