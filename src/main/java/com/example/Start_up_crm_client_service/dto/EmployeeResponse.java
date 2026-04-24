package com.example.Start_up_crm_client_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EmployeeResponse {

    private Long id;

    // ── Identity ─────────────────────────
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    // ── Work Info ─────────────────────────
    private String department;
    private String designation;
    private String role;
    private Double salary;

    // ── Employment Info ───────────────────
    private LocalDate joiningDate;
    private Boolean active;

    // ── Optional Info ─────────────────────
    private String address;

    // ── Multi-tenant Info ─────────────────
    private String clientCode;
}