package bankApp.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Payment {
	private String sender;
	private String receiver;
	private double amount;
	private double senderBal;
	private double receiverBal;
	private String createdAt;
	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
	public Payment(String sender, String receiver, double amount, double senderBal, double receiverBal,
			Date createdAt) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
		this.senderBal = senderBal;
		this.receiverBal = receiverBal;
		this.createdAt =   String.format(dateFormat.format(createdAt));
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getSenderBal() {
		return senderBal;
	}
	public void setSenderBal(double senderBal) {
		this.senderBal = senderBal;
	}
	public double getReceiverBal() {
		return receiverBal;
	}
	public void setReceiverBal(double receiverBal) {
		this.receiverBal = receiverBal;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = String.format(dateFormat.format(createdAt));
	}
	@Override
	public String toString() {
		return "Payment [sender=" + sender + ", receiver=" + receiver + ", amount=" + amount + ", senderBal="
				+ senderBal + ", receiverBal=" + receiverBal + ", createdAt=" + createdAt + "]";
	}
	
	
	
}
