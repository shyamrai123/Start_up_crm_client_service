package com.example.Start_up_crm_client_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_address")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String line1;
    private String line2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}