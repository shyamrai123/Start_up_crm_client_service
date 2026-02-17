package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.dto.ApiResponse;
import com.example.Start_up_crm_client_service.dto.LoginRequest;
import com.example.Start_up_crm_client_service.dto.SignupRequest;

import java.util.Map;

public interface AuthService {
    ApiResponse<Map<String, String>> registerUser(SignupRequest signupRequest);
    ApiResponse<Map<String, String>> authenticateUser(LoginRequest loginRequest);
    ApiResponse<Map<String, String>> refreshAccessToken(String refreshToken);
}
