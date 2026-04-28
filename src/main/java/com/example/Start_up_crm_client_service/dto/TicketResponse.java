package com.example.Start_up_crm_client_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {

    private String ticketId;
    private String category;
    private String subject;
    private String priority;
    private String status;
    private LocalDateTime createdAt;
}
