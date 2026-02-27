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

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    private String department;
    private String designation;
    private String role;
    private Double salary;
    private LocalDate joiningDate;
    private String status;

    private String panNumber;
    private String aadhaarNumber;

    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String emergencyContact;

    private Integer experience;
    private String qualification;
    private String address;

    private String clientCode;
    private String companyName;

}
