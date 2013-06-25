package com.qsoft.longdt;

public class BankAccountDTO {

	private String accountNumber;
	private double balance;
	private double openTimestamp;

	public BankAccountDTO() {
		super();
	}

	public BankAccountDTO(String accountNumber, double balance,
			double openTimestamp) {
		super();
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.openTimestamp = openTimestamp;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getOpenTimestamp() {
		return openTimestamp;
	}

	public void setOpenTimestamp(double openTimestamp) {
		this.openTimestamp = openTimestamp;
	}
}
