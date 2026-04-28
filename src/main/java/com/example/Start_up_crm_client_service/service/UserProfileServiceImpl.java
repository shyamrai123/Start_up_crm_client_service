package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.dto.*;
import com.example.Start_up_crm_client_service.entity.*;
import com.example.Start_up_crm_client_service.repository.*;
import com.example.Start_up_crm_client_service.security.JwtPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserPersonalRepository personalRepo;
    private final UserEducationRepository educationRepo;
    private final UserCareerRepository careerRepo;
    private final UserAddressRepository addressRepo;
    private final UserEmergencyContactRepository emergencyRepo;

    // ================= AUTH HELPERS =================

    private JwtPrincipal getPrincipal() {
        return (JwtPrincipal) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    private Long getCurrentUserId() {
        JwtPrincipal principal = getPrincipal();

        System.out.println("🔥 JWT PRINCIPAL: " + principal);
        System.out.println("🔥 USER ID FROM TOKEN: " + principal.getId());

        return principal.getId();
    }

    // ================= CREATE =================

    @Override
    public UserPersonalResponse createPersonal(UserPersonalRequest dto) {
        Long userId = getCurrentUserId();

        UserPersonal entity = UserPersonal.builder()
                .userId(userId)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .gender(dto.getGender())
                .designation(dto.getDesignation())
                .maritalStatus(dto.getMaritalStatus())
                .dateOfBirth(dto.getDateOfBirth())
                .build();

        return mapPersonal(personalRepo.save(entity));
    }

    @Override
    public UserEducationResponse createEducation(UserEducationRequest dto) {

        Long userId = getCurrentUserId();

        // 🔥 CHECK EXISTING
        UserEducation entity = educationRepo
                .findFirstByUserId(userId)
                .orElse(new UserEducation());

        entity.setUserId(userId);
        entity.setHighestQualification(dto.getHighestQualification());
        entity.setSpecialization(dto.getSpecialization());
        entity.setUniversity(dto.getUniversity());
        entity.setPassedOutYear(dto.getPassedOutYear());

        return mapEducation(educationRepo.save(entity));
    }

    @Override
    public UserCareerResponse createCareer(UserCareerRequest dto) {
        Long userId = getCurrentUserId();

        UserCareer entity = UserCareer.builder()
                .userId(userId)
                .skills(dto.getSkills().toString())
                .certifications(dto.getCertifications())
                .linkedInUrl(dto.getLinkedInUrl())
                .resumeUrl(dto.getResumeUrl())
                .build();

        return mapCareer(careerRepo.save(entity));
    }

    @Override
    public UserAddressResponse createAddress(UserAddressRequest dto) {
        Long userId = getCurrentUserId();

        UserAddress entity = UserAddress.builder()
                .userId(userId)
                .line1(dto.getLine1())
                .line2(dto.getLine2())
                .city(dto.getCity())
                .state(dto.getState())
                .postalCode(dto.getPostalCode())
                .country(dto.getCountry())
                .build();

        return mapAddress(addressRepo.save(entity));
    }

    @Override
    public UserEmergencyResponse createEmergency(UserEmergencyRequest dto) {
        Long userId = getCurrentUserId();

        UserEmergencyContact entity = UserEmergencyContact.builder()
                .userId(userId)
                .name(dto.getName())
                .relation(dto.getRelation())
                .phone(dto.getPhone())
                .alternatePhone(dto.getAlternatePhone())
                .build();

        return mapEmergency(emergencyRepo.save(entity));
    }


//    @Override
//    public List<UserEmergencyResponse> createMultipleEmergency(List<UserEmergencyRequest> dtos) {
//
//        Long userId = getCurrentUserId();
//
//        return dtos.stream().map(dto -> {
//
//            UserEmergencyContact entity = UserEmergencyContact.builder()
//                    .userId(userId)
//                    .name(dto.getName())
//                    .relation(dto.getRelation())
//                    .phone(dto.getPhone())
//                    .alternatePhone(dto.getAlternatePhone())
//                    .build();
//
//            emergencyRepo.save(entity);
//            return mapEmergency(entity);
//
//        }).toList();
//    }

    // ================= GET SELF =================
    @Override
    public UserPersonalResponse getMyPersonal() {
        Long userId = getCurrentUserId();
        return mapPersonal(personalRepo.findByUserId(userId).orElse(null));
    }

    @Override
    public UserEducationResponse getMyEducation() {
        Long userId = getCurrentUserId();

        return educationRepo.findFirstByUserId(userId)
                .map(this::mapEducation)
                .orElse(null);
    }

    @Override
    public UserCareerResponse getMyCareer() {
        Long userId = getCurrentUserId();
        return mapCareer(careerRepo.findByUserId(userId).orElse(null));
    }

    @Override
    public UserAddressResponse getMyAddress() {
        Long userId = getCurrentUserId();
        return mapAddress(addressRepo.findByUserId(userId).orElse(null));
    }

    @Override
    public List<UserEmergencyResponse> getMyEmergency() {
        Long userId = getCurrentUserId();
        return emergencyRepo.findByUserId(userId)
                .stream().map(this::mapEmergency).toList();
    }

    // ================= GET BY USER ID =================

    @Override
    public UserPersonalResponse getPersonalByUserId(Long userId) {
        return personalRepo.findByUserId(userId)
                .map(this::mapPersonal)
                .orElse(null);
    }

    @Override
    public UserEducationResponse getEducationByUserId(Long userId) {
        return educationRepo.findFirstByUserId(userId)
                .map(this::mapEducation)
                .orElse(null);
    }

    @Override
    public UserCareerResponse getCareerByUserId(Long userId) {
        return careerRepo.findByUserId(userId)
                .map(this::mapCareer)
                .orElse(null);
    }

    @Override
    public UserAddressResponse getAddressByUserId(Long userId) {
        return addressRepo.findByUserId(userId)
                .map(this::mapAddress)
                .orElse(null);
    }

    @Override
    public List<UserEmergencyResponse> getEmergencyByUserId(Long userId) {
        return emergencyRepo.findByUserId(userId)
                .stream().map(this::mapEmergency).toList();
    }

    // ================= FULL PROFILE =================

    @Override
    public UserProfileResponse getMyProfile() {
        return getUserProfile(getCurrentUserId());
    }

    @Override
    public UserProfileResponse getUserProfile(Long userId) {
        return UserProfileResponse.builder()
                .personal(getPersonalByUserId(userId))
                .education(getEducationByUserId(userId))
                .career(getCareerByUserId(userId))
                .address(getAddressByUserId(userId))
                .emergency(getEmergencyByUserId(userId))
                .build();
    }


    //=================UPDATE PROFILE===============//

    @Override
    public UserPersonalResponse updateMyPersonal(UserPersonalRequest dto) {
        Long userId = getCurrentUserId();

        UserPersonal entity = personalRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Personal not found"));

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setGender(dto.getGender());
        entity.setMaritalStatus(dto.getMaritalStatus());
        entity.setDateOfBirth(dto.getDateOfBirth());

        return mapPersonal(personalRepo.save(entity));
    }

    @Override
    public UserCareerResponse updateMyCareer(UserCareerRequest dto) {
        Long userId = getCurrentUserId();

        UserCareer entity = careerRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Career not found"));

        entity.setSkills(dto.getSkills().toString());
        entity.setCertifications(dto.getCertifications());
        entity.setLinkedInUrl(dto.getLinkedInUrl());
        entity.setResumeUrl(dto.getResumeUrl());

        return mapCareer(careerRepo.save(entity));
    }

    @Override
    public UserEducationResponse updateMyEducation(Long id, UserEducationRequest dto) {

        Long userId = getCurrentUserId();

        UserEducation entity = educationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Education not found"));

        if (!entity.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        entity.setHighestQualification(dto.getHighestQualification());
        entity.setSpecialization(dto.getSpecialization());
        entity.setUniversity(dto.getUniversity());
        entity.setPassedOutYear(dto.getPassedOutYear());

        return mapEducation(educationRepo.save(entity));
    }

    @Override
    public UserAddressResponse updateMyAddress(UserAddressRequest dto) {
        Long userId = getCurrentUserId();

        UserAddress entity = addressRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        entity.setLine1(dto.getLine1());
        entity.setLine2(dto.getLine2());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setPostalCode(dto.getPostalCode());
        entity.setCountry(dto.getCountry());

        return mapAddress(addressRepo.save(entity));
    }

    @Override
    public UserEmergencyResponse updateMyEmergency(Long id, UserEmergencyRequest dto) {

        Long userId = getCurrentUserId();

        UserEmergencyContact entity = emergencyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Emergency not found"));

        if (!entity.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        entity.setName(dto.getName());
        entity.setRelation(dto.getRelation());
        entity.setPhone(dto.getPhone());
        entity.setAlternatePhone(dto.getAlternatePhone());

        return mapEmergency(emergencyRepo.save(entity));
    }



    // ================= MAPPERS =================

    private UserPersonalResponse mapPersonal(UserPersonal e) {
        if (e == null) return null;
        return UserPersonalResponse.builder()
                .userId(e.getUserId())
                .firstName(e.getFirstName())
                .lastName(e.getLastName())
                .email(e.getEmail())
                .phone(e.getPhone())
                .designation(e.getDesignation())
                .gender(e.getGender())
                .maritalStatus(e.getMaritalStatus())
                .dateOfBirth(e.getDateOfBirth())
                .build();
    }

    private UserEducationResponse mapEducation(UserEducation e) {
        return UserEducationResponse.builder()
                .id(e.getId())   // ✅ ADD THIS
                .userId(e.getUserId())
                .highestQualification(e.getHighestQualification())
                .specialization(e.getSpecialization())
                .university(e.getUniversity())
                .passedOutYear(e.getPassedOutYear())
                .build();
    }

    private UserCareerResponse mapCareer(UserCareer e) {
        if (e == null) return null;
        return UserCareerResponse.builder()
                .userId(e.getUserId())
                .skills(List.of(e.getSkills()))
                .certifications(e.getCertifications())
                .linkedInUrl(e.getLinkedInUrl())
                .resumeUrl(e.getResumeUrl())
                .build();
    }

    private UserAddressResponse mapAddress(UserAddress e) {
        if (e == null) return null;
        return UserAddressResponse.builder()
                .userId(e.getUserId())
                .line1(e.getLine1())
                .line2(e.getLine2())
                .city(e.getCity())
                .state(e.getState())
                .postalCode(e.getPostalCode())
                .country(e.getCountry())
                .build();
    }

    private UserEmergencyResponse mapEmergency(UserEmergencyContact e) {
        return UserEmergencyResponse.builder()
                .id(e.getId()) // ✅ ADD THIS
                .userId(e.getUserId())
                .name(e.getName())
                .relation(e.getRelation())
                .phone(e.getPhone())
                .alternatePhone(e.getAlternatePhone())
                .build();
    }
}