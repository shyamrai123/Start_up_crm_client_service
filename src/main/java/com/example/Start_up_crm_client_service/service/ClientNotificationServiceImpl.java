package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.entity.Client;
import com.example.Start_up_crm_client_service.repository.ClientRepository;
import com.example.Start_up_crm_client_service.service.ClientNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientNotificationServiceImpl implements ClientNotificationService {

    private final ClientRepository clientRepository;
    private final JavaMailSender mailSender;

    @Override
    public void sendClientRegistrationMail(String email) {

        // 🔹 Clean email input
        String cleanedEmail = email.trim();

        // 🔹 If multiple emails accidentally passed, split them
        String[] emailArray = cleanedEmail.contains(",")
                ? cleanedEmail.split("\\s*,\\s*")
                : new String[]{cleanedEmail};

        Client client = clientRepository.findByEmail(emailArray[0])
                .orElseThrow(() ->
                        new RuntimeException("Client not found with email: " + email));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("raishyamkumar21@gmail.com"); // ✅ Always set from
        message.setTo(emailArray);               // ✅ Correct way
        message.setSubject("StartAhb CRM - Client Registration Successful");

        message.setText(
                "Dear " + client.getCompanyName() + ",\n\n" +
                        "Welcome to StartAhb CRM!\n\n" +
                        "Your organization has been successfully registered.\n\n" +
                        "Client Code: " + client.getClientCode() + "\n" +
                        "Registered Email: " + client.getEmail() + "\n\n" +
                        "You can login using the link below:\n" +
                        "http://startahb.com/login-main\n\n" +
                        "For security reasons, please change your password after first login.\n\n" +
                        "Thank You,\n" +
                        "StartAhb CRM Team"
        );

        System.out.println("Incoming email value: " + email);

        mailSender.send(message);
    }
    }
