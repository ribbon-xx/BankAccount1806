package com.qsoft.longdt;

public class TransactionDTO {

	private String accountNumber;
	private double amount;
	private String description;
	private double timestamp;

	public TransactionDTO() {
		super();
	}

	public TransactionDTO(String accountNumber, double amount,
			String description, double timestamp) {
		super();
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.description = description;
		this.timestamp = timestamp;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(double timestamp) {
		this.timestamp = timestamp;
	}

}
