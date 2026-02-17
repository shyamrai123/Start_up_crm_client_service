package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.dto.ClientLoginRequest;
import com.example.Start_up_crm_client_service.dto.ClientSignupRequest;
import com.example.Start_up_crm_client_service.dto.ApiResponse;
import com.example.Start_up_crm_client_service.entity.Client;
import com.example.Start_up_crm_client_service.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

import static com.example.Start_up_crm_client_service.entity.Client.*;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    private String generateUniqueClientCode() {
        String code;
        do {
            int number = 100000 + new Random().nextInt(900000);
            code = String.valueOf(number);
        } while (clientRepository.existsByClientCode(code));
        return code;
    }

    @Override
    public ApiResponse<Map<String, String>> registerClient(ClientSignupRequest request) {
        if (clientRepository.existsByEmail(request.getEmail())) {
            return ApiResponse.error("Email already exists", HttpStatus.BAD_REQUEST.value());
        }

        try {
            String uploadDir = "uploads/";
            Files.createDirectories(Path.of(uploadDir));

            // Save certificate
            MultipartFile certFile = request.getCertificateFile();
            String certFileName = System.currentTimeMillis() + "_" + StringUtils.cleanPath(certFile.getOriginalFilename());
            Path certPath = Path.of(uploadDir + certFileName);
            Files.copy(certFile.getInputStream(), certPath, StandardCopyOption.REPLACE_EXISTING);

            // Save logo
            MultipartFile logoFile = request.getLogoFile();
            String logoFileName = System.currentTimeMillis() + "_" + StringUtils.cleanPath(logoFile.getOriginalFilename());
            Path logoPath = Path.of(uploadDir + logoFileName);
            Files.copy(logoFile.getInputStream(), logoPath, StandardCopyOption.REPLACE_EXISTING);

            Client client = builder()
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
                    .certificatePath(String.valueOf(request.getCertificateFile()))
                    .logoPath(String.valueOf(request.getLogoFile()))
                    .role("ROLE_ORG")
                    .build();

            Client saved = clientRepository.save(client);

            return ApiResponse.success("Registration Successful. Company ID: " + saved.getId(), null, HttpStatus.CREATED.value());

        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("Internal Server Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ApiResponse<Map<String, String>> loginClient(ClientLoginRequest request) {
        Optional<Client> optionalClient = clientRepository.findByEmail(request.getEmail());
        if (optionalClient.isEmpty()) {
            return ApiResponse.error("Invalid Email or Password", HttpStatus.UNAUTHORIZED.value());
        }

        Client client = optionalClient.get();
        if (!passwordEncoder.matches(request.getPassword(), client.getPassword())) {
            return ApiResponse.error("Invalid Email or Password", HttpStatus.UNAUTHORIZED.value());
        }

        Map<String, String> response = new HashMap<>();
        response.put("email", client.getEmail());
        response.put("role", client.getRole());

        return ApiResponse.success("Login Successful", response, HttpStatus.OK.value());
    }
}