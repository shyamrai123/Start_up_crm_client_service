package com.example.Start_up_crm_client_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "helpdesk_ticket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HelpdeskTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String ticketId;

    private String category;

    private String subject;

    @Column(length = 2000)
    private String description;

    private String attachmentUrl;

    @Enumerated(EnumType.STRING)
    private TicketPriority priority;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    private LocalDateTime createdAt;
}
