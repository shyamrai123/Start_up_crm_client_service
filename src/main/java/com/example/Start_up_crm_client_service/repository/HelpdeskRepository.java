package com.example.Start_up_crm_client_service.repository;

import com.example.Start_up_crm_client_service.entity.HelpdeskTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface HelpdeskRepository extends JpaRepository<HelpdeskTicket, Long> {

    Optional<HelpdeskTicket> findByTicketId(String ticketId);
}
