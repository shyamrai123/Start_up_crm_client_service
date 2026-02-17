package com.example.Start_up_crm_client_service.repository;

import com.example.Start_up_crm_client_service.entity.Role;
import com.example.Start_up_crm_client_service.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
