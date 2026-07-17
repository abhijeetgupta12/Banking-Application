package com.banking.account.service;

import com.banking.account.dtos.AccountAndBalanceDTO;

public interface TransactionService {
	
	String debitAccBalance(String userId, AccountAndBalanceDTO accountDetails);
	String creditAccBalance(AccountAndBalanceDTO accountDetails);
	String checkBalance(String userId, Long accNo);

}
