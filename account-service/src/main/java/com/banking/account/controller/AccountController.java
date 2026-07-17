package com.banking.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.account.dtos.AccountRequestDTO;
import com.banking.account.dtos.AccountStausUpdateDTO;
import com.banking.account.entity.Account;
import com.banking.account.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {
	
	@Autowired
	private AccountService accService;

	@PostMapping("/create-account")
	public ResponseEntity<Account> createAccount(@RequestHeader("X-Auth-User") String userId,
														@RequestBody AccountRequestDTO accReqDTO){
		System.out.println(userId);
		Account addedAcc = accService.createAccount(userId,accReqDTO.getAcc_type());
		return new ResponseEntity<>(addedAcc,HttpStatus.CREATED);
	}
	
	@GetMapping("/account-details/{accNo}")
	public ResponseEntity<Account> getDetailsByAccNo(@RequestHeader("X-Auth-User") String userId, 
																				@PathVariable Long accNo){
		Account acc = accService.getAccountDetailsByAccNo(userId, accNo);
		return new ResponseEntity<Account>(acc,HttpStatus.FOUND);
	}
	
	@PatchMapping("/status-update")
	public ResponseEntity<String> updateAccStatus(@RequestHeader("X-Auth-User") String userId, 
														@RequestBody AccountStausUpdateDTO accStatReqDTO){
		String message = accService.updateAccStatus(userId,accStatReqDTO);
		return new ResponseEntity<String>(message,HttpStatus.CREATED);
	}
	
	@GetMapping("/test")
	public String welcome() {
		return "Hello";
	}
}
