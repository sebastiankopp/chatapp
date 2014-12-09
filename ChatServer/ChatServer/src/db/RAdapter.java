package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.ChatMessage;
import model.Conversation;
import model.User;

public class RAdapter extends AbstractDBAdapter {
	private static final String GET_ALL_CONVS = "Select uc.conv_id from user u, usr_conv uc where u.userid = uc.userid and nickname = ?;";
	private static final String GET_USR_BY_CONV = "Select u.userid, u.realname, u.nickname from user, usr_conv where u.userid = uc.userid and uc.conv_id = ?;";
	private static final String GET_PW_BY_USR = "Select passwd_hash from user where nickname = ?";
//	private static final String ADD_USER = "Insert into user (nickname, password) values (?,?);";
	public  RAdapter() {
		// TODO Auto-generated constructor stub
		super();
	}
	public List<Conversation> getConvsByUsr(String nickn) throws SQLException{
		PreparedStatement pst = con.prepareStatement(GET_ALL_CONVS);
		pst.setString(1, nickn);
		ResultSet rs = pst.executeQuery();
		List<Conversation> rc = new LinkedList<Conversation>();
		pst = con.prepareStatement(GET_USR_BY_CONV);
		while (rs.next()) {
			int convId = rs.getInt(1);
			pst.setInt(1, convId);
			ResultSet rs2 = pst.executeQuery();
			List<User> ux = new LinkedList<User>();
			while (rs2.next()){
				ux.add(new User(rs2.getInt(1), rs2.getString(3), rs2.getString(2)));
			}
			Conversation ccx = new Conversation(convId , ux);
			rc.add(ccx);
		}
		return rc;
	}
	public List<ChatMessage> getMessages(Conversation conv){
		return null;
		
	}
	public boolean validateUser(String nickname, String pw){
		try {
			PreparedStatement pst = con.prepareStatement(GET_PW_BY_USR);
			pst.setString(1, nickname);
			ResultSet rs = pst.executeQuery();
			if (rs.next()){
				if (pwh.createHash(pw).equals(rs.getString(1))) return true;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return false;
	}
}
