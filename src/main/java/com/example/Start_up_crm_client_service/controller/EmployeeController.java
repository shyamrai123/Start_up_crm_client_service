package com.example.Start_up_crm_client_service.controller;

import com.example.Start_up_crm_client_service.entity.ClientHr;
import com.example.Start_up_crm_client_service.entity.Employee;
import com.example.Start_up_crm_client_service.repository.ClientHrRepository;
import com.example.Start_up_crm_client_service.service.EmployeeService;
import com.example.Start_up_crm_client_service.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


import java.util.List;
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ClientHrRepository clientHrRepository;
    private final JwtTokenUtil jwtTokenUtil;


    // 🔐 Only ORG can add employee
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ORG')")
    public Employee addEmployee(@RequestBody Employee employee,
                                Authentication authentication) {

        // Get logged-in ORG email from JWT
        String email = authentication.getName();

        // Fetch ORG (HR) from DB
        ClientHr hr = clientHrRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("ORG not found"));

        // Automatically set company details
        employee.setClientCode(hr.getClient().getClientCode());
        employee.setCompanyName(hr.getClient().getCompanyName());

        return employeeService.addEmployee(employee);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ORG')")
    public ResponseEntity<?> getEmployees(Authentication authentication) {

        // 1️⃣ Get logged-in email from security context
        String email = authentication.getName();

        // 2️⃣ Fetch HR (ClientHr) using email
        ClientHr hr = clientHrRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("HR not found"));

        // 3️⃣ Fetch employees by clientCode & companyName
        List<Employee> employees =
                employeeService.getByClientCodeAndCompany(
                        hr.getClientCode(),
                        hr.getCompanyName()
                );

        return ResponseEntity.ok(employees);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        ClientHr hr = clientHrRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("HR not found"));

        employeeService.deleteEmployee(
                id,
                hr.getClientCode(),
                hr.getCompanyName()
        );

        return ResponseEntity.ok("Employee deleted successfully");
    }
}
