package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.dto.*;
import com.example.Start_up_crm_client_service.entity.Client;
import com.example.Start_up_crm_client_service.entity.ClientHr;
import com.example.Start_up_crm_client_service.repository.ClientHrRepository;
import com.example.Start_up_crm_client_service.repository.ClientRepository;
import com.example.Start_up_crm_client_service.service.ClientHrService;
import com.example.Start_up_crm_client_service.util.JwtTokenUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientHrServiceImpl implements ClientHrService {

    private final ClientHrRepository clientHrRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    // ========================
    // HR SIGNUP
    // ========================
    @Override
    public HrResponse signup(ClientHrSignupRequest request) {

        Optional<Client> clientOptional =
                clientRepository.findById(request.getClientId());

        if (clientOptional.isEmpty()) {
            return new HrResponse(404, false,
                    "Company not found", null);
        }

        // Check duplicate email under same company
        if (clientHrRepository
                .findByEmailAndClientId(
                        request.getEmail(),
                        request.getClientId())
                .isPresent()) {

            return new HrResponse(400, false,
                    "HR already exists for this company", null);
        }

        ClientHr hr = ClientHr.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .client(clientOptional.get())
                .build();

        clientHrRepository.save(hr);

        return new HrResponse(200, true,
                "HR registered successfully", null);
    }

    // ========================
    // HR LOGIN
    // ========================
    @Override
    public HrResponse login(ClientHrLoginRequest request) {

        Optional<ClientHr> optionalHr = clientHrRepository.findByEmailAndClientId(request.getEmail(),request.getClientId());

        if (optionalHr.isEmpty()) {
            return new HrResponse(404, false, "HR not found", null);
        }

        ClientHr hr = optionalHr.get();

        if (!passwordEncoder.matches(request.getPassword(), hr.getPassword())) {
            return new HrResponse(401, false, "Invalid password", null);
        }

        return new HrResponse(200, true, "Login successful", null);
    }
}