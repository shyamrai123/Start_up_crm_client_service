package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.dto.AddEmployeeRequest;
import com.example.Start_up_crm_client_service.dto.EmployeeResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeeService {

    String addEmployee(AddEmployeeRequest request);

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse getEmployeeById(Long id);

    String updateEmployee(Long id, AddEmployeeRequest request);

    String deleteEmployee(Long id);
}