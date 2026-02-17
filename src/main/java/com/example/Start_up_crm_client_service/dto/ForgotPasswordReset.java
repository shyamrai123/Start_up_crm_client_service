package com.example.Start_up_crm_client_service.dto;

import lombok.Data;

@Data
public class ForgotPasswordReset {
    private String token;
    private String newPassword;
}