package com.qsoft.longdt;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;

public class BankAccountTest extends TestCase {

	private BankAccount ba;
	private BankAccountDAO baDAO;
	private TransactionDAO tDAO;

	@Override
	protected void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		baDAO = mock(BankAccountDAO.class);
		tDAO = mock(TransactionDAO.class);
		ba = new BankAccount(baDAO);
		reset(baDAO);
		reset(tDAO);
	}

	@Test
	public void testOpenAccount() {
		BankAccountDTO baDTO = ba.openAccount("1234567890");
		baDTO.setBalance(0);
		assertEquals(0d, baDTO.getBalance(), 0.01);
		assertEquals("1234567890", baDTO.getAccountNumber());
	}

	@Test
	public void testInvokedoCreate() {
		BankAccountDTO account = ba.openAccount("12345567890");
		verify(baDAO).doCreate(account);
	}

	@Test
	public void testGetAccountReturnDTO() {
		String accNumber = "12345567890";
		ba.getAccountByNumber(accNumber);
		verify(baDAO).getAccountByNumber(accNumber);

	}

	@Test
	public void testDeposit() {
		String accNumber = "12345567890";
		BankAccountDTO account = ba.openAccount(accNumber);
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor
				.forClass(BankAccountDTO.class);

		ba.deposite(account, 200, "desc 200");

		verify(baDAO, times(1)).doUpdate(argument.capture());
		assertEquals(200.0, argument.getValue().getBalance(), 0.01);

		ba.deposite(account, -100, "desc -100");
		verify(baDAO, times(2)).doUpdate(argument.capture());
		assertEquals(100.0, argument.getValue().getBalance(), 0.01);
	}

	@Test
	public void testSaveAccountWithTimeStamp() {
		String accNumber = "12345567890";
		BankAccountDTO accountDTO = ba.openAccount(accNumber);

		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		ba.deposite(accountDTO, 100, "desc 100", 1352653200000l);
		verify(baDAO, times(1)).doUpdate(argument.capture());
		assertEquals(1352653200000l, argument.getValue().getOpenTimestamp(),
				0.01);
	}

	@Test
	public void testDepositeTransaction() {
		String accNumber = "12345567890";
		Transaction transaction = new Transaction();
		transaction.setTransactionDao(tDAO);
		transaction.createTransaction(accNumber, 100, "desc 100k",
				1352653200000l);

		ArgumentCaptor<TransactionDTO> argumentTransaction = ArgumentCaptor
				.forClass(TransactionDTO.class);
		verify(tDAO, times(1)).doUpdate(argumentTransaction.capture());
		assertEquals(accNumber, argumentTransaction.getValue()
				.getAccountNumber());
		assertEquals(100, argumentTransaction.getValue().getAmount(), 0.01);
		assertEquals("desc 100k", argumentTransaction.getValue()
				.getDescription());
		assertTrue(argumentTransaction.getValue().getTimestamp() != 0);
	}

	@Test
	public void testWithDraw() {
		String accNumber = "12345567890";
		BankAccountDTO accountDTO = ba.openAccount(accNumber);
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		ba.withDraw(accountDTO, 50, "desc -50k", 1352653200000l);
		verify(baDAO, times(1)).doUpdate(argument.capture());
		assertEquals(-50, argument.getValue().getBalance(), 0.01);
	}

	@Test
	public void testWithDrawTransaction() {
		Transaction transaction = new Transaction();
		transaction.setTransactionDao(tDAO);
		transaction.createTransaction("123456789", 50, "Withdraw 50k",
				1352653200000l);
		ArgumentCaptor<TransactionDTO> argumentTransaction = ArgumentCaptor
				.forClass(TransactionDTO.class);
		verify(tDAO).doUpdate(argumentTransaction.capture());
		assertEquals("123456789", argumentTransaction.getValue()
				.getAccountNumber());
		assertEquals(50, argumentTransaction.getValue().getAmount(), 0.01);
		assertEquals("Withdraw 50k", argumentTransaction.getValue()
				.getDescription());
		assertTrue(argumentTransaction.getValue().getTimestamp() != 0);
	}
}
