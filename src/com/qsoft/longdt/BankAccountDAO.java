package com.qsoft.longdt;

public class BankAccountDAO {

	public boolean doCreate(BankAccountDTO dto) {

		return false;
	}

	public BankAccountDTO doRead(String accountNumber) {
		return null;
	}

	public BankAccountDTO doUpdate(TransactionDTO dto) {
		BankAccountDTO baDTO = new BankAccountDTO();
		baDTO.setAccountNumber(dto.getAccountNumber());
		baDTO.setBalance(baDTO.getBalance() + dto.getAmount());
		baDTO.setOpenTimestamp(1245663576);
		return baDTO;
	}

	public void doDelete() {
		return;
	}
}
