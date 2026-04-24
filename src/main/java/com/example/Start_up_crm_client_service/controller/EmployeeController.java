package com.example.Start_up_crm_client_service.controller;

import com.example.Start_up_crm_client_service.dto.ClientHrResponse;
import com.example.Start_up_crm_client_service.entity.Employee;
import com.example.Start_up_crm_client_service.security.JwtPrincipal;
import com.example.Start_up_crm_client_service.service.ClientHrService;
import com.example.Start_up_crm_client_service.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ClientHrService clientHrService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ORG')")
    public ResponseEntity<?> addEmployee(
            @RequestBody Employee employee,
            Authentication authentication) {

        Map<String, String> hrData = fetchHrData(authentication);

        employee.setClientCode(hrData.get("clientCode"));
        employee.setCompanyName(hrData.get("companyName"));

        return ResponseEntity.ok(Map.of(
                "message", "Employee added successfully",
                "employee", employeeService.addEmployee(employee)
        ));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ORG')")
    public ResponseEntity<List<Employee>> getEmployees(Authentication authentication) {

        Map<String, String> hrData = fetchHrData(authentication);

        return ResponseEntity.ok(
                employeeService.getByClientCodeAndCompany(
                        hrData.get("clientCode"),
                        hrData.get("companyName")
                )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ORG')")
    public ResponseEntity<Employee> getEmployee(
            @PathVariable Long id,
            Authentication authentication) {

        Map<String, String> hrData = fetchHrData(authentication);

        return ResponseEntity.ok(
                employeeService.getByIdAndClientCode(id, hrData.get("clientCode"))
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ORG')")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee,
            Authentication authentication) {

        Map<String, String> hrData = fetchHrData(authentication);

        employee.setClientCode(hrData.get("clientCode"));
        employee.setCompanyName(hrData.get("companyName"));

        return ResponseEntity.ok(Map.of(
                "message", "Employee updated successfully",
                "employee", employeeService.updateEmployee(id, employee, hrData.get("clientCode"))
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ORG')")
    public ResponseEntity<?> deleteEmployee(
            @PathVariable Long id,
            Authentication authentication) {

        Map<String, String> hrData = fetchHrData(authentication);

        employeeService.deleteEmployee(
                id,
                hrData.get("clientCode"),
                hrData.get("companyName")
        );

        return ResponseEntity.ok(Map.of("message", "Employee deleted successfully"));
    }

    // ✅ FINAL FIXED METHOD
    private Map<String, String> fetchHrData(Authentication authentication) {

        Object principal = authentication.getPrincipal();

        String email;

        if (principal instanceof JwtPrincipal jwt) {
            email = jwt.getUsername();
        } else {
            email = principal.toString(); // fallback
        }

        ClientHrResponse<Map<String, String>> response =
                clientHrService.getHrByEmail(email);

        if (!response.isSuccess() || response.getData() == null) {
            throw new RuntimeException("HR not found in AuthService for: " + email);
        }

        return response.getData();
    }
}