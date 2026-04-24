package com.example.Start_up_crm_client_service.repository;

import com.example.Start_up_crm_client_service.entity.UserPersonal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  UserPersonalRepository extends JpaRepository<UserPersonal, Long> {
    Optional<UserPersonal> findByUserId(Long userId);
}