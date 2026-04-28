package com.example.Start_up_crm_client_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserEmergencyListRequest {
    private List<UserEmergencyRequest> emergencyContacts;
}