package servercore;

import java.util.TreeMap;

import model.User;

public class ServerCenter {
	private static ServerCenter instance;
//	private GsonControl gsc;
	private TreeMap<String,User> actives; // eingeloggte Nutzer
	private ServerCenter (){
		 actives = new TreeMap<String, User>();
	}
	public static ServerCenter getInstance() {
		if (instance == null)
			instance = new ServerCenter();
		return instance;
	}
	public String processJson(String reqBody, String param) {
		if (reqBody == null || reqBody.length() == 0){
			return "NIXNIXNIXNIX";
		} else {
			switch (param.toLowerCase()){
				// TODO Eigentliche Fälle hinschreiben
				//	Typen:
				//		Login-Anfrage
				//		Nachricht schreiben
				//  	Konversationen auflisten
				//		Konversation erstellen
				//		Konversation: Nutzer hinzufügen
				//		Logout-Anfrage
				//		Pull-Request
				//		PW ändern
				case "test":
				default: return reqBody;
			}
		
		}
		
	}
	protected SingleUserContext loginUser(){
		// TODO
		return null;
	}
	protected boolean logoutUser(){
		// TODO
		return false;
	}
	

}
