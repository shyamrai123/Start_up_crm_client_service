package com.example.Start_up_crm_client_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsersValidationRequest {

    private List<Long> userIds;

}
