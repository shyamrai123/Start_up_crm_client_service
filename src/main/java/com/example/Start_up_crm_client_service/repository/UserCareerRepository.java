package com.example.Start_up_crm_client_service.repository;

import com.example.Start_up_crm_client_service.entity.UserCareer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCareerRepository extends JpaRepository<UserCareer, Long> {
    Optional<UserCareer> findByUserId(Long userId);
}