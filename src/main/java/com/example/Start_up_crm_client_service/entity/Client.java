package com.example.Start_up_crm_client_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    @Column(unique = true)
    private String email;

    private String mobileNumber;

    private String password;

    private String domain;

    private String address;

    private String country;

    private String state;

    private String postalCode;

    private String certificatePath;

    private String logoPath;

    private String role;   // ROLE_ORG
    private String  clientCode;
}