package bankApp.view;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import bankApp.controller.BankExceptions;
import bankApp.controller.UserController;
import bankApp.model.User;

public class Update {

	static void displayUpdateChoice() {
		String choicesArr[] = { "Name ", "Username", "Address", "Contact No", "Password", "Done" };
		for (int i = 0; i < choicesArr.length; i++) {
			System.out.println((i + 1) + "  " + choicesArr[i]);
		}
	}

	public void updateProcess() {
		BufferedReader brInp = new BufferedReader(new InputStreamReader(System.in));
		ShowAccounts userList = new ShowAccounts();
		userList.displayDetails(false);
		try {
			System.out.print("Enter username : ");
			String updtUser = brInp.readLine();
			boolean userFound = verifyUser(updtUser);
			while (userFound != true) {
				System.out.print("\nEnter again : ");
				updtUser = brInp.readLine();
				userFound = verifyUser(updtUser);
			}
			updateUser(updtUser);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateUser(String username) throws Exception {
		App.displayHeader("Update Account of " + username);
		User updatedUser = null;
		User uptUser = Update.UserDetail(username, true);
		BufferedReader brInp = new BufferedReader(new InputStreamReader(System.in));
		boolean verify = false;
		String updCh = "";

		while (!updCh.equals("6")) {
			Update.displayUpdateChoice();
			System.out.print("Select choice to update : ");
			updCh = brInp.readLine();
			try {
				if (!Pattern.matches("[1-6]{1}", updCh)) {
					throw new BankExceptions("Enter Above Choices only [1-6]");
				}
			} catch (BankExceptions e) {
				System.err.println(e);
			}
			switch (updCh) {

			case "1":
				System.out.println("Current Name : " + uptUser.getName());
				System.out.print("Enter New Name : ");
				String newName = brInp.readLine();
				uptUser.setName(newName);
				updatedUser = Update.updateUserDetails(uptUser, username, 1);
				App.displayHeader("Updated Successfully to " + updatedUser.getName());

				break;
			case "2":
				System.out.println("Current Username : " + uptUser.getUsername());
				System.out.print("Enter New Username : ");
				String newUserName = brInp.readLine();
				verify = Register.verifyUsername(newUserName);
				while (verify != true) {
					System.out.print("Try something new : ");
					newUserName = brInp.readLine();
					verify = Register.verifyUsername(newUserName);
				}
				uptUser.setUsername(newUserName);
				Update.updateUserDetails(uptUser, username, 2);
				App.displayHeader("Updated Successfully to " + updatedUser.getUsername());
				break;
			case "3":
				System.out.println("Current Address : " + uptUser.getAddress());
				System.out.print("Enter New Address : ");
				String newAddr = brInp.readLine();
				uptUser.setAddress(newAddr);
				Update.updateUserDetails(uptUser, username, 3);
				App.displayHeader("Updated Successfully to " + updatedUser.getAddress());
				break;
			case "4":
				System.out.println("Current Mobile No : " + uptUser.getContact());
				System.out.print("Enter New Mobile No. : ");
				String newContact = brInp.readLine();
				verify = Register.validateMob(newContact);
				while (verify != true) {
					System.out.print("Enter Mobile Number Again : ");
					newContact = brInp.readLine();
					verify = Register.validateMob(newContact);
				}
				uptUser.setContact(newContact);
				Update.updateUserDetails(uptUser, username, 4);
				App.displayHeader("Updated Successfully to " + updatedUser.getContact());
				break;
			case "5":
				System.out.println("Current Password : *****");
				System.out.print("Confirm Old Password : ");
				String confirmPassword = brInp.readLine();

				while (verify != true) {
					System.err.println("Password Not Matched..!! ");
					System.out.print("Try Again : ");
					confirmPassword = brInp.readLine();
					verify = verifyPassword(confirmPassword, uptUser.getUsername());
				}
				System.out.print("Password Matched..!!");
				System.out.print("Enter New Password : ");
				String newPassword = brInp.readLine();
				verify = Register.passwordValidation(newPassword);
				while (verify != true) {
					System.err.print("Set Again : ");
					newPassword = brInp.readLine();
					verify = Register.passwordValidation(newPassword);
				}
				uptUser.setPassword(newPassword);
				Update.updateUserDetails(uptUser, username, 5);
				App.displayHeader("Password Updated Successfully ");
				break;
			case "6":
				App.displayHeader("Thank You");
				App.displayHeader("SC Bank ");
				return;
			}
		}

	}

	static User updateUserDetails(User uptUser, String username, int mode) throws FileNotFoundException, IOException {
		List<User> usrList = new ArrayList<User>();

		ObjectInputStream ois = null;
		String File = "resources/users.db";
		try {
			ois = new ObjectInputStream(new FileInputStream(File));
			while (true) {
				User user = (User) ois.readObject();
				usrList.add(user);
			}

		} catch (EOFException e) {
			System.out.println("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		new FileOutputStream(File).close();

		for (User user : usrList) {
			if (user.getUsername().equals(username)) {
				switch (mode) {
				case 1:
					user.setName(uptUser.getName());
					break;
				case 2:
					user.setUsername(uptUser.getUsername());
					break;
				case 3:
					user.setAddress(uptUser.getAddress());
					break;
				case 4:
					user.setContact(uptUser.getContact());
					break;
				case 5:
					user.setPassword(uptUser.getPassword());
					break;
				}
				uptUser = user;
			}
		}
		ObjectOutputStream oos = null;
		oos = new ObjectOutputStream(new FileOutputStream(File, true));
		for (User user : usrList)
			oos.writeObject(user);
		oos.close();

		return uptUser;
	}

	static User UserDetail(String username, boolean printDetails) {
		User uptUser = null;
		ObjectInputStream ois = null;
		String inputFile = "resources/userData.db";
		try {
			ois = new ObjectInputStream(new FileInputStream(inputFile));
			while (true) {
				User user = (User) ois.readObject();
				if (user.getUsername().equals(username)) {
					uptUser = user;
				}
			}
		} catch (EOFException e) {

		} catch (Exception e) {

		} finally {
			try {
				if (ois != null)
					ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (printDetails) {
			System.out.println("Username : " + uptUser.getUsername());
			System.out.println("Account No : " + uptUser.getAccountNo());
			System.out.println("Name : " + uptUser.getName());
			System.out.println("Address : " + uptUser.getAddress());
			System.out.println("Contact : " + uptUser.getContact());
			System.out.println("Intitial Deposit : " + uptUser.getInitialDep() + "\t Balance" + uptUser.getBalance());
		}

		return uptUser;

	}

	private static boolean verifyUser(String username) {
		try {
			if (UserController.getUser(username) == null)
				throw new BankExceptions("Username doesn't exist.");
			else
				return true;
		} catch (BankExceptions e) {
			System.err.println(e);
			return false;
		}
	}

	static boolean verifyPassword(String password, String username) {

		User u = Update.UserDetail(username, false);
		if (u.getPassword().equals(password))
			return true;

		return false;
	}
}
