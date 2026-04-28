package com.example.Start_up_crm_client_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_emergency")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEmergencyContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String name;
    private String relation;
    private String phone;
    private String alternatePhone;
}