package bankApp.controller;
public class BankExceptions extends Exception {

	private static final long serialVersionUID = 1L;
	String msg;

	public BankExceptions(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		return msg;
	}
	
}
