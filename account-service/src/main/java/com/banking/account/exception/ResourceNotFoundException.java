package com.banking.account.exception;


public class ResourceNotFoundException extends RuntimeException{

	public ResourceNotFoundException(String msg) {
		super(msg);
		System.out.println("ResourceNotFoundException: " + msg);
	}
}
