package com.example.Start_up_crm_client_service.dto;

import lombok.Data;

@Data
public class ClientLoginRequest {

    private String email;
    private String password;
}