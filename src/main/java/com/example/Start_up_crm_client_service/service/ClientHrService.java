package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.dto.ClientHrLoginRequest;
import com.example.Start_up_crm_client_service.dto.ClientHrResponse;

import java.util.Map;

public interface ClientHrService {

    ClientHrResponse<Map<String, String>> login(ClientHrLoginRequest request);

    ClientHrResponse<Map<String, String>> getHrByEmail(String email);
}