package com.example.Start_up_crm_client_service.controller;

import com.example.Start_up_crm_client_service.dto.*;
import com.example.Start_up_crm_client_service.service.ClientHrService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/hr")
@RequiredArgsConstructor
public class ClientHrController {

    private final ClientHrService clientHrService;


    @PostMapping("/login")
    public ClientHrResponse<Map<String, String>> login(
            @RequestBody ClientHrLoginRequest request) {

        return clientHrService.login(request);
    }
}