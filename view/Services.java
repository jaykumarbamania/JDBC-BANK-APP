package bankApp.view;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import bankApp.controller.BankExceptions;
import bankApp.controller.PaymentController;
import bankApp.controller.UserController;
import bankApp.model.Payment;
import bankApp.model.User;



public class Services {
	String ch;
	User currentUser = null;
	
	boolean performServices(String ch) throws IOException {
		currentUser = Login.getLoginnedUser();
		String currentUsername = currentUser.getUsername();
//		PaymentInterface paymentRef = Payments::new; 
		switch (ch) {
		
		case "1": //deposit
			Services.deposit(currentUsername);
			break;
			
		case "2": //transferring
			Services.transfer(currentUsername);
			break;
			
		case "3": //show Balance
			System.out.println("Intial Deposit : "+ currentUser.getInitialDep());
			currentUser.setBalance(UserController.getBalance(currentUsername));
			System.out.println("Current User Balance : "+currentUser.getBalance());
			break;
		case "4":
			Services.userPayments(currentUsername);
			System.out.println(String.format("Intital Deposit - Rs "+currentUser.getInitialDep()+" as on "+currentUser.getCreatedAt()));
			break;
		case "5":
			System.out.println("Accountholder name : "+currentUser.getName());
			System.out.println("Accountholder address : "+currentUser.getAddress());
			System.out.println("Accountholder contact : "+currentUser.getContact());
			break;
		case "6":
			Login.setLoginnedUser(null);
			App.displayHeader("Logout Successfully");
			return false;
		case "7":
			try {
				PaymentController.getAllPayments().stream().forEach(p -> System.out.println(p.toString()));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		
		return true;
		
	}
	
	private static void deposit(String user) {
		try (Scanner input = new Scanner(System.in)) {
			System.out.print("Enter Amount : ");
			double amt=0;
			try {
				amt = input.nextDouble();
				if(!Pattern.matches(".*[^0-9].*", Double.toString(amt))) {
					throw new BankExceptions("Enter proper numbers for amount");
				}
				UserController.updateUserBalance(user, amt, true); // true - for credit the amount
				PaymentController.storePayments(user, "", amt, UserController.getBalance(user), 0);
				
			}catch(BankExceptions e) {
				System.err.println(e);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void transfer(String user) {
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
				UserController.updateUserBalance(payeeName, transferAmt, true); // true - for credit the amount
				PaymentController.storePayments(user, payeeName, -transferAmt, UserController.getBalance(user), UserController.getBalance(payeeName));
				App.displayHeader("Transaction Successfull");
			}
		}catch(BankExceptions e) {
			System.err.println(e);
		}
	}
	
	private static void userPayments(String user) {
		try {
			List<Payment> lastFivePayments = PaymentController.getUserPayments(user).stream().collect(Collectors.toList());
			lastFivePayments.stream().limit(5).forEach(payment ->{
				if(payment.getAmount()>0 && (payment.getReceiver().equals(user) || payment.getSender().equals(user) )) {
					System.out.println(String
							.format("Rs." + Math.abs(payment.getAmount()) + " credited to your account. Balance - Rs. "
									+ (payment.getSenderBal()) + " as on " + payment.getCreatedAt()));
				}
				if(payment.getAmount()<0) {
					if(payment.getReceiver().equals(user)) {
						System.out.println(String
								.format("Rs." + Math.abs(payment.getAmount()) + " credited to your account. Balance - Rs. "
										+ (payment.getReceiverBal()) + " as on " + payment.getCreatedAt()));
					}else {
						System.out.println(String
								.format("Rs." + Math.abs(payment.getAmount()) + " debited from your account. Balance - Rs. "
										+ (payment.getSenderBal()) + " as on " + payment.getCreatedAt()));
					}
				}
			});
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
