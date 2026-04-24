package com.example.Start_up_crm_client_service.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CreateTicketRequest {

    private String category;
    private String subject;
    private String description;
    private MultipartFile file;
}
