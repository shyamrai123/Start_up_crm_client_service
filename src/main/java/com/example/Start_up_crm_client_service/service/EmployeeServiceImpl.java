package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.dto.AddEmployeeRequest;
import com.example.Start_up_crm_client_service.dto.EmployeeResponse;
import com.example.Start_up_crm_client_service.entity.Employee;
import com.example.Start_up_crm_client_service.repository.EmployeeRepository;
import com.example.Start_up_crm_client_service.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public String addEmployee(AddEmployeeRequest request) {

        try {
            Employee employee = mapToEntity(new Employee(), request);
            employeeRepository.save(employee);
            return "Employee added successfully";

        } catch (Exception e) {
            e.printStackTrace(); // Shows real error in console
            throw new RuntimeException("Error adding employee: " + e.getMessage());
        }
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        return mapToResponse(employee);
    }

    @Override
    public String updateEmployee(Long id, AddEmployeeRequest request) {

        try {
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

            mapToEntity(employee, request);

            employeeRepository.save(employee);

            return "Employee updated successfully";

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating employee: " + e.getMessage());
        }
    }

    @Override
    public String deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        employeeRepository.delete(employee);

        return "Employee deleted successfully";
    }

    // 🔥 Common Mapping Method
    private Employee mapToEntity(Employee employee, AddEmployeeRequest request) throws Exception {

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDepartment(request.getDepartment());
        employee.setDesignation(request.getDesignation());
        employee.setRole(request.getRole());
        employee.setSalary(request.getSalary());
        employee.setJoiningDate(request.getJoiningDate());
        employee.setActive(request.getActive());
        employee.setAddress(request.getAddress());

        // ✅ Aadhaar Upload
        if (request.getAadhaarDocument() != null &&
                !request.getAadhaarDocument().isEmpty()) {

            employee.setAadhaarDocument(
                    request.getAadhaarDocument().getBytes()
            );
        }

        // ✅ PAN Upload
        if (request.getPanDocument() != null &&
                !request.getPanDocument().isEmpty()) {

            employee.setPanDocument(
                    request.getPanDocument().getBytes()
            );
        }

        return employee;
    }

    private EmployeeResponse mapToResponse(Employee emp) {

        return EmployeeResponse.builder()
                .id(emp.getId())
                .firstName(emp.getFirstName())
                .lastName(emp.getLastName())
                .email(emp.getEmail())
                .phone(emp.getPhone())
                .department(emp.getDepartment())
                .designation(emp.getDesignation())
                .role(emp.getRole())
                .salary(emp.getSalary())
                .joiningDate(emp.getJoiningDate() != null ?
                        LocalDate.parse(emp.getJoiningDate().toString()) : null)
                .active(emp.getActive())
                .address(emp.getAddress())
                .build();
    }
}