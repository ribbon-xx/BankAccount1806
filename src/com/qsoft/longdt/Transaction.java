package com.qsoft.longdt;

import java.util.ArrayList;

public class Transaction {
	private TransactionDAO transactionDao;

	public void setTransactionDao(TransactionDAO transactionDao) {
		this.transactionDao = transactionDao;
	}

	public TransactionDTO createTransaction(String accountNumber, float amount,
			String description, long timeStamp) {
		TransactionDTO transactionDTO = new TransactionDTO(accountNumber,
				amount, description, timeStamp);
		transactionDao.doUpdate(transactionDTO);
		return transactionDTO;
	}

	public ArrayList<TransactionDTO> getTransactionsOccurred(
			String accountNumber) {
		return transactionDao.getTransactionsOccurredDao(accountNumber);
	}

	public ArrayList<TransactionDTO> getTransactionsOccurred(
			String accountNumber, long startTime, long stopTime) {
		return transactionDao.getTransactionsOccurredDao(accountNumber,
				startTime, stopTime);
	}

	public ArrayList<TransactionDTO> getLastTransaction(String accountNumber) {
		return transactionDao.getLastTransactionDao(accountNumber);
	}
}