package com.banking.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.customer.dtos.AuthRequest;
import com.banking.customer.dtos.AuthResponse;
import com.banking.customer.entity.User;
import com.banking.customer.service.UserService;

@RestController
@RequestMapping("/customer")
public class UserController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<User> createUser(@RequestBody User user){
		User userResponse = userService.registerUser(user);
		return new ResponseEntity<User>(userResponse,HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> userLogin(@RequestBody AuthRequest req){
		AuthResponse res = userService.userLogin(req);
		if(res.getToken()!=null)
			return new ResponseEntity<AuthResponse>(res,HttpStatus.FOUND);
		else
			return new ResponseEntity<AuthResponse>(res,HttpStatus.NOT_FOUND);
	}
	
}
