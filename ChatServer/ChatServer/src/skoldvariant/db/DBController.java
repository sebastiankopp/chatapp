package skoldvariant.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DBController {
	private static final String CREATE_USR_TABLE = "Create table if not exists user (" + 
		"userid int not null auto_increment primary key, nickname varchar(50) not null unique," + 
			"passwd_hash varchar(255) not null, realname varchar(100));";
	private static final String CREATE_CONV_TABLE = "Create table if not exists conversation (" + 
			"conv_id int not null auto_increment primary key, conv_str varchar(100));";
	private static final String CREATE_UC_TABLE = "Create table if not exists usr_conv(" +
			"usr_conv_id long not null auto_increment primary key, userid int, conv_id int);";
	private static final String CREATE_MSG_TABLE = "Create table if not exists message (" + 
			"msg_id long not null auto_increment primary key, msg_text clob, msg_dt timestamp not null, " + 
			"conv_id int not null, absender_id int not null);";
	private static final String DRIVER_CLASS = "org.h2.Driver";
	private static final String DB_CON_URL = "jdbc:h2:./chatdb";
	private static final String DB_USR = "sa";
	private static final String DB_PW = "";
	private static DBController instance = null;
	private DBController(){
		try {
			Connection con = getConnection();
			con.createStatement().execute(CREATE_USR_TABLE);
			con.createStatement().execute(CREATE_CONV_TABLE);
			con.createStatement().execute(CREATE_UC_TABLE);
			con.createStatement().execute(CREATE_MSG_TABLE);
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public Connection getConnection() throws SQLException {
		return getConnection(AbstractDBAdapter.DEFAULT_ROLLBACK_TRIES);
	}
	public static DBController getInstance(){
		if (instance == null)
			instance = new DBController();
		return instance;
	}
	private Connection getConnection(int tries) throws SQLException{
		if (tries > 0){
			try {
				Class.forName(DRIVER_CLASS);
				return DriverManager.getConnection(DB_CON_URL, DB_USR, DB_PW);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return getConnection(tries-1);
			}
		} else throw new SQLException("Connection could not be established");
	}
}
