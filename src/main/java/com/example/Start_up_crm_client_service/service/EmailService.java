package com.example.Start_up_crm_client_service.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}