package com.abc.transaction.validator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.abc.account.IAccount;
import com.abc.account.SavingsAccount;
import com.abc.exception.NotEnoughBalanceException;
import com.abc.exception.ValidationException;
import com.abc.transaction.Deposit;
import com.abc.transaction.Withdraw;

/**
 * 
 * @author Sanju Thomas
 *
 */
public class TestWithdrawValidator {
	
	private ITransactionValidator withdrawValidator;
	
	@Before
	public void setUp(){
		withdrawValidator = new WithdrawValidator();
	}

	@Test
	public void shouldValidateSuccessfully() throws ValidationException{
		final IAccount account = new SavingsAccount("S1");
		final Deposit deposit = new Deposit(100.00);
		account.process(deposit);
		withdrawValidator.validate(account, new Withdraw(10.00));
	}
	
	@Test(expected = NotEnoughBalanceException.class)
	public void shouldThrowNotEnoughBalanceException() throws ValidationException{
		final IAccount account = new SavingsAccount("S1");
		final Deposit deposit = new Deposit(100.00);
		account.process(deposit);
		withdrawValidator.validate(account, new Withdraw(101.00));
	}
	
	@Test()
	public void shouldGetValidExceptionMessage(){
		final IAccount account = new SavingsAccount("S1");
		final Deposit deposit = new Deposit(100.00);
		account.process(deposit);
		try {
			withdrawValidator.validate(account, new Withdraw(101.00));
		} catch (final ValidationException e) {
			final String message = e.getMessage();
			assertEquals("Unfortunately account doesn't have enough balance to serve the requested withdrwal! Available balance: 100.0Requested amount: 101.0", message);
		}
	}
}
