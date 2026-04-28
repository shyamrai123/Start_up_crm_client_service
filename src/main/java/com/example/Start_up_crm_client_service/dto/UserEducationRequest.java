package com.example.Start_up_crm_client_service.dto;

import lombok.Data;

@Data
public class UserEducationRequest {

    private String highestQualification;
    private String specialization;
    private String university;
    private Integer passedOutYear;
}