package com.example.Start_up_crm_client_service.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtPrincipal {

    private Long id;
    private String username;
}