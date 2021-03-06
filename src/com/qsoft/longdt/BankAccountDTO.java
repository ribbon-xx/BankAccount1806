package com.qsoft.longdt;

public class BankAccountDTO {

	private String accountNumber;
	private float balance;
	private Long openTimestamp;
	private String description;

	public BankAccountDTO(String accountNumber) {
		super();
		this.accountNumber = accountNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public Long getOpenTimestamp() {
		return openTimestamp;
	}

	public void setOpenTimestamp(Long openTimestamp) {
		this.openTimestamp = openTimestamp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
