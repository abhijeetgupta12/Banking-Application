package com.banking.account.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;

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
	public String transferAmount(String userId, AccountAndBalanceDTO accountDetails) {
		// TODO Auto-generated method stub
		
		String debitMessage = debitAccBalance(userId,accountDetails);
		String creditMessage = creditAccBalance(accountDetails);
		
		return debitMessage+"\n"+creditMessage;
		
	}
	
	public String debitAccBalance(String userId, AccountAndBalanceDTO accountDetails) {
		// TODO Auto-generated method stub
		System.out.println("Logged in user - "+userId);
		System.out.println("Sender's AccountNo - "+accountDetails.getFrom_acc_no());
		System.out.println("Balance to be transaferred - "+accountDetails.getBalance());
		
		Account acc = accountRepository.findById(accountDetails.getFrom_acc_no())
				.orElseThrow(()->new ResourceNotFoundException("ERROR: Sender account_no does not exist! \n"));
		
		System.out.println("Balance available - "+acc.getBalance());
		
			
		if(!acc.getCust_id().equals(userId))
			throw new ResourceNotFoundException("ERROR: Account does not belong to logged in user! \n");
		else if(!acc.getStatus().equals("ACTIVE"))
			throw new ResourceNotFoundException("ERROR: Account is not ACTIVE! \n");
		else if(acc.getBalance()<accountDetails.getBalance())
			throw new ResourceNotFoundException("ERROR: Insufficient Balance!! \n");
		
		
		
		Long curr_bal=acc.getBalance();
		acc.setBalance(curr_bal-accountDetails.getBalance());
		
		accountRepository.save(acc);
		System.out.println("Balance deducted from sender's account");
		//sendNotification();
		
		return "Amount of Rs."+accountDetails.getBalance()+
									" deducted from account no "+accountDetails.getFrom_acc_no();
	}


	public String creditAccBalance(AccountAndBalanceDTO accountDetails) {
		// TODO Auto-generated method stub
		
		System.out.println("Receiver's AccountNo - "+accountDetails.getTo_acc_no());
		
		Account acc = accountRepository.findById(accountDetails.getTo_acc_no())
				.orElseThrow(()->new ResourceNotFoundException("ERROR: Receiver's AccountNo does not exist! \n"));
		
		System.out.println("Receiver's curr balance - "+acc.getBalance());
		
			
		if(!acc.getStatus().equals("ACTIVE"))
			throw new ResourceNotFoundException("ERROR: Account is not ACTIVE! \n");
		
		Long curr_bal=acc.getBalance();
		acc.setBalance(curr_bal+accountDetails.getBalance());
		
		accountRepository.save(acc);
		System.out.println("Balance transfered to receiver's account");
		//sendNotification();
		
		return "Amount of Rs."+accountDetails.getBalance()+
									" credited to account no "+acc.getAcc_no();
	}
	
	
	

	@Override
	public String checkBalance(String userId, Long accNo) {
		// TODO Auto-generated method stub
		Account acc = accountRepository.findById(accNo)
				.orElseThrow(()-> new ResourceNotFoundException("ERROR: Account does not exist! \n"));

		if(!acc.getCust_id().equals(userId))
			throw new ResourceNotFoundException("ERROR: Account does not belong to the user logged in! \n");
		
		return acc.getBalance().toString();

	}

	

	
}
