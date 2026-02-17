package com.example.Start_up_crm_client_service.controller;

import com.example.Start_up_crm_client_service.dto.ApiResponse;
import com.example.Start_up_crm_client_service.dto.ClientLoginRequest;
import com.example.Start_up_crm_client_service.dto.ClientSignupRequest;
import com.example.Start_up_crm_client_service.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;



//    @PostMapping("/register")
//    public ResponseEntity<ApiResponse<Map<String, String>>> registerClient(
//            @RequestBody ClientSignupRequest request) {
//
//        // request.certificatePath and request.logoPath should already contain uploaded file paths
//        ApiResponse<Map<String, String>> response = clientService.registerClient(request);
//
//        return ResponseEntity.status(response.getCode()).body(response);
//    }


//    @PostMapping(value = "/register", consumes = "multipart/form-data")
//    public ResponseEntity<ApiResponse<Map<String, String>>> registerClient(
//            @ModelAttribute ClientSignupRequest request) {
//
//        ApiResponse<Map<String, String>> response = clientService.registerClient(request);
//        return ResponseEntity.status(response.getCode()).body(response);
//    }

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ApiResponse signup(
            @ModelAttribute ClientSignupRequest request) {

        return clientService.registerClient(request);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> loginClient(
            @RequestBody ClientLoginRequest request) {

        return ResponseEntity.ok(clientService.loginClient(request));
    }

}