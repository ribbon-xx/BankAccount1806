package com.qsoft.longdt;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;

public class BankAccountTest extends TestCase {

	private BankAccount ba;
	private BankAccountDAO baDAO;

	@Override
	protected void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		baDAO = mock(BankAccountDAO.class);
		ba = new BankAccount(baDAO);
	}

	@Test
	public void testOpenAccount() {
		BankAccountDTO baDTO = ba.openAccount("1234567890", 0);
		assertEquals(0d, baDTO.getBalance());
		assertEquals("1234567890", baDTO.getAccountNumber());
	}

	@Test
	public void testInvokedoCreate() {
		BankAccountDTO account = ba.openAccount("12345567890", 0);
		verify(baDAO).doCreate(account);
	}

	@Test
	public void testGetAccountReturnDTO() {

		String accNumber = "12345567890";
		BankAccountDTO accOpen = ba.openAccount(accNumber, 0);

		when(ba.getAccount(accOpen.getAccountNumber())).thenReturn(accOpen);

		BankAccountDTO account = ba.getAccount(accNumber);

		assertEquals(accNumber, account.getAccountNumber());
		assertEquals(0d, account.getBalance());
	}

	@Test
	public void testGetAccountInvokedoRead() {
		BankAccountDTO account = ba.getAccount("12345567890");
		verify(baDAO).doRead("12345567890");
	}

	@Test
	public void testDepositUpdateAccount() {
		String accNumber = "12345567890";

		ArgumentCaptor<BankAccountDTO> baCapture = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		ArgumentCaptor<TransactionDTO> tranCapture = ArgumentCaptor
				.forClass(TransactionDTO.class);

		BankAccountDTO baDTO = ba.openAccount(accNumber, 0);

		when(ba.getAccount(accNumber)).thenReturn(baDTO);

		ba.deposit(baDTO.getAccountNumber(), 100, "desc");

		verify(baDAO, times(1)).doCreate(baCapture.capture());
		List<BankAccountDTO> listBA = baCapture.getAllValues();
		assertEquals(listBA.get(0).getBalance(), 0.0, 0.01);

		verify(baDAO, times(1)).doUpdate(tranCapture.capture());
		assertEquals(tranCapture.getValue().getAmount(), 100.0, 0.01);
		assertEquals(tranCapture.getValue().getAccountNumber(), accNumber);
		assertTrue(tranCapture.getValue().getTimestamp() != 0);
	}

	@Test
	public void testWithDrawUpdateAccount() {
		String accNumber = "12345567890";
		double amount = 50d;

		BankAccountDTO baDTO = ba.openAccount(accNumber, 100);

		ArgumentCaptor<BankAccountDTO> openCapture = ArgumentCaptor
				.forClass(BankAccountDTO.class);

		ArgumentCaptor<TransactionDTO> withdrawCapture = ArgumentCaptor
				.forClass(TransactionDTO.class);

		when(
				baDAO.doUpdate(new TransactionDTO(accNumber, amount, "des",
						111111d))).thenReturn(
				new BankAccountDTO(baDTO.getAccountNumber(), baDTO.getBalance()
						- amount, 111111d));

		BankAccountDTO baDTOUpdated = ba.withdraw(baDTO.getAccountNumber(),
				50, "desc");

		verify(baDAO, times(1)).doCreate(openCapture.capture());
		assertEquals(openCapture.getValue().getBalance(), 100f, 0.01);

		verify(baDAO, times(1)).doUpdate(withdrawCapture.capture());
		assertEquals(withdrawCapture.getValue().getAmount(), 50f, 0.01);

		assertEquals(50f, baDTOUpdated.getBalance(), 0.01);

	}

	@Test
	public void testWithDrawSaveToDatabase() {
		String accNumber = "12345567890";
	}

}
