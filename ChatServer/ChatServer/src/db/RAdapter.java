package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import model.ChatMessage;
import model.Conversation;
import model.User;

public class RAdapter extends AbstractDBAdapter {
	private static final String GET_ALL_CONVS = "Select uc.conv_id from user u, usr_conv uc where u.userid = uc.userid and nickname = ?;";
	private static final String GET_USR_BY_CONV = "Select u.userid, u.realname, u.nickname from user u, usr_conv  uc where u.userid = uc.userid and uc.conv_id = ?;";
	private static final String GET_PW_BY_USR = "Select passwd_hash from user where nickname = ?";
	private static final String GET_USR_BY_NICKNAME = "Select userid, nickname, realname from user where nickname = ?;";
	private static final String GET_USR_BY_ID = "Select userid, nickname, realname from user where userid = ?;";
	private static final String GET_MSG_BY_CONV = "Select msg_id, msg_text, msg_dt, absender_id from message where conv_id = ?;";
//	private static final String ADD_USER = "Insert into user (nickname, password) values (?,?);";
	private ResultSet rs;
	public  RAdapter() {
		// TODO Auto-generated constructor stub
		super();
	}
	public List<Conversation> getConvsByUsr(String nickn) throws SQLException{
		PreparedStatement pst = con.prepareStatement(GET_ALL_CONVS);
		pst.setString(1, nickn);
		rs = pst.executeQuery();
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
	public List<ChatMessage> getMessages(int convid){
		try {
			Conversation conv = new Conversation(convid, null);
			PreparedStatement pst = con.prepareStatement(GET_USR_BY_CONV);
			pst.setInt(1, convid);
			rs = pst.executeQuery();
			List<User> ulist = new LinkedList<User>();
			while(rs.next()){
				ulist.add(new User(rs.getInt(1), rs.getString(3), rs.getString(2)));
			}
			conv.setUsers(ulist);
			List<ChatMessage> rc = new LinkedList<ChatMessage>();
			pst = con.prepareStatement(GET_MSG_BY_CONV);
			pst.setInt(1, convid);
			rs = pst.executeQuery();
			while(rs.next()){
				User abs = ulist.stream().filter(exx -> {
							try {
								return (exx.getUserid() == rs.getInt(4));
							} catch (Exception e) {return false;}
						}).collect(Collectors.toList()).get(0);
				rc.add(new ChatMessage(rs.getString(2), abs, conv, rs.getTimestamp(3).toLocalDateTime()));
			}
			return rc;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public boolean validateUser(String nickname, String pw){
		try {
			PreparedStatement pst = con.prepareStatement(GET_PW_BY_USR);
			pst.setString(1, nickname);
			rs = pst.executeQuery();
			if (rs.next()){
				String hash = pwh.createHash(pw);
				String dbhash = rs.getString(1);
				System.err.println(hash + " wurde abgeglichen mit:\n" + dbhash);
				if (hash.equals(dbhash)) return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public User getUserByNickname(String username) throws NullPointerException {
		// TODO Auto-generated method stub
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(GET_USR_BY_NICKNAME);
			pst.setString(1, username);
			rs = pst.executeQuery();
			if (rs.next()){
				return new User (rs.getInt(1), rs.getString(2), rs.getString(3));
			} else throw new NullPointerException("User not found");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NullPointerException(e.getMessage());
		}
	}
	public User getUserById(int userid) throws NullPointerException {
		// TODO Auto-generated method stub
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(GET_USR_BY_ID);
			pst.setInt(1, userid);
			ResultSet rs = pst.executeQuery();
			if (rs.next()){
				return new User (rs.getInt(1), rs.getString(2), rs.getString(3));
			} else throw new NullPointerException("User not found");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NullPointerException(e.getMessage());
		}
	}
	public String getAllUsersforPrint(){
		StringBuilder rc = new StringBuilder();
		try {
			ResultSet rs = con.createStatement().executeQuery("Select * from user;");
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++){
				rc.append(rsmd.getColumnLabel(i) + "\t\t");
			}
			rc.append("\n-------------------------------------------------\n");
			while (rs.next()){
				for (int i = 1; i <= rsmd.getColumnCount(); i++){
					String obj;
					try {
						obj = rs.getString(i);
					} catch (Exception e){
						obj =  ""+rs.getInt(i);
					}
					rc.append(obj + "\t");
				}
				rc.append("\n\n");
			}
		} catch (Exception e) {
			rc.append("\n" + e.getMessage() + "\n");
			for (StackTraceElement dd: e.getStackTrace()){
				rc.append(dd.toString() + "\n");
			}
		}
		return rc.toString();
	}
}
