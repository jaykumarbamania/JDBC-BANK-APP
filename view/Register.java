package bankApp.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bankApp.controller.BankExceptions;
import bankApp.controller.UserController;
import bankApp.model.User;


public class Register {

	public static boolean Registeration() throws IOException {
		
		boolean verify = false;
		BufferedReader brInp = new BufferedReader(new InputStreamReader(System.in));
	
		System.out.print("Enter Name : ");
		String name = brInp.readLine();

		System.out.print("Enter address : ");
		String address = brInp.readLine();

		System.out.print("Enter Mobile number : ");
		String contact = brInp.readLine();
		verify = Register.validateMob(contact);
		while (verify != true) {
			System.out.print("Enter Mobile Number Again : ");
			contact = brInp.readLine();
			verify = Register.validateMob(contact);
			
		}

		System.out.print("Set Username : ");
		String username = brInp.readLine();
		verify = Register.verifyUsername(username);
		while (verify != true) {
			System.out.print("Try something new : ");
			username = brInp.readLine();
			verify = Register.verifyUsername(username);
		}

		System.out.println("Set a  Password with matching condition : ");
		System.out.println("(minimum 8 chars, minimum 1 digit, 1 lowercase, 1 uppercase, 1 special character[!@#$%^&*_] )");
		System.out.print("Enter Password : ");
		String password = brInp.readLine();
		verify = passwordValidation(password);
		while (verify != true) {
			System.out.println("( minimum 8 chars, minimum 1 digit, 1 lowercase, 1 uppercase, 1 special character[!@#$%^&*_] )");
			System.out.print("Set Again : ");
			password = brInp.readLine();
			verify = passwordValidation(password);
		}

		System.out.print("Enter Initial Deposit : ");
		String InitialDep = brInp.readLine();
		
		double initial = Double.parseDouble(InitialDep);
		
		long accountNo = (long) Math.floor(Math.random() * 9000000000L) + 1000000000L;
		
		try {
			int result = UserController.insertUser(username,accountNo,name, address, contact, password, initial);
			if(result == 1) return true;
			else return false;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	static boolean passwordValidation(String password) {
		String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[!@#$%^&*_])" + "(?=\\S+$).{8,20}$";
		Pattern p = Pattern.compile(regex);
		try {

			if (password == "") {
				throw new BankExceptions("Must Enter Password");
			} else {
				Matcher matcher = p.matcher(password);
//				System.out.println(matcher.matches());
				if (!matcher.matches()) {
					throw new BankExceptions("Invalid Password Condition");
				}
			}
		} catch (BankExceptions e) {
			System.err.println(e+"\n");
			return false;
		}
		return true;
	}
	
	public static boolean validateMob(String mob) {
		String regex = "^\\d{10}$";
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(mob);
		try {
			if (mob == "") 
				throw new BankExceptions("Must Enter Mobile No");
			
			if(mob.length() != 10) 
				throw new BankExceptions("Must Enter 10 digits");
			
			if (!matcher.matches()) 
				throw new BankExceptions("Enter only digits");
			
			else 
				return true;
			
		} catch (BankExceptions e) {
			System.err.println("\n"+e);
			return false;
		}
	}
	
	static boolean verifyUsername(String username) {
		User u = null;
		u = UserController.getUser(username);
		
		try {
			if(u != null) {
				throw new BankExceptions("This username is not available");
			}
		}catch(BankExceptions e) {
			System.err.println(e);
			return false;
		}
		return true;
	}
}

