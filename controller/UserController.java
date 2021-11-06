package bankApp.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bankApp.model.User;

public class UserController {
	
	public static List<User>  getAllUsers() throws SQLException {
		
		List<User> users = new ArrayList<User>();
		
		Connection con = JDBCProperties.getConnection();
		
		try {
			String query = "SELECT * FROM BANKDB.USERS";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				users.add(new User(rs.getInt(1),rs.getString(2),rs.getLong(3),
						rs.getString(4),rs.getString(5),rs.getString(6),
						rs.getString(7),rs.getDouble(8),rs.getDouble(9),rs.getDate(10)));
			}
		}catch(Exception e) {e.printStackTrace();}
		finally {
			con.close();
		}
		return users;
	}

}
