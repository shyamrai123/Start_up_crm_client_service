package com.example.Start_up_crm_client_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientHrResponse<T> {

    private int code;
    private boolean success;
    private String message;
    private T data;   // ✅ FIXED
}