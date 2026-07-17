package com.banking.gateway.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter_gate extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
    	System.out.println("Inside doFilter");
        String authHeader = request.getHeader("Authorization");
        if(authHeader==null||authHeader.isEmpty())System.out.println("No Auth Header Found");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(token);
                System.out.println("Username "+username);
            } catch (Exception e) {
                // Invalid or malformed token — security rules will handle it
            }
        }

        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                if (jwtUtil.validateToken(token, username)) {
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    username, null, List.of());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println(auth);
                    System.out.println("Authenticated - SecurityContextHolder Updated");
                    
                    request.setAttribute("authenticatedUser", username);
                }
            } catch (Exception e) {
                // Token validation failed — leave unauthenticated
            }
        }
        

        filterChain.doFilter(request, response);
    }

}
