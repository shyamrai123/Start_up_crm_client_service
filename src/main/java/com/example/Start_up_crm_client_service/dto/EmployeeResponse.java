package com.example.Start_up_crm_client_service.dto;


import lombok.Data;
import lombok.Builder;

import java.sql.Date;


@Data
@Builder
public class EmployeeResponse {

    private Long id;
    private String email;
    private String clientCode;

    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private Date joiningDate;
    private String department;
    private String designation;
    private String role;

    private Double salary;
    private String status;
}