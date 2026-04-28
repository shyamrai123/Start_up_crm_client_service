package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.dto.CreateTicketRequest;
import com.example.Start_up_crm_client_service.dto.TicketResponse;

import java.util.List;
import java.util.Map;

public interface HelpdeskService {

    TicketResponse createTicket(CreateTicketRequest request);

    List<TicketResponse> getAllTickets();

    TicketResponse getTicketById(String ticketId);

    Map<String, Long> getStats();
}
