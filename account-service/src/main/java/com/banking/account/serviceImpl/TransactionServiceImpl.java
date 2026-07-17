package com.banking.account.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.account.dtos.AccountAndBalanceDTO;
import com.banking.account.entity.Account;
import com.banking.account.exception.ResourceNotFoundException;
import com.banking.account.repo.AccountRepository;
import com.banking.account.service.TransactionService;

import jakarta.transaction.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	AccountRepository accountRepository;

	@Override
	@Transactional
	public String debitAccBalance(String userId, AccountAndBalanceDTO accountDetails) {
		// TODO Auto-generated method stub
		System.out.println(userId);
		System.out.println(accountDetails.getAcc_no()+" "+accountDetails.getBalance());
		
		Account acc = accountRepository.findById(accountDetails.getAcc_no())
				.orElseThrow(()->new ResourceNotFoundException("Account no does not exist!"));
		System.out.println(acc.getAcc_no());
		
			
		if(!acc.getCust_id().equals(userId))
			throw new ResourceNotFoundException("Account does not belong to logged in user!");
		else if(!acc.getStatus().equals("ACTIVE"))
			throw new ResourceNotFoundException("Account is not ACTIVE!");
		else if(acc.getBalance()<accountDetails.getBalance())
			throw new ResourceNotFoundException("Insufficient Balance!!");
		
		
		
		Long curr_bal=acc.getBalance();
		acc.setBalance(curr_bal-accountDetails.getBalance());
		
		accountRepository.save(acc);
		
		//sendNotification();
		
		return "Amount of Rs."+accountDetails.getBalance()+
									" deducted from account no "+acc.getAcc_no();
	}

	@Override
	@Transactional
	public String creditAccBalance(AccountAndBalanceDTO accountDetails) {
		// TODO Auto-generated method stub
		Account acc = accountRepository.findById(accountDetails.getAcc_no())
				.orElseThrow(()->new ResourceNotFoundException("Account no does not exist!"));
		System.out.println(acc.getAcc_no());
		
			
		if(!acc.getStatus().equals("ACTIVE"))
			throw new ResourceNotFoundException("Account is not ACTIVE!");
		
		Long curr_bal=acc.getBalance();
		acc.setBalance(curr_bal+accountDetails.getBalance());
		
		accountRepository.save(acc);
		
		//sendNotification();
		
		return "Amount of Rs."+accountDetails.getBalance()+
									" credited to account no "+acc.getAcc_no();
	}

	@Override
	public String checkBalance(String userId, Long accNo) {
		// TODO Auto-generated method stub
		Account acc = accountRepository.findById(accNo)
				.orElseThrow(()-> new ResourceNotFoundException("Account does not exist!"));

		if(!acc.getCust_id().equals(userId))
			throw new ResourceNotFoundException("Account does not belong to the user logged in!");
		
		return acc.getBalance().toString();

	}

	
}
