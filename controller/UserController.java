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

	static Statement st = null;

	// method to get all user
	public static List<User> getAllUsers() throws SQLException {
		Connection con = JDBCProperties.getConnection();
		
		List<User> users = new ArrayList<User>();
		PreparedStatement pst = null;
		try {
			String query = "SELECT * FROM BANKDB.USERS";
			pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				users.add(new User(rs.getInt(1), rs.getString(2), rs.getLong(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getDouble(8), rs.getDouble(9), rs.getDate(10)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pst.close();
			con.close();
		}
		return users;
	}

	// method to register user in database
	public static int insertUser(String username, long accountNo, String name, String address, String contact,
			String password, Double initialDep) throws Exception {

		Connection con = JDBCProperties.getConnection();
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

	// method to check user for login purpose
	public static User userLogin(String username, String password) {
		
		Connection con = JDBCProperties.getConnection();
		User u = null;
		String query = "SELECT * FROM BANKDB.USERS WHERE user_name = ? AND user_password = ?";
		try {
			PreparedStatement pst  = con.prepareStatement(query);
			pst.setString(1, username);
			pst.setString(2, password);
			ResultSet rs = pst.executeQuery();
//			System.out.println(rs.toString());
			if (rs.next()) {
				u = new User(rs.getInt(1), rs.getString(2), rs.getLong(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getDouble(8), rs.getDouble(9), rs.getTimestamp(10));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;

	}

	// get User using only username to check user if exist's or not
	public static User getUser(String username) {
		Connection con = JDBCProperties.getConnection();
		User u = null;
		String query = "SELECT * FROM BANKDB.USERS WHERE user_name = ?";
		try {
			PreparedStatement pst = con.prepareStatement(query);
			pst = con.prepareStatement(query);
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				u = new User(rs.getInt(1), rs.getString(2), rs.getLong(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getDouble(8), rs.getDouble(9), rs.getTimestamp(10));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
	}

	// method to update user_balance of a specified user
	public static void updateUserBalance(String username, double amount, boolean credit) {
		Connection con = JDBCProperties.getConnection();
		PreparedStatement pst = null;
		double user_updated_balance = credit ? getBalance(username) + amount : getBalance(username) - amount;
//		System.out.println(user_updated_balance);
		String query = "UPDATE USERS SET user_balance = ? WHERE user_name = ?";
		try {
			pst = con.prepareStatement(query);
			pst.setDouble(1, user_updated_balance);
			pst.setString(2, username);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// get only user_balance using username
	public static double getBalance(String username) {
		Connection con = JDBCProperties.getConnection();
		double user_current_balance = 0;
		String query = "SELECT * FROM BANKDB.USERS WHERE user_name = ?";
		try {
			PreparedStatement pst = con.prepareStatement(query);
			pst = con.prepareStatement(query);
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				user_current_balance = rs.getDouble(9);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user_current_balance;
	}
	
	public static void updateUserDetails() {
		
	}

}
