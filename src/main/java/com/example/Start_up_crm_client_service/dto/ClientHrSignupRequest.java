package com.example.Start_up_crm_client_service.dto;

import lombok.Data;

@Data
public class ClientHrSignupRequest {
    private String fullName;
    private String email;
    private String password;
    private Long clientId;   // Company ID
}