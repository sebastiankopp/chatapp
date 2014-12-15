package chatBase.srv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DBAdapter {
	private Connection con;
	private static final String CREATE_TABLE = "Create table if not exists chatusers (username varchar(100) not null primary key, passwd_hash varchar(255) not null);";
	private static final String VALIDATE_USR = "Select passwd_hash from chat_users where username = ?;";
	private static final String ADD_USR = "Insert into chatuser (username, passwd_hash) values (?,?);";
	private static final String SET_PW = "Update chatusers set passwd_hash = ? where username = ?;";
	private static final String SEE_USRS = "Select username from chatusers;";
	private static final String DRIVER_CLASS = "org.h2.Driver";
	private static final String DB_CON_URL = "jdbc:h2:./chatdb";
	private static final String DB_USR = "sa";
	private static final String DB_PW = "";
	private static final int DEFAULT_ROLLBACK_TRIES = 5;
	private PWHasher pwh;
	private static DBAdapter instance = null;
	private Server srv;
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
			srv.logStackTrace(e);
			srv.logMessage("Fatal error. Connection could not be established");
		}
	}
	private Connection getConnection(int tries) throws SQLException{
		if (tries > 0){
			try {
				Class.forName(DRIVER_CLASS);
				return DriverManager.getConnection(DB_CON_URL, DB_USR, DB_PW);
			} catch (ClassNotFoundException | SQLException e) {
				srv.logStackTrace(e);
				return getConnection(tries-1);
			}
		} else throw new SQLException("Connection could not be established");
	}
	public String getPwHash(String username){
		try {
			PreparedStatement pst = con.prepareStatement(VALIDATE_USR);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				return rs.getString(1);
			else return null;
		} catch (SQLException e) {
			srv.logStackTrace(e);
			return null;
		}
	}
	public boolean addUser (String name, String pwplain){
		try {
			PreparedStatement pst = con.prepareStatement(ADD_USR);
			pst.setString(1, name);
			pst.setString(2, pwh.createHash(pwplain));
			pst.executeUpdate();
			con.commit();
			return true;
		} catch (Exception e) {
			srv.logStackTrace(e);
			doRollback();
			return false;
		}
	}
	public List<String> listUsers(){
		List<String> rc = new LinkedList<String>();
		try {
			ResultSet rs = con.createStatement().executeQuery(SEE_USRS);
			while (rs.next())
				rc.add(rs.getString(1));
		} catch (Exception e){
			srv.logStackTrace(e);
		}
		return rc;
	}
	public boolean changePW(String username, String pwplain){
		try {
			PreparedStatement pst = con.prepareStatement(SET_PW);
			pst.setString(1, pwh.createHash(pwplain));
			pst.setString(2, username);
			pst.executeUpdate();
			con.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			doRollback();
			return false;
		}
	}
	public boolean deleteUser(String nickname){
		return false;
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
