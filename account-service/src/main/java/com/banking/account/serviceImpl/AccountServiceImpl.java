package com.banking.account.serviceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.account.dtos.AccountStausUpdateDTO;
import com.banking.account.entity.Account;
import com.banking.account.exception.ResourceNotFoundException;
import com.banking.account.repo.AccountRepository;
import com.banking.account.service.AccountService;

import jakarta.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	private AccountRepository accRepo;

	@Override
	@Transactional
	public Account createAccount(String userName, String acc_type) {
		// TODO Auto-generated method stub
		Account accDetails = new Account();
		accDetails.setCust_id(userName);
		accDetails.setAcc_type(acc_type);
		accDetails.setBalance(0L);
		accDetails.setStatus("ACTIVE");
		
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");		
        
        accDetails.setCreated_at(now.format(formatter));
        accDetails.setUpdated_at(now.format(formatter));
        Account addedAcc = accRepo.save(accDetails);
		return addedAcc;
	}

	@Override
	public Account getAccountDetailsByAccNo(String userId, Long accNo) {
		// TODO Auto-generated method stub
		Account	accRetrieved = accRepo.findById(accNo)
							.filter(acc->acc.getCust_id().equals(userId))
							.orElseThrow(()-> new ResourceNotFoundException("No such account related to customerId exist!"));
		
		return accRetrieved;
	}

	@Override
	public List<Account> getAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public String updateAccStatus(String userId, AccountStausUpdateDTO accStatReqDTO) {
		// TODO Auto-generated method stub
		Account	accRetrieved = accRepo.findById(accStatReqDTO.getAcc_no())
				.filter(acc->acc.getCust_id().equals(userId))
				.orElseThrow(()-> new ResourceNotFoundException("No such account related to customerId exist!"));
		
		if(accRetrieved.getStatus()!=null)
			accRetrieved.setStatus(accStatReqDTO.getStatus());
		
		Account updatedAcc = accRepo.save(accRetrieved);
		
		return updatedAcc.getAcc_no()+" "+updatedAcc.getStatus();
	}

}
