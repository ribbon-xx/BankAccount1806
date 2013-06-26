package com.qsoft.longdt;

public class BankAccount {

	private BankAccountDAO baDAO;

	public BankAccountDTO openAccount(String accountNumber) {
		BankAccountDTO accountDTO = new BankAccountDTO(accountNumber);
		baDAO.doCreate(accountDTO);
		return accountDTO;
	}

	public BankAccount(BankAccountDAO bankAccountDAO) {
		super();
		baDAO = bankAccountDAO;
	}

	public void setBankAccountDAO(BankAccountDAO BankAccountDAO) {
		this.baDAO = BankAccountDAO;
	}

	public BankAccountDTO getAccountByNumber(String accountNumber) {
		return baDAO.getAccountByNumber(accountNumber);
	}

	public void deposite(BankAccountDTO accountDTO, float amount,
			String description) {
		accountDTO.setBalance(accountDTO.getBalance() + amount);
		accountDTO.setDescription(description);
		baDAO.doUpdate(accountDTO);
	}

	public void deposite(BankAccountDTO accountDTO, float amount,
			String description, long timeStamp) {
		accountDTO.setBalance(accountDTO.getBalance() + amount);
		accountDTO.setDescription(description);
		accountDTO.setOpenTimestamp(timeStamp);
		baDAO.doUpdate(accountDTO);
	}

	public void withDraw(BankAccountDTO accountDTO, float amount,
			String description, long timeStamp) {
		accountDTO.setBalance(accountDTO.getBalance() - amount);
		accountDTO.setDescription(description);
		accountDTO.setOpenTimestamp(timeStamp);
		baDAO.doUpdate(accountDTO);
	}
}
