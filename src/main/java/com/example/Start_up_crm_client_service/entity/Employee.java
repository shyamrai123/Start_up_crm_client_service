package com.example.Start_up_crm_client_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ── Basic Info ─────────────────────────
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String phone;
    private String gender;
    private String maritalStatus;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String bloodGroup;

    // ── Work Info ─────────────────────────
    private String department;
    private String designation;
    private String role;
    private Double salary;
    private LocalDate joiningDate;
    private String status; // ACTIVE / INACTIVE
    private String employeeCode;

    // ── Statutory ─────────────────────────
    private String panNumber;
    private String aadhaarNumber;
    private String uan;
    private String bankName;
    private String accountNumber;
    private String ifscCode;

    // ── Documents (from master) ────────────
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] aadhaarDocument;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] panDocument;

    // ── Org Context ───────────────────────
    private String clientCode;
    private String companyName;
}