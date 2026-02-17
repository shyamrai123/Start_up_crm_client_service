package com.example.Start_up_crm_client_service.dto;

import lombok.Data;

@Data
public class ClientHrLoginRequest {
    private String email;
    private String password;
    private Long clientId;
}
