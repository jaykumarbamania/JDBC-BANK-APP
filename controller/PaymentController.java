package bankApp.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import bankApp.model.Payment;

public class PaymentController {
	
	
	public static void storePayments(String sender, String receiver, double amount,
			double senderBal, double receicerBal)  {
		
		Connection con = JDBCProperties.getConnection();

		String insertQuery = "INSERT INTO PAYMENTS(payment_id,sender,receiver,amount,"
				+ "sender_bal,receiver_bal) VALUES(null,?,?,?,?,?)";
		try {
			PreparedStatement pst = con.prepareStatement(insertQuery);
			pst.setString(1, sender);
			pst.setString(2, receiver);
			pst.setDouble(3, amount);
			pst.setDouble(4, senderBal);
			pst.setDouble(5, receicerBal);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Payment> getAllPayments() throws SQLException {
		
		Connection con = JDBCProperties.getConnection();

		List<Payment> payments = new ArrayList<Payment>();
		PreparedStatement pst = null;
		try {
			String query = "SELECT * FROM BANKDB.PAYMENTS";
			pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				payments.add(new Payment(rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5),
						rs.getDouble(6), rs.getTimestamp(7)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pst.close();
			con.close();
		}
		return payments;
	}
	
	public static List<Payment> getUserPayments(String sender) throws SQLException {
		
		Connection con = JDBCProperties.getConnection();

		List<Payment> allPayments = new ArrayList<Payment>();
		PreparedStatement pst = null;
		try {
			String query = "SELECT * FROM BANKDB.PAYMENTS";
			pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				allPayments.add(new Payment(rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5),
						rs.getDouble(6), rs.getTimestamp(7)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pst.close();
			con.close();
		}
		List<Payment> filteredPayments = allPayments.stream()
													.filter(payment -> payment.getSender().equals(sender) || (payment.getReceiver() == null ? true : payment.getReceiver().equals(sender)) )
													.collect(Collectors.toList());
		Collections.sort(filteredPayments,new CompareByDate().reversed());
		
		return filteredPayments;
	}
}
