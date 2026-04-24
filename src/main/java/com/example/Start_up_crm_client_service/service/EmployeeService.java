package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.dto.AddEmployeeRequest;
import com.example.Start_up_crm_client_service.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse addEmployee(AddEmployeeRequest request, String clientCode, String companyName);

    List<EmployeeResponse> getByClientCodeAndCompany(String clientCode, String companyName);

    EmployeeResponse getByIdAndClientCode(Long id, String clientCode);

    EmployeeResponse updateEmployee(Long id, AddEmployeeRequest request, String clientCode);

    void deleteEmployee(Long id, String clientCode, String companyName);
}