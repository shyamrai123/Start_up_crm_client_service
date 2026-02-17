package com.example.Start_up_crm_client_service.repository;

import com.example.Start_up_crm_client_service.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}