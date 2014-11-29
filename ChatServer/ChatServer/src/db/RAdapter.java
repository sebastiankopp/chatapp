package db;

import java.sql.Connection;
import java.sql.SQLException;

public class RAdapter {
	private DBController dbc;
	private Connection con;
	private static final String ADD_USER = "Insert into user (nickname, password) values (?,?);";
	public  RAdapter() {
		// TODO Auto-generated constructor stub
		dbc = DBController.getInstance();
		try {
			con = dbc.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
	}
}
