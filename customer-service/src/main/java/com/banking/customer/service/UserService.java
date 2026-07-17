package com.banking.customer.service;

import com.banking.customer.dtos.AuthRequest;
import com.banking.customer.dtos.AuthResponse;
import com.banking.customer.entity.User;

public interface UserService {

	User registerUser(User user);
	AuthResponse userLogin(AuthRequest req);
	
}
