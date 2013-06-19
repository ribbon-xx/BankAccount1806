package com.qsoft.longdt;

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

}
