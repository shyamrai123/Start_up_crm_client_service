package com.example.Start_up_crm_client_service.dto;

import lombok.Data;

@Data
public class GenerateTokenRequest {

    private String username;

    private Long userId;

    private String role;


}
