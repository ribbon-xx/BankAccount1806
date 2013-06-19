package com.qsoft.longdt;

public class BankAccountDTO {

	private String accountNumber;
	private double balance;
	private String openTimestamp;
	
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

	public String getOpenTimestamp() {
		return openTimestamp;
	}

	public void setOpenTimestamp(String openTimestamp) {
		this.openTimestamp = openTimestamp;
	}
}
