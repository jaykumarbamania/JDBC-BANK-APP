package bankApp.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bankApp.model.User;

public class UserController {
	
	static Connection con = JDBCProperties.getConnection();
	static Statement st = null;
	
	public static List<User>  getAllUsers() throws SQLException {
		
		List<User> users = new ArrayList<User>();
		PreparedStatement pst = null;
		try {
			String query = "SELECT * FROM BANKDB.USERS";
			pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				users.add(new User(rs.getInt(1),rs.getString(2),rs.getLong(3),
						rs.getString(4),rs.getString(5),rs.getString(6),
						rs.getString(7),rs.getDouble(8),rs.getDouble(9),rs.getDate(10)));
			}
		}catch(Exception e) {e.printStackTrace();}
		finally {
			pst.close();
			con.close();
		}
		return users;
	}
	
	public static int insertUser(String username,long accountNo,String name, String address, 
			String contact,String password, Double initialDep) throws Exception {
		
		PreparedStatement pst = null;
		String insertQuery = "INSERT INTO USERS(user_id,user_name,user_accountNo,user_fullname,"
				+ "user_address,user_contact,user_password,user_initialDep,user_balance) VALUES(null,?,?,?,?,?,?,?,?)";
		pst = con.prepareStatement(insertQuery);
		pst.setString(1, username);
		pst.setLong(2, accountNo);
		pst.setString(3, name);
		pst.setString(4, address);
		pst.setString(5, contact);
		pst.setString(6, password);
		pst.setDouble(7, initialDep);
		pst.setDouble(8, initialDep);
		int result = pst.executeUpdate();
		pst.close();
		return result;
	}
	
	public static User getUser(String username,String password)  {
		PreparedStatement pst = null;
		User u = null;
		String query = "SELECT * FROM BANKDB.USERS WHERE user_name = ? AND user_password = ?";
		try {
			pst = con.prepareStatement(query);
			pst.setString(1, username);
			pst.setString(2, password);
			ResultSet rs = pst.executeQuery();
			System.out.println(rs.toString());
			if(rs.next()) {
				u = new User(rs.getInt(1),rs.getString(2),rs.getLong(3),
						rs.getString(4),rs.getString(5),rs.getString(6),
						rs.getString(7),rs.getDouble(8),rs.getDouble(9),rs.getDate(10));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return u;
		
	}

}
