package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.dto.ClientHrLoginRequest;
import com.example.Start_up_crm_client_service.dto.ClientHrSignupRequest;
import com.example.Start_up_crm_client_service.dto.HrResponse;

public interface ClientHrService {

    HrResponse signup(ClientHrSignupRequest request);

    HrResponse login(ClientHrLoginRequest request);
}