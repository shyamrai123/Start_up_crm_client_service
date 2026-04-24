package com.example.Start_up_crm_client_service.service;

import com.example.Start_up_crm_client_service.dto.*;

import java.util.List;

public interface UserProfileService {

    // ================= CREATE =================
    UserPersonalResponse createPersonal(UserPersonalRequest dto);
    UserEducationResponse createEducation(UserEducationRequest dto);
    UserCareerResponse createCareer(UserCareerRequest dto);
    UserAddressResponse createAddress(UserAddressRequest dto);
    UserEmergencyResponse createEmergency(UserEmergencyRequest dto);

//    List<UserEmergencyResponse> createMultipleEmergency(List<UserEmergencyRequest> dtos);

    // ================= GET (SELF) =================
    UserPersonalResponse getMyPersonal();
    UserEducationResponse getMyEducation();
    UserCareerResponse getMyCareer();
    UserAddressResponse getMyAddress();
    List<UserEmergencyResponse> getMyEmergency();

    // ================= GET (ORG) =================
    UserPersonalResponse getPersonalByUserId(Long userId);
    UserEducationResponse getEducationByUserId(Long userId);
    UserCareerResponse getCareerByUserId(Long userId);
    UserAddressResponse getAddressByUserId(Long userId);
    List<UserEmergencyResponse> getEmergencyByUserId(Long userId);

    // ================= FULL PROFILE =================
    UserProfileResponse getMyProfile();
    UserProfileResponse getUserProfile(Long userId);


    // ================= UPDATE SELF =================
    UserPersonalResponse updateMyPersonal(UserPersonalRequest dto);
    UserCareerResponse updateMyCareer(UserCareerRequest dto);
    UserAddressResponse updateMyAddress(UserAddressRequest dto);
    UserEducationResponse updateMyEducation(Long id, UserEducationRequest dto);
    UserEmergencyResponse updateMyEmergency(Long id, UserEmergencyRequest dto);
}