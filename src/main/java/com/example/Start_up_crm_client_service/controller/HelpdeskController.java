package com.example.Start_up_crm_client_service.controller;

import com.example.Start_up_crm_client_service.dto.CreateTicketRequest;
import com.example.Start_up_crm_client_service.service.HelpdeskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/helpdesk")
@RequiredArgsConstructor
public class HelpdeskController {

    private final HelpdeskService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createTicket(@ModelAttribute CreateTicketRequest request) {
        return ResponseEntity.ok(service.createTicket(request));
    }

    @GetMapping
    public ResponseEntity<?> getAllTickets() {
        return ResponseEntity.ok(service.getAllTickets());
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<?> getTicket(@PathVariable String ticketId) {
        return ResponseEntity.ok(service.getTicketById(ticketId));
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        return ResponseEntity.ok(service.getStats());
    }
}
