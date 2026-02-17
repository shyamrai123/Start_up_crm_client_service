package com.example.Start_up_crm_client_service.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailsService {
    UserDetails loadUserByUsername(String usernameOrEmail);
}
