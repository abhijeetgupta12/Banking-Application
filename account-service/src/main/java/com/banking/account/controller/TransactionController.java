package com.banking.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.account.dtos.AccountAndBalanceDTO;
import com.banking.account.service.TransactionService;

@RestController
@RequestMapping("/account-internal")
public class TransactionController {
	
	
	
	@Autowired
	TransactionService transactionService;

//	@PatchMapping("/debit")
//	public ResponseEntity<String> debitBalance(@RequestHeader("X-Auth-User") String userId,
//												@RequestBody AccountAndBalanceDTO accountDetails){
//		
//		String message = transactionService.debitAccBalance(userId,accountDetails);
//		
//		return new ResponseEntity<String>(message,HttpStatus.OK);
//			
//	}
//	
//	@PatchMapping("/credit")
//	public ResponseEntity<String> creditBalance(@RequestBody AccountAndBalanceDTO accountDetails){
//		
//
//		String message = transactionService.creditAccBalance(accountDetails);
//		
//		return new ResponseEntity<String>(message,HttpStatus.OK);
//			
//	}
	
	@PatchMapping("/transfer-balance")
	public ResponseEntity<String> transferBalance(@RequestHeader("X-Auth-User") String userId, 
														@RequestBody AccountAndBalanceDTO accountDetails){
		
		System.out.println("Inside transferBalance");
		String transMessage = transactionService.transferAmount(userId, accountDetails);
		
		return new ResponseEntity<String>(transMessage,HttpStatus.OK);
	}
	
	
	
	@GetMapping("/check-balance/{accNo}")
	public ResponseEntity<String> checkBalance(@RequestHeader("X-Auth-User") String userId, 
																		@PathVariable Long accNo){
		
		String curr_balance = transactionService.checkBalance(userId, accNo);
		
		return new ResponseEntity<String>(curr_balance,HttpStatus.OK);
	}
}
