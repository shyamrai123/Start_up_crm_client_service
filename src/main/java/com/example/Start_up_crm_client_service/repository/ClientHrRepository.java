package com.example.Start_up_crm_client_service.repository;

import com.example.Start_up_crm_client_service.entity.ClientHr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientHrRepository extends JpaRepository<ClientHr, Long> {
    Optional<ClientHr> findByEmailAndClientId(String email, Long clientId);
}