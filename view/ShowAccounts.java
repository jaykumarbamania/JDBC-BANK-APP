package bankApp.view;


import java.sql.SQLException;

import bankApp.controller.UserController;
import bankApp.model.User;

//@FunctionalInterface //allows only one abstract method
//interface ShowInterface{
//	void details(boolean everyDetails);
//}
//Jaykumar

public class ShowAccounts {
	
	public void displayDetails(boolean everyDetails)  {
		try {
			UserController.getAllUsers().stream().forEach(user -> displayFunction(everyDetails, user));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void displayFunction(boolean everyDetails,User user) {
		if(everyDetails) {
//			 System.out.print((i++)+") ");
			 System.out.println("   Account No : "+((User) user).getAccountNo());
			 System.out.println("   Username   : "+user.getUsername()+" ");
			 System.out.println("   Name       : "+user.getName()+" ");
			 System.out.println("   Contact    : "+user.getContact()+" ");
			 System.out.println("   Address    : "+user.getAddress()+" ");
			 System.out.println("   Balance    : "+user.getBalance()+" ");
			 System.out.println();
		 }else {
//			 System.out.print((i++)+") ");
			 System.out.println("   Username   : "+user.getUsername()+" ");
		 }
	}
	
}
