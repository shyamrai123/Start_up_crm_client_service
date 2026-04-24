package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.dto.ClientLoginRequest;
import com.example.Start_up_crm_client_service.dto.ClientSignupRequest;
import com.example.Start_up_crm_client_service.dto.ApiResponse;
import com.example.Start_up_crm_client_service.dto.GenerateTokenRequest;
import com.example.Start_up_crm_client_service.entity.Client;
import com.example.Start_up_crm_client_service.entity.Role;
import com.example.Start_up_crm_client_service.feign.AuthServiceClient;
import com.example.Start_up_crm_client_service.repository.ClientRepository;
import com.example.Start_up_crm_client_service.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private  final AuthServiceClient authServiceClient;
    //    private final JwtTokenUtil jwtTokenUtil;
    private final ClientNotificationService clientNotificationService; // ✅ Injected

    // ✅ Generate unique 6-digit client code
    private String generateUniqueClientCode() {
        String code;
        do {
            int number = 100000 + new Random().nextInt(900000);
            code = String.valueOf(number);
        } while (clientRepository.existsByClientCode(code));
        return code;
    }

    // =====================================================
    // ✅ REGISTER CLIENT
    // =====================================================

    @Override
    public ApiResponse<Map<String, String>> registerClient(ClientSignupRequest request) {

        List<String> errors = new ArrayList<>();

        if (clientRepository.existsByEmail(request.getEmail())) {
            errors.add("Email already exists");
        }

        if (clientRepository.existsByCompanyName(request.getCompanyName())) {
            errors.add("Company name already exists");
        }

        if (!errors.isEmpty()) {
            return ApiResponse.error(
                    String.join(", ", errors),
                    HttpStatus.BAD_REQUEST.value()
            );
        }

        try {

            String uploadDir = "uploads/";
            Files.createDirectories(Path.of(uploadDir));

            // ✅ Save certificate (null-safe)
            String certFileName = null;
            MultipartFile certFile = request.getCertificateFile();
            if (certFile != null && !certFile.isEmpty()) {
                certFileName = System.currentTimeMillis() + "_" +
                        StringUtils.cleanPath(certFile.getOriginalFilename());

                Path certPath = Path.of(uploadDir + certFileName);
                Files.copy(certFile.getInputStream(), certPath,
                        StandardCopyOption.REPLACE_EXISTING);
            }

            // ✅ Save logo (null-safe)
            String logoFileName = null;
            MultipartFile logoFile = request.getLogoFile();
            if (logoFile != null && !logoFile.isEmpty()) {
                logoFileName = System.currentTimeMillis() + "_" +
                        StringUtils.cleanPath(logoFile.getOriginalFilename());

                Path logoPath = Path.of(uploadDir + logoFileName);
                Files.copy(logoFile.getInputStream(), logoPath,
                        StandardCopyOption.REPLACE_EXISTING);
            }

            // ✅ Save Client
            Client client = Client.builder()
                    .clientCode(generateUniqueClientCode())
                    .companyName(request.getCompanyName())
                    .email(request.getEmail())
                    .mobileNumber(request.getMobileNumber())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .domain(request.getDomain())
                    .address(request.getAddress())
                    .country(request.getCountry())
                    .state(request.getState())
                    .postalCode(request.getPostalCode())
                    .certificatePath(certFileName)
                    .logoPath(logoFileName)
                    .role("ROLE_ORG")
                    .build();

            Client saved = clientRepository.save(client);

            // ✅ Send Registration Email (Safe Execution)
            try {
                clientNotificationService.sendClientRegistrationMail(saved.getEmail());
            } catch (Exception mailException) {
                mailException.printStackTrace();
                // Do NOT fail registration if email fails
            }

            return ApiResponse.success(
                    "Registration Successful. Company ID: " + saved.getId(),
                    null,
                    HttpStatus.CREATED.value()
            );

        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(
                    "Internal Server Error: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }

    // =====================================================
    // ✅ LOGIN CLIENT
    // =====================================================


    @Override
    public ApiResponse<Map<String, String>> loginClient(ClientLoginRequest request) {

        Map<String, String> tokenResponse =
                authServiceClient.loginClient(request);

        return ApiResponse.success(
                "Login Successful",
                tokenResponse,
                HttpStatus.OK.value()
        );

    }
}



//
//    @Override
//    public ApiResponse<Map<String, String>> loginClient(ClientLoginRequest request) {
//
//        Optional<Client> optionalClient =
//                clientRepository.findByClientCodeAndEmail(
//                        request.getClientCode(),
//                        request.getEmail()
//                );
//
//        if (optionalClient.isEmpty()) {
//            return ApiResponse.error(
//                    "Invalid Client ID or Email",
//                    HttpStatus.UNAUTHORIZED.value()
//            );
//        }
//
//        Client client = optionalClient.get();
//
//        if (!passwordEncoder.matches(request.getPassword(), client.getPassword())) {
//            return ApiResponse.error(
//                    "Invalid Password",
//                    HttpStatus.UNAUTHORIZED.value()
//            );
//        }
//
//        // ✅ Create ROLE set for JWT
//        Set<Role> roles = new HashSet<>();
//        Role role = new Role();
//        role.setName(com.example.Start_up_crm_client_service.entity.RoleName.ROLE_ORG);
//        roles.add(role);
//
//
//
//        Map<String, String> response = new HashMap<>();
//        response.put("role", "ROLE_ORG");
//        response.put("clientCode", client.getClientCode());
//
//        return ApiResponse.success(
//                "Login Successful",
//                response,
//                HttpStatus.OK.value()
//        );
//    }
//}