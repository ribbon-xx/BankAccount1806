package com.qsoft.longdt;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;
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

		Calendar cal = mock(Calendar.class);
		when(cal.getTimeInMillis()).thenReturn(1371798081880l);

		BankAccountDTO returnValue = new BankAccountDTO();
		returnValue.setAccountNumber("12345567890");
		returnValue.setBalance(0);
		returnValue.setOpenTimestamp(cal.getTimeInMillis());

		when(ba.getAccount("12345567890")).thenReturn(returnValue);

		BankAccountDTO account = ba.getAccount("12345567890");

		assertEquals("12345567890", account.getAccountNumber());
		assertEquals(0d, account.getBalance());
		assertEquals(1371798081880l, account.getOpenTimestamp());
	}

	@Test
	public void testGetAccountInvokedoRead() {
		BankAccountDTO account = ba.getAccount("12345567890");
		verify(baDAO).doRead("12345567890");
	}

	@Test
	public void testDepositUpdateAccount() {
		ArgumentCaptor<BankAccountDTO> baCapture = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		ArgumentCaptor<TransactionDTO> tranCapture = ArgumentCaptor
				.forClass(TransactionDTO.class);

		BankAccountDTO baDTO = ba.openAccount("1234567890", 0);

		ba.deposit(baDTO.getAccountNumber(), 100, "desc");

		verify(baDAO, times(1)).doCreate(baCapture.capture());
		List<BankAccountDTO> listBA = baCapture.getAllValues();
		assertEquals(listBA.get(0).getBalance(), 0.0, 0.01);

		verify(baDAO, times(1)).doUpdate(tranCapture.capture());
		assertEquals(tranCapture.getValue().getAmount(), 100.0, 0.01);
		assertEquals(tranCapture.getValue().getAccountNumber(), "1234567890");
		assertTrue(tranCapture.getValue().getTimestamp() != 0);
	}

}
