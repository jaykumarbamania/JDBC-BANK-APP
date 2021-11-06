package bankApp.view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import bankApp.controller.BankExceptions;

public class App {

	public static void main(String[] args) throws Exception {

		App.displayHeader("SC Bank");
		ShowAccounts accounts = new ShowAccounts();

		BufferedReader brInp = new BufferedReader(new InputStreamReader(System.in));
		Scanner input = new Scanner(System.in);

		String choice = "0";
//		bank.displayChoice();
//Jaykumar

		while (!choice.equals("5")) {
			App.displayChoice();
			System.out.print("Enter your choice : ");
			choice = brInp.readLine();
			try {
				if (!Pattern.matches("[1-5]{1}", choice)) {
					throw new BankExceptions("Enter Above Choices only [1-5]");
				}
			} catch (BankExceptions e) {
				System.err.println(e);
			}
			switch (choice) {
			case "1":
				if (Register.Registeration()) {
					App.displayHeader("Registered Successfully");
				}
				break;
			case "2":

				Login.LoginProcess();


				break;
			case "3":
//				Update update = new Update();
//				App.displayHeader("Update Accounts");
//				dispRef.details(false);
//				System.out.print("Enter username : ");
//				String updtUser = brInp.readLine();
//				boolean userFound = update.verifyUser(updtUser);
//				while (userFound != true) {
//					System.out.print("\nEnter again : ");
//					updtUser = brInp.readLine();
//					userFound = update.verifyUser(updtUser);
//				}
//				update.updateUser(updtUser);
//				update.Registeration();
				break;
			case "4":
				App.displayHeader("All Registered Accounts");
				accounts.displayDetails(true);
				break;
			case "5":
				App.displayHeader("THANK YOU");
				break;
			default:
				break;
			}

		}
		input.close();

	}

	static void displayChoice() {
		String choicesArr[] = { "Register account.", "Login.", "Update accounts.", "Show Accounts.", "Exit." };
		IntStream.range(0, choicesArr.length)
				.forEach(index -> System.out.println((index + 1) + "  " + choicesArr[index]));
	}

	static void displayHeader(String name) {
		System.out.println("----------------------");
		System.out.println(name);
		System.out.println("----------------------");
	}

}
