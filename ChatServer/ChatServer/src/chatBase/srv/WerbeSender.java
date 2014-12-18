package chatBase.srv;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;
import java.util.stream.Collectors;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import chatBase.model.Advertisement;
import de.root1.simon.Registry;
import de.root1.simon.Simon;
import de.root1.simon.exceptions.NameBindingException;
/**
 * Stellt das Objekt im Server dar, das die Werbung absondert
 * Die XML-Dateien werden in einer advert.xml-Datei im Serververzeichnis gespeichert
 * 
 * @author Sebastian Kopp
 *
 */
public class WerbeSender  {
	public static final int DEFAULT_UPD_INTERVAL = 10000;
	private Vector<Advertisement> ads;
	private Server srv;
	private static final String AD_FILE = "advert.xml";
	public static final String AD_BINDING = "werbeserver";
	private Registry reg;
	public WerbeSender(int port) throws UnknownHostException, IOException, NameBindingException{
		srv = Server.getInstance();
		ads = new Vector<Advertisement>();
		reg = Simon.createRegistry(port);
		reg.bind(AD_BINDING, new SimonWerbeServerImpl(this));
		try {
			Element root = (new SAXBuilder()).build(new File(AD_FILE)).getRootElement();
			for (Element xad: root.getChildren("advert")){
				Advertisement _ad = new Advertisement();
				_ad.setFontFamilies(xad.getChild("fonts").getChildren().stream()
						.map(ee -> ee.getText()).collect(Collectors.toList()));
				_ad.setAdText(xad.getChildText("text")
						.replaceAll("\\n", "\n").replaceAll("\\\\n", "\n"));
				
				_ad.setFontSize(Integer.parseInt(xad.getChildText("font-size")));
				ads.add(_ad);
			}
		} catch (Exception e) {
			srv.logStackTrace(e);
		}
	}
	public void addAdvertisement(Advertisement ad){
		ads.add(ad);
	}
	Vector<Advertisement> getAds(){
		return this.ads;
	}
	
}
