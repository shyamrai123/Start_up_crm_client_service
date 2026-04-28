package com.example.Start_up_crm_client_service.repository;

import com.example.Start_up_crm_client_service.entity.UserEducation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserEducationRepository extends JpaRepository<UserEducation, Long> {
    Optional<UserEducation> findFirstByUserId(Long userId);
}