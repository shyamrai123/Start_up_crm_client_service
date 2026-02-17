package com.example.Start_up_crm_client_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "client_hr",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email", "client_id"})
        }
)
public class ClientHr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;   // ❌ remove unique=true

    private String password;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;   // Company reference
}