package com.example.Start_up_crm_client_service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserEducationResponse {

    private Long id;   // ✅ ADD THIS
    private Long userId;

    private String highestQualification;
    private String specialization;
    private String university;
    private Integer passedOutYear;
}