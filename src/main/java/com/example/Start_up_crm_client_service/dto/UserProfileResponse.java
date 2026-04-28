package com.example.Start_up_crm_client_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UserProfileResponse {

    private UserPersonalResponse personal;
    private UserEducationResponse education;
    private UserCareerResponse career;
    private UserAddressResponse address;
    private List<UserEmergencyResponse> emergency;
}