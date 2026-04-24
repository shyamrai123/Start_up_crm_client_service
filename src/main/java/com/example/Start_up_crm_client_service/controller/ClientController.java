package com.example.Start_up_crm_client_service.controller;

import com.example.Start_up_crm_client_service.dto.ApiResponse;
import com.example.Start_up_crm_client_service.dto.ClientLoginRequest;
import com.example.Start_up_crm_client_service.dto.ClientSignupRequest;
import com.example.Start_up_crm_client_service.feign.AuthServiceClient;
import com.example.Start_up_crm_client_service.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final AuthServiceClient authServiceClient;


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> loginClient(
            @RequestBody ClientLoginRequest request) {

        ApiResponse<Map<String, String>> response =
                clientService.loginClient(request);

        return ResponseEntity.status(response.getCode()).body(response);
    }


}