package com.banking.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.account.dtos.AccountAndBalanceDTO;
import com.banking.account.service.TransactionService;

@RestController
@RequestMapping("/account-internal")
public class TransactionController {
	
	
	
	@Autowired
	TransactionService transactionService;

	@PatchMapping("/debit")
	public ResponseEntity<String> debitBalance(Authentication auth,
												@RequestBody AccountAndBalanceDTO accountDetails){
		
		String userId = auth.getName();
		String message = transactionService.debitAccBalance(userId,accountDetails);
		
		return new ResponseEntity<String>(message,HttpStatus.OK);
			
	}
	
	@PatchMapping("/credit")
	public ResponseEntity<String> creditBalance(@RequestBody AccountAndBalanceDTO accountDetails){
		

		String message = transactionService.creditAccBalance(accountDetails);
		
		return new ResponseEntity<String>(message,HttpStatus.OK);
			
	}
	
	@GetMapping("/check-balance/{accNo}")
	public ResponseEntity<String> checkBalance(Authentication auth, @PathVariable Long accNo){
		
		String userId = auth.getName();
		String curr_balance = transactionService.checkBalance(userId, accNo);
		
		return new ResponseEntity<String>(curr_balance,HttpStatus.OK);
	}
}
