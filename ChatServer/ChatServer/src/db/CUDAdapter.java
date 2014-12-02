package db;

import java.sql.PreparedStatement;

import security.PWHasher;

public class CUDAdapter extends AbstractDBAdapter{
	
	private static final String ADD_USER = "Insert into user (nickname, password) values (?,?);";
	private PWHasher pwh;
//	private static final String CHK_UNAME_EXIST = "Select count (*) from user where nickname = ?;";
	public CUDAdapter(){
		dbc = DBController.getInstance();
		try {
			con = dbc.getConnection();
			pwh = new PWHasher();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 * Hinzufügen eines Nutzers nach Nutzernamen und Passwort
	 * Die Überprüfung, ob der Username bereits existiert und ob das PW stark genug ist, muss stattgefunden haben!
	 * Das PW jedoch soll noch nicht gehashed sein!
	 * @return ob Nutzereintrag geglückt ist
	 */
	public boolean addUser(String desiredNickname, String pw){
		try {
			PreparedStatement pst = con.prepareStatement(ADD_USER);
			pst.setString(1, desiredNickname);
			pst.setString(2, pwh.createHash(pw));
			pst.execute();
			con.commit();
			return true;
		} catch (Exception e) {
			// TODO Rollbackbehandlung schreiben
			e.printStackTrace();
			doRollback();
			return false;
		}
		
	}
	
}
