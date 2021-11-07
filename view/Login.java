package bankApp.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

import bankApp.controller.UserController;
import bankApp.model.User;

public class Login {

	private static User loginnedUser;

	public static void LoginProcess() throws IOException {
		BufferedReader brInp = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter username : ");
		String username = brInp.readLine();

		System.out.print("Enter Password : ");
		String password = brInp.readLine();

		boolean auth = Login.checkUserLogin(username, password);
		if (auth) {
			App.displayHeader("W E L C O M E");
			Login.displayLoginedChoice();
			System.out.print("Enter your choice : ");
			Services ops = new Services();
			ops.ch = brInp.readLine();
			boolean notLogOut = ops.performServices(ops.ch);
			while (notLogOut != false) {
				Login.displayLoginedChoice();
				System.out.print("Enter your choice : ");
				ops.ch = brInp.readLine();
				notLogOut = ops.performServices(ops.ch);
			}
		} else {
			System.err.println("Invalid Credentials");
			App.displayHeader("Login Again");
		}
	}

	private static boolean checkUserLogin(String username, String password) {

		boolean logined = false;
		User u = UserController.userLogin(username, password);
		if (u != null) {
			System.out.println(u.toString());
			logined = true;
			Login.setLoginnedUser(u);
			System.out.println("Login Successfully");
		}
		return logined;

}


	public static void displayLoginedChoice() {
		String choicesArr[] = { "Deposit.", "Transfer.", "Show Balance", "Last 5 Transactions.", "User Information.",
				"Log out", "Show Paymnets" };
		IntStream.range(0, choicesArr.length)
				.forEach(index -> System.out.println((index + 1) + "  " + choicesArr[index]));
	}

	public static User getLoginnedUser() {
		return loginnedUser;
	}

	public static void setLoginnedUser(User loginnedUser) {
		Login.loginnedUser = loginnedUser;
	}
}
