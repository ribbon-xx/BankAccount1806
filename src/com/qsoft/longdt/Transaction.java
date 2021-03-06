package com.qsoft.longdt;

import java.util.ArrayList;

public class Transaction {
	private TransactionDAO tDAO;

	public void setTransactionDao(TransactionDAO tDAO) {
		this.tDAO = tDAO;
	}

	public TransactionDTO createTransaction(String accountNumber, float amount,
			String description, long timeStamp) {
		TransactionDTO transactionDTO = new TransactionDTO(accountNumber,
				amount, description, timeStamp);
		tDAO.doUpdate(transactionDTO);
		return transactionDTO;
	}

	public ArrayList<TransactionDTO> getTransactionsOccurred(
			String accountNumber) {
		return tDAO.getTransactionsOccurredDao(accountNumber);
	}

	public ArrayList<TransactionDTO> getTransactionsOccurred(
			String accountNumber, long startTime, long stopTime) {
		return tDAO.getTransactionsOccurredDao(accountNumber, startTime,
				stopTime);
	}

	public ArrayList<TransactionDTO> getLimitTransactions(String accountNumber,
			int limit) {
		return tDAO.getLimitTransactionDao(accountNumber, limit);
	}
}