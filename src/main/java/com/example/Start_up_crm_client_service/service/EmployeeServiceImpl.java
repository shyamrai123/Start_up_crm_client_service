package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.dto.AddEmployeeRequest;
import com.example.Start_up_crm_client_service.dto.EmployeeResponse;
import com.example.Start_up_crm_client_service.entity.Employee;
import com.example.Start_up_crm_client_service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    // ================= CREATE =================
    @Override
    public EmployeeResponse addEmployee(AddEmployeeRequest request, String clientCode, String companyName) {

        Employee employee = mapToEntity(request);

        employee.setClientCode(clientCode);
        employee.setCompanyName(companyName);

        return mapToResponse(employeeRepository.save(employee));
    }

    // ================= GET ALL =================
    @Override
    public List<EmployeeResponse> getByClientCodeAndCompany(String clientCode, String companyName) {

        return employeeRepository
                .findByClientCodeAndCompanyName(clientCode, companyName)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ================= GET BY ID =================
    @Override
    public EmployeeResponse getByIdAndClientCode(Long id, String clientCode) {

        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!emp.getClientCode().equals(clientCode)) {
            throw new RuntimeException("Access denied");
        }

        return mapToResponse(emp);
    }

    // ================= UPDATE =================
    @Override
    public EmployeeResponse updateEmployee(Long id, AddEmployeeRequest request, String clientCode) {

        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!emp.getClientCode().equals(clientCode)) {
            throw new RuntimeException("Access denied");
        }

        updateEntity(emp, request);

        return mapToResponse(employeeRepository.save(emp));
    }

    // ================= DELETE =================
    @Override
    public void deleteEmployee(Long id, String clientCode, String companyName) {

        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!emp.getClientCode().equals(clientCode) ||
                !emp.getCompanyName().equals(companyName)) {
            throw new RuntimeException("Access denied");
        }

        employeeRepository.delete(emp);
    }

    // ================= MAPPERS =================

    private Employee mapToEntity(AddEmployeeRequest r) {

        Employee e = new Employee();

        e.setFirstName(r.getFirstName());
        e.setLastName(r.getLastName());
        e.setEmail(r.getEmail());
        e.setPhone(r.getPhone());
        e.setGender(r.getGender());
        e.setMaritalStatus(r.getMaritalStatus());
        e.setDateOfBirth(r.getDateOfBirth());
        e.setBloodGroup(r.getBloodGroup());

        e.setDepartment(r.getDepartment());
        e.setDesignation(r.getDesignation());
        e.setRole(r.getRole());
        e.setSalary(r.getSalary());
        e.setJoiningDate(r.getJoiningDate());
        e.setStatus(r.getStatus());

        e.setPanNumber(r.getPanNumber());
        e.setAadhaarNumber(r.getAadhaarNumber());
        e.setUan(r.getUan());
        e.setBankName(r.getBankName());
        e.setAccountNumber(r.getAccountNumber());
        e.setIfscCode(r.getIfscCode());

        try {
            if (r.getAadhaarDocument() != null)
                e.setAadhaarDocument(r.getAadhaarDocument().getBytes());

            if (r.getPanDocument() != null)
                e.setPanDocument(r.getPanDocument().getBytes());

        } catch (Exception ex) {
            throw new RuntimeException("File processing error");
        }

        return e;
    }

    private void updateEntity(Employee e, AddEmployeeRequest r) {

        if (r.getFirstName() != null) e.setFirstName(r.getFirstName());
        if (r.getLastName() != null) e.setLastName(r.getLastName());
        if (r.getPhone() != null) e.setPhone(r.getPhone());
        if (r.getDepartment() != null) e.setDepartment(r.getDepartment());
        if (r.getDesignation() != null) e.setDesignation(r.getDesignation());
        if (r.getRole() != null) e.setRole(r.getRole());
        if (r.getSalary() != null) e.setSalary(r.getSalary());
        if (r.getStatus() != null) e.setStatus(r.getStatus());
    }

    private EmployeeResponse mapToResponse(Employee e) {

        return EmployeeResponse.builder()
                .id(e.getId())
                .firstName(e.getFirstName())
                .lastName(e.getLastName())
                .email(e.getEmail())
                .phone(e.getPhone())
                .department(e.getDepartment())
                .designation(e.getDesignation())
                .role(e.getRole())
                .salary(e.getSalary())
                .joiningDate(Date.valueOf(e.getJoiningDate()))
                .build();
    }
}