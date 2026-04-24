package com.example.Start_up_crm_client_service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserAddressResponse {

    private Long userId;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}