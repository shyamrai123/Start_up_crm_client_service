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

    // ─────────────────────────────────────────────────────────────
    // ADD
    // ─────────────────────────────────────────────────────────────

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // ─────────────────────────────────────────────────────────────
    // GET ALL (by company)
    // ─────────────────────────────────────────────────────────────

    @Override
    public List<Employee> getByClientCodeAndCompany(String clientCode, String companyName) {
        return employeeRepository.findByClientCodeAndCompanyName(clientCode, companyName);
    }

    // ─────────────────────────────────────────────────────────────
    // GET SINGLE (with company isolation)
    // ─────────────────────────────────────────────────────────────

    @Override
    public Employee getByIdAndClientCode(Long id, String clientCode) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!employee.getClientCode().equals(clientCode)) {
            throw new RuntimeException("Access denied: Employee does not belong to your company");
        }

        return employee;
    }

    // ─────────────────────────────────────────────────────────────
    // UPDATE — only basic/work/statutory fields
    // Address, education, career, emergency → use EmployeeProfileService
    // ─────────────────────────────────────────────────────────────

    @Override
    public Employee updateEmployee(Long id, Employee updatedData, String clientCode) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!existing.getClientCode().equals(clientCode)) {
            throw new RuntimeException("Access denied: Employee does not belong to your company");
        }

        // ── Basic info ────────────────────────────────────────────
        if (updatedData.getFirstName() != null)  existing.setFirstName(updatedData.getFirstName());
        if (updatedData.getLastName() != null)   existing.setLastName(updatedData.getLastName());
        if (updatedData.getEmail() != null)      existing.setEmail(updatedData.getEmail());
        if (updatedData.getPhone() != null)      existing.setPhone(updatedData.getPhone());
        if (updatedData.getGender() != null)     existing.setGender(updatedData.getGender());
        if (updatedData.getMaritalStatus() != null) existing.setMaritalStatus(updatedData.getMaritalStatus());
        if (updatedData.getDateOfBirth() != null)   existing.setDateOfBirth(updatedData.getDateOfBirth());
        if (updatedData.getBloodGroup() != null)    existing.setBloodGroup(updatedData.getBloodGroup());

        // Keep fullName in sync
        if (updatedData.getFirstName() != null || updatedData.getLastName() != null) {
            String first = updatedData.getFirstName() != null
                    ? updatedData.getFirstName() : existing.getFirstName();
            String last = updatedData.getLastName() != null
                    ? updatedData.getLastName() : existing.getLastName();
            existing.setFullName(first + " " + last);
        }

        // ── Work info ─────────────────────────────────────────────
        if (updatedData.getDepartment() != null)   existing.setDepartment(updatedData.getDepartment());
        if (updatedData.getDesignation() != null)  existing.setDesignation(updatedData.getDesignation());
        if (updatedData.getRole() != null)         existing.setRole(updatedData.getRole());
        if (updatedData.getSalary() != null)       existing.setSalary(updatedData.getSalary());
        if (updatedData.getJoiningDate() != null)  existing.setJoiningDate(updatedData.getJoiningDate());
        if (updatedData.getStatus() != null)       existing.setStatus(updatedData.getStatus());

        // ── Statutory ─────────────────────────────────────────────
        if (updatedData.getPanNumber() != null)     existing.setPanNumber(updatedData.getPanNumber());
        if (updatedData.getAadhaarNumber() != null) existing.setAadhaarNumber(updatedData.getAadhaarNumber());
        if (updatedData.getUan() != null)           existing.setUan(updatedData.getUan());
        if (updatedData.getBankName() != null)      existing.setBankName(updatedData.getBankName());
        if (updatedData.getAccountNumber() != null) existing.setAccountNumber(updatedData.getAccountNumber());
        if (updatedData.getIfscCode() != null)      existing.setIfscCode(updatedData.getIfscCode());

        // 🔐 clientCode & companyName intentionally NOT updated
        return employeeRepository.save(existing);
    }

    // ─────────────────────────────────────────────────────────────
    // DELETE (with company isolation)
    // ─────────────────────────────────────────────────────────────

    @Override
    public void deleteEmployee(Long id, String clientCode, String companyName) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!employee.getClientCode().equals(clientCode) ||
                !employee.getCompanyName().equals(companyName)) {
            throw new RuntimeException("Access denied: You are not allowed to delete this employee");
        }

        employeeRepository.delete(employee);
    }
}