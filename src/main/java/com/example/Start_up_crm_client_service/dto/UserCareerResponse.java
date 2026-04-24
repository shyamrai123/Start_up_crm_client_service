package com.example.Start_up_crm_client_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UserCareerResponse {

    private Long userId;
    private List<String> skills;
    private String certifications;
    private String linkedInUrl;
    private String resumeUrl;
}