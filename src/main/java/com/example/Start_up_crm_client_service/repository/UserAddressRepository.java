package com.example.Start_up_crm_client_service.repository;

import com.example.Start_up_crm_client_service.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    Optional<UserAddress> findByUserId(Long userId);
}