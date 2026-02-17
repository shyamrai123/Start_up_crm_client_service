package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.dto.ClientLoginRequest;
import com.example.Start_up_crm_client_service.dto.ClientSignupRequest;
import com.example.Start_up_crm_client_service.dto.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.Map;
public interface ClientService {

    ApiResponse<Map<String, String>> registerClient(ClientSignupRequest request);
    ApiResponse<Map<String, String>> loginClient(ClientLoginRequest request);

}