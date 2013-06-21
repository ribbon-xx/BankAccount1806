package com.qsoft.longdt;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Times;

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
		ba.deposit("1234567890", 100, "deposit");

		ArgumentCaptor<TransactionDTO> argCaptor = ArgumentCaptor
				.forClass(TransactionDTO.class);
		verify(baDAO, times(1)).doUpdate(argCaptor.capture());
		assertEquals(argCaptor.getValue().getAmount(), 100, 0.01);
		assertEquals(argCaptor.getValue().getAccountNumber(), "1234567890");
		assertTrue(argCaptor.getValue().getTimestamp() != 0);
	}

}
