package com.qsoft.longdt;

public class BankAccount {

	private BankAccountDAO baDAO;
	private TransactionDAO tDAO;

	public BankAccountDTO openAccount(String accountNumber) {
		BankAccountDTO accountDTO = new BankAccountDTO(accountNumber);
		accountDTO.setBalance(0);
		accountDTO.setOpenTimestamp(System.currentTimeMillis());
		baDAO.doCreate(accountDTO);
		return accountDTO;
	}

	public BankAccount() {
		super();
	}

	public void setBankAccountDAO(BankAccountDAO BankAccountDAO) {
		this.baDAO = BankAccountDAO;
	}

	public BankAccountDTO getAccountByNumber(String accountNumber) {
		return baDAO.getAccountByNumber(accountNumber);
	}

	public void settDAO(TransactionDAO tDAO) {
		this.tDAO = tDAO;
	}

	public void deposite(BankAccountDTO accountDTO, float amount,
			String description, long timeStamp) {
		accountDTO.setBalance(accountDTO.getBalance() + amount);
		accountDTO.setDescription(description);
		accountDTO.setOpenTimestamp(timeStamp);
		baDAO.doUpdate(accountDTO);
		Transaction trans = new Transaction();
		trans.setTransactionDao(tDAO);
		trans.createTransaction(accountDTO.getAccountNumber(), amount,
				description, timeStamp);
	}

	public void withDraw(BankAccountDTO accountDTO, float amount,
			String description, long timeStamp) {
		accountDTO.setBalance(accountDTO.getBalance() - amount);
		accountDTO.setDescription(description);
		accountDTO.setOpenTimestamp(timeStamp);
		baDAO.doUpdate(accountDTO);
		Transaction trans = new Transaction();
		trans.setTransactionDao(tDAO);
		trans.createTransaction(accountDTO.getAccountNumber(), amount,
				description, timeStamp);
	}

}