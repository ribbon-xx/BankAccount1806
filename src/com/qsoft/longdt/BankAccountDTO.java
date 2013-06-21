package com.qsoft.longdt;

public class BankAccountDTO {

	private String accountNumber;
	private double balance;
	private long openTimestamp;
	
	public BankAccountDTO() {
		super();
	}

	public BankAccountDTO(String accountNumber, double balance,
			long openTimestamp) {
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

	public long getOpenTimestamp() {
		return openTimestamp;
	}

	public void setOpenTimestamp(long openTimestamp) {
		this.openTimestamp = openTimestamp;
	}
}
