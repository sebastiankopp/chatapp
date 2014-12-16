package chatBase.srv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
/**
 * Klasse mit alleiniger Herrschaft über die Nutzerdatenbank der Chatanwendung
 * Dient dem Hinzufügen, Löschen, Verifizieren von Nutzern und dem Passwortändern
 * @author Sebastian Kopp
 *
 */
public class DBAdapter {
	private Connection con;
	private static final String CREATE_TABLE = "Create table if not exists chatusers (username varchar(100) not null primary key, passwd_hash varchar(255) not null);";
	private static final String VALIDATE_USR = "Select passwd_hash from chatusers where username = ?;";
	private static final String ADD_USR = "Insert into chatusers (username, passwd_hash) values (?,?);";
	private static final String SET_PW = "Update chatusers set passwd_hash = ? where username = ?;";
	private static final String DEL_USR = "Delete from chatusers where username = ?;";
	private static final String SEE_USRS = "Select username from chatusers;";
	private static final String DRIVER_CLASS = "org.h2.Driver";
	private static final String DB_CON_URL = "jdbc:h2:./chatbasedb";
	private static final String DB_USR = "sa";
	private static final String DB_PW = "";
	private static final int DEFAULT_ROLLBACK_TRIES = 5;
	private PWHasher pwh;
	private static DBAdapter instance = null;
	private Server srv = null;
	/**
	 * 
	 * @return Instanz der Singletonklasse DBAdapter
	 */
	public static DBAdapter getInstance(){
		if (instance == null)
			instance = new DBAdapter();
		instance.srv = Server.getInstance();	
		return instance;
	}
	private  DBAdapter(){
		srv = Server.getInstance();	
		try {
			con = getConnection(DEFAULT_ROLLBACK_TRIES);
			con.createStatement().execute(CREATE_TABLE);
			pwh = new PWHasher();
		} catch (Exception e) {
			if (srv != null) {
				srv.logStackTrace(e);
				srv.logMessage("Fatal error. Connection could not be established");
			}
			
		}
	}
	/**
	 * Verbindung mit mehreren Versuchen erzeugen
	 * @param tries Anz Versuche
	 * @return Verbindung mit der DB
	 * @throws SQLException
	 */
	private Connection getConnection(int tries) throws SQLException{
		if (tries > 0){
			try {
				Class.forName(DRIVER_CLASS);
				return DriverManager.getConnection(DB_CON_URL, DB_USR, DB_PW);
			} catch (ClassNotFoundException | SQLException e) {
				if (srv != null) srv.logStackTrace(e);
				return getConnection(tries-1);
			}
		} else throw new SQLException("Connection could not be established");
	}
	/**
	 * Verifiziert die Korrektheit eines Nutzerpassworts
	 * @param username Nutzername
	 * @return Korrektheit des Passworts, im Fehlerfall bzw. bei Nichtexistenz des Users <code>false</code>
	 */
	public boolean verifyUsr(String username, String pw){
		String pwxhash = pwh.createHash(pw);
		try {
			PreparedStatement pst = con.prepareStatement(VALIDATE_USR);
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				return rs.getString(1).equals(pwxhash);
			else return false;
		} catch (SQLException e) {
			if (srv != null)srv.logStackTrace(e);
			else e.printStackTrace();
			return false;
		}
	}
	/**
	 *  Nutzer (durch Admin) hinzufügen
	 * @param name Nutzername
	 * @param pwplain Passwort (Klartext)
	 * @return Erfolg der Operation
	 */
	public boolean addUser (String name, String pwplain){
		try {
			PreparedStatement pst = con.prepareStatement(ADD_USR);
			pst.setString(1, name);
			pst.setString(2, pwh.createHash(pwplain));
			pst.executeUpdate();
			con.commit();
			return true;
		} catch (Exception e) {
			if (srv != null)
				srv.logStackTrace(e);
			doRollback();
			return false;
		}
	}
	/**
	 * 
	 * @return Namen aller User des Chatsystems
	 */
	public List<String> listUsers(){
		List<String> rc = new LinkedList<String>();
		try {
			ResultSet rs = con.createStatement().executeQuery(SEE_USRS);
			while (rs.next())
				rc.add(rs.getString(1));
		} catch (Exception e){
			if (srv != null)srv.logStackTrace(e);
		}
		return rc;
	}
	/**
	 * Passwort eines Nutzers ändern
	 * @param username Nutzername
	 * @param pwplain Klartext des gewünschten Neupassworts
	 * @return Erfolg der Operation
	 */
	public boolean changePW(String username, String pwplain){
		try {
			PreparedStatement pst = con.prepareStatement(SET_PW);
			pst.setString(1, pwh.createHash(pwplain));
			pst.setString(2, username);
			pst.executeUpdate();
			con.commit();
			return true;
		} catch (Exception e) {
			if (srv != null)srv.logStackTrace(e);
			doRollback();
			return false;
		}
	}
	/**
	 * Nutzer aus DB transaktional löschen
	 * @param username Nutzername
	 * @return Erfolg der Operation
	 */
	public boolean deleteUser(String username){
		try {
			PreparedStatement pst = con.prepareStatement(DEL_USR);
			pst.setString(1, username);
			pst.executeUpdate();
			con.commit();
			return true;
		} catch (Exception e) {
			if (srv != null) srv.logStackTrace(e);
			doRollback();
			return false;
		}
	}
	private void doRollback (){doRollback(DEFAULT_ROLLBACK_TRIES);}
	private void doRollback(int tries){
		try {
			con.rollback();
		} catch (Exception exc){
			if (tries > 0)
				doRollback(tries-1);
			else System.err.println("FATAL Error: Rollback finally failed!");
		}
	}
}
