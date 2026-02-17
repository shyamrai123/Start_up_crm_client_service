package com.example.Start_up_crm_client_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    private String department;
    private String designation;
    private String role;

    private Double salary;

    private LocalDate joiningDate;

    private Boolean active;

    @Column(length = 1000)
    private String address;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] aadhaarDocument;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] panDocument;
}