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
        name = "clienthr",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email", "client_id"})
        }
)
public class ClientHr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String password;

    private String clientCode;

    private String companyName;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)  // ✅ FK to clients.id
    private Client client;
}
