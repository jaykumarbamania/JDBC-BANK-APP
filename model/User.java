package bankApp.model;

import java.util.Date;

public class User {
	private int id;
	private String username;
	private String name;
	private String address;
	private String contact;
	private String password;
	private double InitialDep;
	private double balance;
	long accountNo = 0;
	private Date createdAt;
	
	public User(int id, String username, long accountNo, String name, String address, String contact, String password,
			double initialDep, double balance, Date createdAt) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.contact = contact;
		this.username = username;
		this.password = password;
		this.InitialDep = initialDep;
		this.balance = balance;
		this.accountNo = accountNo;
		this.createdAt = createdAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getInitialDep() {
		return InitialDep;
	}

	public void setInitialDep(double initialDep) {
		InitialDep = initialDep;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", name=" + name + ", address=" + address + ", contact="
				+ contact + ", password=" + password + ", InitialDep=" + InitialDep + ", balance=" + balance
				+ ", accountNo=" + accountNo + ", createdAt=" + createdAt + "]";
	}
	
	
	

}
