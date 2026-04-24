package com.example.Start_up_crm_client_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserCareerRequest {

    private List<String> skills;
    private String certifications;
    private String linkedInUrl;
    private String resumeUrl;
}