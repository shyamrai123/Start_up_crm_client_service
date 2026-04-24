package com.example.Start_up_crm_client_service.repository;

import com.example.Start_up_crm_client_service.dto.DepartmentStats;
import com.example.Start_up_crm_client_service.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // ── Tenant-based queries ─────────────────────────
    List<Employee> findByClientCode(String clientCode);

    List<Employee> findByClientCodeAndCompanyName(String clientCode, String companyName);

    Optional<Employee> findByEmail(String email);

    // ── Counts ─────────────────────────
    Long countByClientCode(String clientCode);

    Long countByClientCodeAndStatus(String clientCode, String status);

    // ── Analytics ─────────────────────────
    @Query("SELECT SUM(e.salary) FROM Employee e WHERE e.clientCode = :clientCode")
    Double getTotalSalary(@Param("clientCode") String clientCode);

    @Query("SELECT new com.example.Start_up_crm_client_service.dto.DepartmentStats(" +
            "e.department, COUNT(e)) " +
            "FROM Employee e " +
            "WHERE e.clientCode = :clientCode " +
            "GROUP BY e.department")
    List<DepartmentStats> getDepartmentStats(@Param("clientCode") String clientCode);
}