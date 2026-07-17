package com.banking.account.service;

import java.util.List;

import com.banking.account.dtos.AccountStausUpdateDTO;
import com.banking.account.entity.Account;

public interface AccountService {
	
	Account createAccount(String userName, String acc_type);
	Account getAccountDetailsByAccNo(String userId, Long accNo);
	List<Account> getAllAccounts();
	String updateAccStatus(String userId, AccountStausUpdateDTO accStatReqDTO);
	

}
