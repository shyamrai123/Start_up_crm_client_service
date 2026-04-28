package com.example.Start_up_crm_client_service.controller;

import com.example.Start_up_crm_client_service.dto.ClientHrResponse;
import com.example.Start_up_crm_client_service.dto.DashboardResponse;
import com.example.Start_up_crm_client_service.security.JwtPrincipal;
import com.example.Start_up_crm_client_service.service.ClientHrService;
import com.example.Start_up_crm_client_service.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DashboardController {

    private final DashboardService dashboardService;
    private final ClientHrService clientHrService;

    @GetMapping("/summary")
    @PreAuthorize("hasAuthority('ROLE_ORG')")
    public ResponseEntity<DashboardResponse> getDashboard(Authentication authentication) {

        String clientCode = getClientCode(authentication);

        return ResponseEntity.ok(
                dashboardService.getDashboardData(clientCode)
        );
    }

    private String getClientCode(Authentication authentication) {

        Object principal = authentication.getPrincipal();

        String email;

        if (principal instanceof JwtPrincipal jwt) {
            email = jwt.getUsername();
        } else {
            email = principal.toString();
        }

        ClientHrResponse<Map<String, String>> response =
                clientHrService.getHrByEmail(email);

        if (!response.isSuccess() || response.getData() == null) {
            throw new RuntimeException("HR not found in AuthService for: " + email);
        }

        return response.getData().get("clientCode");
    }
}