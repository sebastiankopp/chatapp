package db;

import java.sql.PreparedStatement;
import java.util.List;

import model.ChatMessage;
import model.User;

public class CUDAdapter extends AbstractDBAdapter{
	private static final String ADD_USER = "Insert into user (nickname, passwd_hash) values (?,?);";
	private static final String DELETE_USER_MESSAGES = "Delete from message where absender_id = (select userid from user where nickname = ?);";
	private static final String DELETE_USER = "Delete from user where nickname = ?;";
	private static final String ADD_MESSAGE = "Insert into messages (msg_text,msg_dt,conv_id,absender_id) values (?,?,?,?);";
	private static final String SET_NEW_PW = "Update user set passwd_hash = ? where userid = ?";
	private static final String ADD_NEW_CONV = "Insert into conv (conv_str) values (?);";
	//	private static final String CHK_UNAME_EXIST = "Select count (*) from user where nickname = ?;";
	public CUDAdapter(){
		super();
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
			e.printStackTrace();
			doRollback();
			return false;
		}
	}
	public boolean addMessage(ChatMessage msg){
		try{
			PreparedStatement pst = con.prepareStatement(ADD_MESSAGE);
			pst.setString(1, msg.getMessage());
			pst.setObject(2, msg.getTstamp());	// sufficient acc to http://www.oracle.com/technetwork/articles/java/jf14-date-time-2125367.html
			pst.setInt(3, msg.getConv().getConvId());
			pst.setInt(4, msg.getFrom().getUserid());
			pst.execute();
			con.commit();
			return true;
		} catch(Exception sqle){
			sqle.printStackTrace();
			doRollback();
			return false;
		}
	}
	public boolean changePW(User u, String pw){
		try{
			PreparedStatement pst = con.prepareStatement(SET_NEW_PW);
			pst.setString(1, pwh.createHash(pw));
			pst.setInt(2, u.getUserid());
			pst.execute();
			con.commit();
			return true;
		} catch (Exception sqle ) {
			sqle.printStackTrace();
			doRollback();
			return false;
		}
		
	}
	public boolean addConv(List<User> users){
		return false;		// TODO finsih
		
	}
	public boolean deleteUserByNickName(String uname){
		try {
			PreparedStatement pst = con.prepareStatement(DELETE_USER_MESSAGES);
			pst.setString(1, uname);
			pst.executeUpdate();
			pst = con.prepareStatement(DELETE_USER);
			pst.setString(1, uname);
			pst.executeUpdate();
			con.commit();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			doRollback();
			return false;
		}
	}
}