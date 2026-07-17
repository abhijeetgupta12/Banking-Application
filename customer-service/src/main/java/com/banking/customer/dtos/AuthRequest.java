package com.banking.customer.dtos;

import lombok.Data;

@Data
public class AuthRequest {

    private String username;
    private String password;
}
