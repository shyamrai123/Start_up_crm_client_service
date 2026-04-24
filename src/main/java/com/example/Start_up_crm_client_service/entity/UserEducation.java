package com.example.Start_up_crm_client_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_education")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEducation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String highestQualification;
    private String specialization;
    private String university;
    private Integer passedOutYear;
}