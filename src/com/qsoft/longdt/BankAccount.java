package com.qsoft.longdt;

public class BankAccount {

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

	public void openAccount(String accountNumber) {
		this.accountNumber = accountNumber;
		BankAccountDAO baDAO = new BankAccountDAO();
		baDAO.doCreate(this.accountNumber);
	}

	public void getAccount(String accountNumber) {

	}

	public void deposit(String accountNumber, double amount, String description) {

	}

	public void withdraw(String accountNumber, double amount, String description) {

	}

	public void getTransactionsOccurred(String accountNumber) {

	}
}
