package com.example.Start_up_crm_client_service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserEmergencyResponse {
    private Long id;
    private Long userId;
    private String name;
    private String relation;
    private String phone;
    private String alternatePhone;
}