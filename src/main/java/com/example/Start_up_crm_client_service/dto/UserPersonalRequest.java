package com.example.Start_up_crm_client_service.dto;

import lombok.Data;

@Data
public class UserPersonalRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String designation;
    private String phone;
    private String gender;
    private String maritalStatus;
    private String dateOfBirth;
}