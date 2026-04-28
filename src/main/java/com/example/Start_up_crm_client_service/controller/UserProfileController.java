package com.example.Start_up_crm_client_service.controller;

import com.example.Start_up_crm_client_service.dto.*;
import com.example.Start_up_crm_client_service.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserProfileController {

    private final UserProfileService service;

    // ================= CREATE =================//

    @PostMapping("/personal")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserPersonalResponse> createPersonal(@RequestBody UserPersonalRequest dto) {
        return ResponseEntity.ok(service.createPersonal(dto));
    }

    @PostMapping("/education")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserEducationResponse> createEducation(@RequestBody UserEducationRequest dto) {
        return ResponseEntity.ok(service.createEducation(dto));
    }

    @PostMapping("/career")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserCareerResponse> createCareer(@RequestBody UserCareerRequest dto) {
        return ResponseEntity.ok(service.createCareer(dto));
    }

    @PostMapping("/address")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserAddressResponse> createAddress(@RequestBody UserAddressRequest dto) {
        return ResponseEntity.ok(service.createAddress(dto));
    }

    @PostMapping("/emergency")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserEmergencyResponse> createEmergency(
            @RequestBody UserEmergencyRequest request) {

        return ResponseEntity.ok(
                service.createEmergency(request)
        );
    }

    // ================= GET SELF =================//

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserProfileResponse> getMyProfile() {
        return ResponseEntity.ok(service.getMyProfile());
    }

    @GetMapping("/me/personal")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserPersonalResponse> getMyPersonal() {
        return ResponseEntity.ok(service.getMyPersonal());
    }

    @GetMapping("/me/education")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserEducationResponse> getMyEducation() {
        return ResponseEntity.ok(service.getMyEducation());
    }

    @GetMapping("/me/career")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserCareerResponse> getMyCareer() {
        return ResponseEntity.ok(service.getMyCareer());
    }

    @GetMapping("/me/address")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserAddressResponse> getMyAddress() {
        return ResponseEntity.ok(service.getMyAddress());
    }

    @GetMapping("/me/emergency")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<UserEmergencyResponse>> getMyEmergency() {
        return ResponseEntity.ok(service.getMyEmergency());
    }


    //==================UPDATE PROFILES============//
    @PutMapping("/me/personal")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserPersonalResponse> updateMyPersonal(@RequestBody UserPersonalRequest dto) {
        return ResponseEntity.ok(service.updateMyPersonal(dto));
    }

    @PutMapping("/me/career")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserCareerResponse> updateMyCareer(@RequestBody UserCareerRequest dto) {
        return ResponseEntity.ok(service.updateMyCareer(dto));
    }

    @PutMapping("/me/address")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserAddressResponse> updateMyAddress(@RequestBody UserAddressRequest dto) {
        return ResponseEntity.ok(service.updateMyAddress(dto));
    }

    @PutMapping("/me/education/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserEducationResponse> updateMyEducation(
            @PathVariable Long id,
            @RequestBody UserEducationRequest dto) {
        return ResponseEntity.ok(service.updateMyEducation(id, dto));
    }

    @PutMapping("/me/emergency/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserEmergencyResponse> updateMyEmergency(
            @PathVariable Long id,
            @RequestBody UserEmergencyRequest dto) {
        return ResponseEntity.ok(service.updateMyEmergency(id, dto));
    }

    // ================= GET ORG =================//

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ORG')")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getUserProfile(userId));
    }
}