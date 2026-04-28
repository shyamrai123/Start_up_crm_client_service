package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.dto.CreateTicketRequest;
import com.example.Start_up_crm_client_service.dto.TicketResponse;
import com.example.Start_up_crm_client_service.entity.HelpdeskTicket;
import com.example.Start_up_crm_client_service.entity.TicketPriority;
import com.example.Start_up_crm_client_service.entity.TicketStatus;
import com.example.Start_up_crm_client_service.repository.HelpdeskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HelpdeskServiceImpl implements HelpdeskService {

    private final HelpdeskRepository repository;

    private static int counter = 1000;

    @Override
    public TicketResponse createTicket(CreateTicketRequest request) {

        String ticketId = "HD-" + LocalDate.now().getYear() + "-" + (++counter);

        String filePath = null;

        // ✅ FIXED FILE UPLOAD
        try {
            if (request.getFile() != null && !request.getFile().isEmpty()) {

                String uploadDir = System.getProperty("user.dir") + "/uploads/";

                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String fileName = System.currentTimeMillis() + "_" +
                        request.getFile().getOriginalFilename();

                filePath = uploadDir + fileName;

                request.getFile().transferTo(new File(filePath));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }

        HelpdeskTicket ticket = HelpdeskTicket.builder()
                .ticketId(ticketId)
                .category(request.getCategory())
                .subject(request.getSubject())
                .description(request.getDescription())
                .attachmentUrl(filePath)
                .priority(TicketPriority.MEDIUM)
                .status(TicketStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(ticket);

        return mapToResponse(ticket);
    }

    @Override
    public List<TicketResponse> getAllTickets() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TicketResponse getTicketById(String ticketId) {
        HelpdeskTicket ticket = repository.findByTicketId(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        return mapToResponse(ticket);
    }

    @Override
    public Map<String, Long> getStats() {

        List<HelpdeskTicket> tickets = repository.findAll();

        long active = tickets.stream()
                .filter(t -> t.getStatus() == TicketStatus.OPEN
                        || t.getStatus() == TicketStatus.IN_PROGRESS)
                .count();

        long resolved = tickets.stream()
                .filter(t -> t.getStatus() == TicketStatus.RESOLVED)
                .count();

        long closed = tickets.stream()
                .filter(t -> t.getStatus() == TicketStatus.CLOSED)
                .count();

        Map<String, Long> stats = new HashMap<>();
        stats.put("active", active);
        stats.put("resolved", resolved);
        stats.put("closed", closed);

        return stats;
    }

    private TicketResponse mapToResponse(HelpdeskTicket ticket) {
        return TicketResponse.builder()
                .ticketId(ticket.getTicketId())
                .category(ticket.getCategory())
                .subject(ticket.getSubject())
                .priority(ticket.getPriority().name())
                .status(ticket.getStatus().name())
                .createdAt(ticket.getCreatedAt())
                .build();
    }
}