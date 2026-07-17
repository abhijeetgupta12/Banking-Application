package com.banking.customer.serviceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banking.customer.config.JwtUtil;
import com.banking.customer.dtos.AuthRequest;
import com.banking.customer.dtos.AuthResponse;
import com.banking.customer.entity.User;
import com.banking.customer.repo.UserRepository;
import com.banking.customer.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private AuthenticationManager authManager;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private JwtUtil jwtUtil;

	@Override
	public User registerUser(User user) {
		// TODO Auto-generated method stub
		user.setPass(passwordEncoder.encode(user.getPass()));
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");		
        
        user.setCreated_at(now.format(formatter));;
        user.setUpdated_at(now.format(formatter));
		User savedUser = userRepository.save(user);
		return savedUser;
	}

	@Override
	public AuthResponse userLogin(AuthRequest request) {
		// TODO Auto-generated method stub
		System.out.println(request.getUsername()+" "+request.getPassword());
    	String token = null;
    	try {
	        authManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        request.getUsername(),
	                        request.getPassword()));
	        
	        token =
	                jwtUtil.generateToken(
	                        request.getUsername());
	        
    	}catch (Exception e) {
			System.out.println(e.getMessage());
		}
    	
        return new AuthResponse(token);
	}

}
