package com.qsoft.longdt;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class BankAccountTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testOpenAccount() {
		BankAccount ba = new BankAccount();
		String accountNumber = "1234567890";
		BankAccountDAO baDAO = mock(BankAccountDAO.class);
		when(baDAO.doCreate(accountNumber)).thenReturn(true);
		ba.openAccount(accountNumber);
		verify(baDAO).doCreate(accountNumber);
	}
}
