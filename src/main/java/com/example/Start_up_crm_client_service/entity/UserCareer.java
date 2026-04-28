package com.example.Start_up_crm_client_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_career")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCareer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String skills;
    private String certifications;
    private String linkedInUrl;
    private String resumeUrl;
}