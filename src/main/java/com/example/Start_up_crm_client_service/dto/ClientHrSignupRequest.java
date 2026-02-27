package com.example.Start_up_crm_client_service.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ClientHrSignupRequest {
    private String fullName;
    private String email;
    private String password;
    //@Column(name = "client_code", unique = true, nullable = false)
    private String clientCode;   // Company ID
}