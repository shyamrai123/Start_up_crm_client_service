package com.example.Start_up_crm_client_service.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ClientSignupRequest {

    private String companyName;
    private String email;
    private String mobileNumber;
    private String password;
    private String domain;
    private String address;
    private String country;
    private String state;
    private String postalCode;

    private MultipartFile certificateFile;
    private MultipartFile logoFile;

}