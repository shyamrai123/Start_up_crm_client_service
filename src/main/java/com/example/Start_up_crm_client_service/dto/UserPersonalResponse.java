package com.example.Start_up_crm_client_service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserPersonalResponse {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String designation;
    private String phone;
    private String gender;
    private String maritalStatus;
    private String dateOfBirth;
}