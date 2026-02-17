package com.example.Start_up_crm_client_service.controller;

import com.example.Start_up_crm_client_service.dto.AddEmployeeRequest;
import com.example.Start_up_crm_client_service.dto.EmployeeResponse;
import com.example.Start_up_crm_client_service.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping(
            value = "/add",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public String addEmployee(@ModelAttribute AddEmployeeRequest request) {
        return employeeService.addEmployee(request);
    }

    @GetMapping("/all")
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping(
            value = "/update/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public String updateEmployee(
            @PathVariable Long id,
            @ModelAttribute AddEmployeeRequest request) {

        return employeeService.updateEmployee(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id);
    }
}