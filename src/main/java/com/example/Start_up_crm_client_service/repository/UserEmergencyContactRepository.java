package com.example.Start_up_crm_client_service.repository;

import com.example.Start_up_crm_client_service.entity.UserEmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserEmergencyContactRepository extends JpaRepository<UserEmergencyContact, Long> {
    List<UserEmergencyContact> findByUserId(Long userId);
}