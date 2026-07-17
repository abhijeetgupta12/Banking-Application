package com.banking.account.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountAndBalanceDTO {
	
	private Long acc_no;
	private Long balance;
}
