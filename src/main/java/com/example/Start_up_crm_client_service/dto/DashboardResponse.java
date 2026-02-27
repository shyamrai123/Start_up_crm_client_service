package com.example.Start_up_crm_client_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardResponse {

    private Long totalEmployees;
    private Long activeEmployees;
    private Long inactiveEmployees;
    private Double totalSalary;
    private List<DepartmentStats> departmentStats;
}