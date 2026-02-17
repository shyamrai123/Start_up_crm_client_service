package com.example.Start_up_crm_client_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UsersValidationResponse {

    private List<Long> validUserIds;
    private List<Long> invalidUserIds;
}
