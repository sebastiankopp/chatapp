package db;

import java.sql.SQLException;

public class RAdapter extends AbstractDBAdapter {
//	private static final String ADD_USER = "Insert into user (nickname, password) values (?,?);";
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
