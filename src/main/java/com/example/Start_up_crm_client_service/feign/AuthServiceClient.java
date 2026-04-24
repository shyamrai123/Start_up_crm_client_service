package com.example.Start_up_crm_client_service.feign;

import com.example.Start_up_crm_client_service.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "auth-service", url = "http://localhost:8080")
public interface AuthServiceClient {


    @PostMapping("/auth/generate-token")
    Map<String, String> generateToken(GenerateTokenRequest request);


    @PostMapping("/auth/login-client")
    Map<String, String> loginClient(
            @RequestBody ClientLoginRequest request
    );


    @PostMapping("/auth/login-hr")
    ClientHrResponse<Map<String, String>> loginHr(
            @RequestBody ClientHrLoginRequest request
    );


    // ✅ New method to fetch HR info by email
    @PostMapping("/auth/hr/email")
    ClientHrResponse<Map<String, String>> getHrByEmail(@RequestBody Map<String, String> request);



    @GetMapping("/employees/email/{email}")
    EmployeeResponse getEmployeeByEmail(@PathVariable String email);

}
