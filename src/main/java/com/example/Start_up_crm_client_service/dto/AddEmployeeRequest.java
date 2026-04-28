package com.example.Start_up_crm_client_service.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AddEmployeeRequest {

    // ── Basic Info ─────────────────────────

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "^[0-9]{10}$")
    private String phone;

    @NotBlank
    private String gender;

    @NotBlank
    private String maritalStatus;

    @NotBlank
    private Double salary;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String bloodGroup;

    // ── Work Info ─────────────────────────

    @NotBlank
    private String department;

    @NotBlank
    private String designation;

    private String role;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joiningDate;

    private String status;

    // ── Statutory ─────────────────────────

    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}")
    private String panNumber;

    @Pattern(regexp = "^[0-9]{12}$")
    private String aadhaarNumber;

    private String uan;

    private String bankName;

    private String accountNumber;

    private String ifscCode;

    // ── Documents ─────────────────────────

    private MultipartFile aadhaarDocument;
    private MultipartFile panDocument;
}