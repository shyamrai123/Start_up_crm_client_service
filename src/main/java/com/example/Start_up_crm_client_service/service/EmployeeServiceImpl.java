package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.entity.Employee;
import com.example.Start_up_crm_client_service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;


    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getByClientCodeAndCompany(String clientCode, String companyName) {
        return employeeRepository
                .findByClientCodeAndCompanyName(clientCode, companyName);
    }
    @Override
    public void deleteEmployee(Long id, String clientCode, String companyName) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // 🔐 SECURITY CHECK (Very Important)
        if (!employee.getClientCode().equals(clientCode) ||
                !employee.getCompanyName().equals(companyName)) {

            throw new RuntimeException("You are not allowed to delete this employee");
        }

        employeeRepository.delete(employee);
    }
}


