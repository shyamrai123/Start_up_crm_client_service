package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.entity.Employee;
import java.util.List;

public interface EmployeeService {

    Employee addEmployee(Employee employee);

    List<Employee> getByClientCodeAndCompany(String clientCode, String companyName);
    void deleteEmployee(Long id, String clientCode, String companyName);
}
