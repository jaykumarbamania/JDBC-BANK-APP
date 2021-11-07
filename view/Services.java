package bankApp.view;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

import bankApp.controller.BankExceptions;
import bankApp.controller.UserController;
import bankApp.model.User;



public class Services {
	String ch;
	User currentUser = null;
	
	boolean performServices(String ch) throws IOException {
		currentUser = Login.getLoginnedUser();
		Scanner input = new Scanner(System.in);
		
//		PaymentInterface paymentRef = Payments::new; 
		switch (ch) {
		
		case "1":
			
			Services.deposit(currentUser.getUsername());
			break;
			
		case "2":
			Services.withdraw(currentUser.getUsername());
			break;
			
		case "3":
			System.out.println("Intial Deposit : "+ currentUser.getInitialDep());
			currentUser.setBalance(UserController.getBalance(currentUser.getUsername()));
			System.out.println("Current User Balance : "+currentUser.getBalance());
			break;
		case "4":
			
			return true;
		case "5":
			System.out.println("Accountholder name : "+currentUser.getName());
			System.out.println("Accountholder address : "+currentUser.getAddress());
			System.out.println("Accountholder contact : "+currentUser.getContact());
			return true;
		case "6":
			Login.setLoginnedUser(null);
			App.displayHeader("Logout Successfully");
			return false;
		case "7":
//			Payments getPayments = new Payments();
//			getPayments.showPayments();
//			return true;
		default:
			break;
		}
		
		return true;
		
	}
	
	private static void deposit(String user) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter Amount : ");
		double amt=0;
		try {
			amt = input.nextDouble();
			if(!Pattern.matches(".*[^0-9].*", Double.toString(amt))) {
				throw new BankExceptions("Enter proper numbers for amount");
			}
			UserController.updateUserBalance(user, amt, true); // true - for credit the amount
		}catch(BankExceptions e) {
			System.err.println(e);
		}
	}
	
	private static void withdraw(String user) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter Payee username : ");
		String payeeName = input.next();
		System.out.print("Enter Amount : ");
		try {
			double transferAmt = input.nextDouble();
			if(!Pattern.matches(".*[^0-9].*", Double.toString(transferAmt))) {
				throw new BankExceptions("Enter proper numbers for amount");
			}
			if(payeeName.equals(user)) {
				throw new BankExceptions("That's your username");
			}
			if(UserController.getUser(payeeName) == null) {
				throw new BankExceptions("User doesn't exists");
			}
			else {
				UserController.updateUserBalance(user, transferAmt, false); // false - for debit the amount
				UserController.updateUserBalance(payeeName, transferAmt, true); // false - for debit the amount
				App.displayHeader("Transaction Successfull");
			}
		}catch(BankExceptions e) {
			System.err.println(e);
		}
		

	}
}
