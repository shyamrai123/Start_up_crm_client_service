package com.example.Start_up_crm_client_service.repository;

import com.example.Start_up_crm_client_service.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);
    boolean existsByEmail(String email);

    boolean existsByCompanyName(String companyName);

    boolean existsByClientCode(String clientCode);
    Optional<Client> findByClientCodeAndEmail(String clientCode, String email);
}
