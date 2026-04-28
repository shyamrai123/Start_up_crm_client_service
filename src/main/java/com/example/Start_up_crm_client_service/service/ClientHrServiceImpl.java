package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.dto.*;
import com.example.Start_up_crm_client_service.feign.AuthServiceClient;
import com.example.Start_up_crm_client_service.repository.ClientHrRepository;
import com.example.Start_up_crm_client_service.repository.ClientRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClientHrServiceImpl implements ClientHrService {

    private final ClientHrRepository clientHrRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthServiceClient authServiceClient;


//    private final JwtTokenUtil jwtTokenUtil;

    // ========================
    // HR SIGNUP
    // ========================
//    @Override
//    public HrResponse signup(ClientHrSignupRequest request) {
//
//        Optional<Client> optionalClient =
//                clientRepository.findByClientCode(request.getClientCode());
//
//        if (optionalClient.isEmpty()) {
//            return new HrResponse(
//                    404,
//                    false,
//                    "Invalid Client Code. Company not found.",
//                    null
//            );
//        }
//
//        Client client = optionalClient.get();
//
//        if (clientHrRepository
//                .findByEmailAndClient_Id(request.getEmail(), client.getId())
//                .isPresent()) {
//
//            return new HrResponse(
//                    400,
//                    false,
//                    "HR already exists for this company",
//                    null
//            );
//        }
//
//        ClientHr hr = ClientHr.builder()
//                .fullName(request.getFullName())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .clientCode(client.getClientCode())        // ✅ from client table
//                .companyName(client.getCompanyName())
//                .client(client)
//                .build();
//
//        clientHrRepository.save(hr);
//
//        return new HrResponse(
//                200,
//                true,
//                "HR Registration Successful",   // ✅ Updated message
//                null
//        );
//    }

    // ========================
    // HR LOGIN
    // ========================

    @Override
    public ClientHrResponse<Map<String, String>> login(ClientHrLoginRequest request) {
        return authServiceClient.loginHr(request);
    }

    @Override
    public ClientHrResponse<Map<String, String>> getHrByEmail(String email) {
        return authServiceClient.getHrByEmail(Map.of("email", email));
    }
//    @Override
//    public HrResponse login(ClientHrLoginRequest request) {
//
//        // 1️⃣ Check client by clientCode
//        Optional<Client> optionalClient =
//                clientRepository.findByClientCode(request.getClientCode());
//
//        if (optionalClient.isEmpty()) {
//            return new HrResponse(404, false, "Invalid Client Code", null);
//        }
//
//        Client client = optionalClient.get();
//
//        // 2️⃣ Find HR by email + clientId
//        Optional<ClientHr> optionalHr =
//                clientHrRepository.findByEmailAndClient_Id(
//                        request.getEmail(),
//                        client.getId()
//                );
//
//        if (optionalHr.isEmpty()) {
//            return new HrResponse(404, false, "HR not found", null);
//        }
//
//        ClientHr hr = optionalHr.get();
//
//        // 3️⃣ Validate password
//        if (!passwordEncoder.matches(request.getPassword(), hr.getPassword())) {
//            return new HrResponse(401, false, "Invalid credentials", null);
//        }
//
//        // 4️⃣ Generate JWT Token
//
//
//        Role hrRole = new Role();
//        hrRole.setName(RoleName.ROLE_ORG);
//
//        Set<Role> roles = Set.of(hrRole);
//
//
//
//        return new HrResponse(
//
//        );
//    }


}