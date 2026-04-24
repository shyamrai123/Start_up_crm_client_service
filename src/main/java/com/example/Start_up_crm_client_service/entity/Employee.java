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

    // ── Basic Info (HR fills on creation) ─────────────────────────
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

    // ── Work Info (HR fills) ──────────────────────────────────────
    private String department;
    private String designation;
    private String role;
    private Double salary;
    private LocalDate joiningDate;
    private String status;      // ACTIVE / INACTIVE etc.
    private String employeeCode; // e.g. EMP-1001

    // ── Statutory (HR fills) ─────────────────────────────────────
    private String panNumber;
    private String aadhaarNumber;
    private String uan;
    private String bankName;
    private String accountNumber;
    private String ifscCode;

    // ── Org context ───────────────────────────────────────────────
    private String clientCode;
    private String companyName;

    // ── Related tables ────────────────────────────────────────────

//    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
//    private UserAddress address;
//
//    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
//    private UserEducation education;
//
//    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
//    private UserCareer career;
//
//    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
//    private java.util.List<UserEmergencyContact> emergencyContacts = new java.util.ArrayList<>();
}