package skoldvariant.servercore;

public class MessageTypes {
	public static final int 
		LOGIN_REQ = 0,
		SUBMIT_MSG = 1,
		CONV_LIST = 2,
		CREATE_CONV = 3,
		CONV_ADDUSR = 4,
		LOGOUT_REQ = 5,
		PULL_REQ = 6,
		CHNG_PW = 7;
}
//Typen:
//		Login-Anfrage
//		Nachricht schreiben
//  	Konversationen auflisten
//		Konversation erstellen
//		Konversation: Nutzer hinzufügen
//		Logout-Anfrage
//		Pull-Request
//		PW ändern