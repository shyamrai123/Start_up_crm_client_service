package com.example.Start_up_crm_client_service.dto;

import lombok.Data;

@Data
public class UserEmergencyRequest {

    private String name;
    private String relation;
    private String phone;
    private String alternatePhone;
}