package com.qsoft.longdt;

import java.util.Calendar;

public class BankAccount {

	private BankAccountDAO baDAO;

	public BankAccount(BankAccountDAO baDAO) {
		super();
		this.baDAO = baDAO;
	}

	public BankAccountDTO openAccount(String accountNumber, double balance) {
		BankAccountDTO bankAccountDTO = new BankAccountDTO();
		bankAccountDTO.setAccountNumber(accountNumber);
		bankAccountDTO.setBalance(balance);
		baDAO.doCreate(bankAccountDTO);
		return bankAccountDTO;
	}

	public BankAccountDTO getAccount(String accountNumber) {
		BankAccountDTO bankAccountDTO = new BankAccountDTO();
		bankAccountDTO = baDAO.doRead(accountNumber);
		return bankAccountDTO;
	}

	public BankAccountDTO deposit(String accountNumber, long amount,
			String description) {
		BankAccountDTO baDTO = getAccount(accountNumber);
		TransactionDTO tranDTO = new TransactionDTO(baDTO.getAccountNumber(),
				baDTO.getBalance() + amount, description, Calendar
						.getInstance().getTimeInMillis());
		return baDAO.doUpdate(tranDTO);
	}

	public BankAccountDTO withdraw(String accountNumber, long amount,
			String description) {
		// TransactionDTO tranDTO = new TransactionDTO(accountNumber, amount,
		// description, Calendar.getInstance().getTimeInMillis());
		return baDAO.doUpdate(new TransactionDTO(accountNumber, amount,
				description, Calendar.getInstance().getTimeInMillis()));
	}
}
