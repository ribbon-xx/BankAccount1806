package com.qsoft.longdt;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;

public class BankAccountTest extends TestCase {

	private BankAccount ba;
	private Transaction trans;
	private BankAccountDAO baDAO;
	private TransactionDAO tDAO;
	private String accNumber;
	private Calendar mockCal;

	@Override
	protected void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		baDAO = mock(BankAccountDAO.class);
		tDAO = mock(TransactionDAO.class);
		mockCal = mock(Calendar.class);
		ba = new BankAccount();
		ba.setBankAccountDAO(baDAO);
		ba.settDAO(tDAO);
		trans = new Transaction();
		trans.setTransactionDao(tDAO);
		accNumber = "12345567890";
		reset(baDAO);
		reset(tDAO);
	}

	@Test
	public void testOpenAccount() {
		BankAccountDTO baDTO = ba.openAccount(accNumber);
		baDTO.setBalance(0);
		assertEquals(0d, baDTO.getBalance(), 0.01);
		assertEquals(accNumber, baDTO.getAccountNumber());
	}

	@Test
	public void testInvokedoCreate() {
		BankAccountDTO account = ba.openAccount(accNumber);
		verify(baDAO).doCreate(account);
	}

	@Test
	public void testGetAccountReturnDTO() {
		ba.getAccountByNumber(accNumber);
		verify(baDAO).getAccountByNumber(accNumber);

	}

	@Test
	public void testSaveAccountWithTimeStamp() {
		BankAccountDTO accountDTO = ba.openAccount(accNumber);

		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		ba.deposite(accountDTO, 100, "desc 100", 1352653200000l);
		verify(baDAO, times(1)).doUpdate(argument.capture());
		assertEquals(1352653200000l, argument.getValue().getOpenTimestamp(),
				0.01);
	}

	@Test
	public void testDeposit() {
		BankAccountDTO account = ba.openAccount(accNumber);
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor
				.forClass(BankAccountDTO.class);

		ba.deposite(account, 200, "de 200", 123123123l);

		verify(baDAO, times(1)).doUpdate(argument.capture());
		assertEquals(200.0, argument.getValue().getBalance(), 0.01);

		ba.deposite(account, -100, "de -100", 123123123l);
		verify(baDAO, times(2)).doUpdate(argument.capture());
		assertEquals(100.0, argument.getValue().getBalance(), 0.01);
	}

	@Test
	public void testDepositeTransaction() {
		trans.createTransaction(accNumber, 100, "de 100k", 123123123l);

		ArgumentCaptor<TransactionDTO> argumentTransaction = ArgumentCaptor
				.forClass(TransactionDTO.class);
		verify(tDAO, times(1)).doUpdate(argumentTransaction.capture());
		assertEquals(accNumber, argumentTransaction.getValue()
				.getAccountNumber());
		assertEquals(100, argumentTransaction.getValue().getAmount(), 0.01);
		assertEquals("de 100k", argumentTransaction.getValue().getDescription());
		assertTrue(argumentTransaction.getValue().getTimestamp() != 0);
	}

	@Test
	public void testWithDraw() {
		BankAccountDTO accountDTO = ba.openAccount(accNumber);
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		ba.withDraw(accountDTO, 50, "wd -50k", 1352653200000l);
		verify(baDAO, times(1)).doUpdate(argument.capture());
		assertEquals(-50, argument.getValue().getBalance(), 0.01);
	}

	@Test
	public void testWithDrawTransaction() {
		trans.createTransaction(accNumber, 50, "wd 50k", 1352653200000l);
		ArgumentCaptor<TransactionDTO> argumentTransaction = ArgumentCaptor
				.forClass(TransactionDTO.class);
		verify(tDAO).doUpdate(argumentTransaction.capture());
		assertEquals(accNumber, argumentTransaction.getValue()
				.getAccountNumber());
		assertEquals(50, argumentTransaction.getValue().getAmount(), 0.01);
		assertEquals("wd 50k", argumentTransaction.getValue().getDescription());
		assertTrue(argumentTransaction.getValue().getTimestamp() != 0);
	}

	@Test
	public void testGetHistory() {
		ArgumentCaptor<String> argumentTransaction = ArgumentCaptor
				.forClass(String.class);
		trans.getTransactionsOccurred(accNumber);
		verify(tDAO, times(1)).getTransactionsOccurredDao(
				argumentTransaction.capture());
		assertEquals(accNumber, argumentTransaction.getValue());
	}

	@Test
	public void testGetHistoryWithPeriodTime() {
		trans.getTransactionsOccurred(accNumber, 12312313l, 456456456l);
		verify(tDAO, times(1)).getTransactionsOccurredDao(accNumber, 12312313l,
				456456456l);
	}

	@Test
	public void testGetHistoryWithLimitedNumber() {
		trans.getLimitTransactions(accNumber, 5);
		verify(tDAO, times(1)).getLimitTransactionDao(accNumber, 5);
	}

	@Test
	public void testSaveTimestampWhenOpenAccount() {
		ArgumentCaptor<BankAccountDTO> argBADTO = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		ba.openAccount(accNumber);
		Long timeStamp = System.currentTimeMillis();
		when(mockCal.getTimeInMillis()).thenReturn(timeStamp);		
		verify(baDAO, times(1)).doCreate(argBADTO.capture());
		assertTrue(argBADTO.getValue().getOpenTimestamp() != null);
		assertEquals(timeStamp, argBADTO.getValue().getOpenTimestamp());
	}
}