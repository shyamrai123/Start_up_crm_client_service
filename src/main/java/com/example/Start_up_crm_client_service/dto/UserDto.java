package com.example.Start_up_crm_client_service.dto;

import com.example.Start_up_crm_client_service.entity.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserDto {
//    private Long id;
//    private String firstName;
//    private String lastName;
//    private String email;
//    private String username;
//    private String mobile_no;
//    private Set<String> roles;
//    private String profileImage;

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String mobileNo;
    private Set<String> roles;
    private String profileImage;
    private String bio;
    private String location;
    private LocalDateTime createdAt;
    private Status status;

}

