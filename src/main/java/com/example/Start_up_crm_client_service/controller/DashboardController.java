package com.example.Start_up_crm_client_service.controller;

import com.example.Start_up_crm_client_service.dto.DashboardResponse;
import com.example.Start_up_crm_client_service.entity.ClientHr;
import com.example.Start_up_crm_client_service.repository.ClientHrRepository;
import com.example.Start_up_crm_client_service.security.CustomUserDetails;
import com.example.Start_up_crm_client_service.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;
    private final ClientHrRepository clientHrRepository;

    @GetMapping("/summary")
    @PreAuthorize("hasAuthority('ROLE_ORG')")
    public ResponseEntity<DashboardResponse> getDashboard(Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        String email = userDetails.getUsername();

        ClientHr hr = clientHrRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("HR not found"));

        DashboardResponse response =
                dashboardService.getDashboardData(hr.getClientCode());

        return ResponseEntity.ok(response);
    }
}