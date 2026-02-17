package com.example.Start_up_crm_client_service.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
public class EmployeeResponse {

    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    private String department;
    private String designation;
    private String role;

    private Double salary;



    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joiningDate;

    private Boolean active;

    private String address;
}