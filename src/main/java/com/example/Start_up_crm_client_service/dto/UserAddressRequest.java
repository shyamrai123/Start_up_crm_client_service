package com.example.Start_up_crm_client_service.dto;

import lombok.Data;

@Data
public class UserAddressRequest {

    private String line1;
    private String line2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}