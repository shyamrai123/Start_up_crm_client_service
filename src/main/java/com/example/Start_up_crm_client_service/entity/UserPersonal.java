package com.example.Start_up_crm_client_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_personal")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPersonal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // 🔥 from JWT

    private String firstName;
    private String lastName;
    private String email;
    private String designation;
    private String phone;
    private String gender;
    private String maritalStatus;
    private String dateOfBirth;
}
