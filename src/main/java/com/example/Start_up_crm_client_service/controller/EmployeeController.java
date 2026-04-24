package com.example.Start_up_crm_client_service.controller;

import com.example.Start_up_crm_client_service.dto.AddEmployeeRequest;
import com.example.Start_up_crm_client_service.dto.EmployeeResponse;
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
    public ResponseEntity<EmployeeResponse> addEmployee(
            @RequestBody AddEmployeeRequest request,
            Authentication authentication) {

        Map<String, String> hrData = fetchHrData(authentication);

        EmployeeResponse response = employeeService.addEmployee(
                request,
                hrData.get("clientCode"),
                hrData.get("companyName")
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ORG')")
    public ResponseEntity<List<EmployeeResponse>> getAll(Authentication authentication) {

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
    public ResponseEntity<EmployeeResponse> getById(
            @PathVariable Long id,
            Authentication authentication) {

        Map<String, String> hrData = fetchHrData(authentication);

        return ResponseEntity.ok(
                employeeService.getByIdAndClientCode(
                        id,
                        hrData.get("clientCode")
                )
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ORG')")
    public ResponseEntity<EmployeeResponse> update(
            @PathVariable Long id,
            @RequestBody AddEmployeeRequest request,
            Authentication authentication) {

        Map<String, String> hrData = fetchHrData(authentication);

        return ResponseEntity.ok(
                employeeService.updateEmployee(
                        id,
                        request,
                        hrData.get("clientCode")
                )
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ORG')")
    public ResponseEntity<String> delete(
            @PathVariable Long id,
            Authentication authentication) {

        Map<String, String> hrData = fetchHrData(authentication);

        employeeService.deleteEmployee(
                id,
                hrData.get("clientCode"),
                hrData.get("companyName")
        );

        return ResponseEntity.ok("Employee deleted successfully");
    }

    private Map<String, String> fetchHrData(Authentication authentication) {

        Object principal = authentication.getPrincipal();
        String email;

        if (principal instanceof JwtPrincipal jwt) {
            email = jwt.getUsername();
        } else {
            email = principal.toString();
        }

        var response = clientHrService.getHrByEmail(email);

        if (!response.isSuccess() || response.getData() == null) {
            throw new RuntimeException("HR not found: " + email);
        }

        return response.getData();
    }
}