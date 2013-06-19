package com.qsoft.longdt;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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

}
