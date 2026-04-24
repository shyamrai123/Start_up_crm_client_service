package com.example.Start_up_crm_client_service.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class AddEmployeeRequest {

    // ── Basic Info ─────────────────────────
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    private String maritalStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String bloodGroup;
    private String clientCode;
    private String companyName;
    // ── Work Info ─────────────────────────
    private String department;
    private String designation;
    private String role;
    private Double salary;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joiningDate;
    private String status; // ACTIVE / INACTIVE
    // ── Statutory ─────────────────────────
    private String panNumber;
    private String aadhaarNumber;
    private String uan;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    // ── Documents (Multipart Upload) ───────
    private MultipartFile aadhaarDocument;
    private MultipartFile panDocument;
}