package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.dto.DashboardResponse;
import com.example.Start_up_crm_client_service.dto.DepartmentStats;
import com.example.Start_up_crm_client_service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final EmployeeRepository employeeRepository;

    public DashboardResponse getDashboardData(String clientCode) {

        Long total = employeeRepository.countByClientCode(clientCode);
        Long active = employeeRepository.countByClientCodeAndStatus(clientCode, "ACTIVE");
        Long inactive = employeeRepository.countByClientCodeAndStatus(clientCode, "INACTIVE");

        Double salary = employeeRepository.getTotalSalary(clientCode);
        if (salary == null) salary = 0.0;

        List<DepartmentStats> deptStats =
                employeeRepository.getDepartmentStats(clientCode);

        return DashboardResponse.builder()
                .totalEmployees(total)
                .activeEmployees(active)
                .inactiveEmployees(inactive)
                .totalSalary(salary)
                .departmentStats(deptStats)
                .build();
    }
}