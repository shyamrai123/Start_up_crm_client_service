//package com.example.Start_up_crm_client_service.dto;
//
//import jakarta.validation.constraints.*;
//import lombok.*;
//import java.time.LocalDate;
//import java.util.List;
//
///**
// * DTOs used by HR to create / update employee profile sections.
// */
//public class HrEmployeeDtos {
//
//    // ─── 1. Create Employee (basic info HR fills first) ──────────────────────
//
//    @Data @NoArgsConstructor @AllArgsConstructor @Builder
//    public static class CreateEmployeeRequest {
//
//        @NotBlank private String firstName;
//        @NotBlank private String lastName;
//
//        @NotBlank @Email
//        private String email;
//
//        private String phone;
//        private String gender;           // MALE | FEMALE | OTHER
//        private String maritalStatus;    // SINGLE | MARRIED | DIVORCED | WIDOWED
//        private LocalDate dateOfBirth;
//        private String bloodGroup;
//
//        // Work info
//        @NotBlank private String department;
//        @NotBlank private String designation;
//        private Double salary;
//        private LocalDate joiningDate;
//        private String status;           // ACTIVE | INACTIVE
//
//        // Statutory
//        private String panNumber;
//        private String aadhaarNumber;
//        private String uan;
//        private String bankName;
//        private String accountNumber;
//        private String ifscCode;
//    }
//
//    // ─── 2. Address ──────────────────────────────────────────────────────────
//
//    @Data @NoArgsConstructor @AllArgsConstructor @Builder
//    public static class UpdateAddressRequest {
//        @NotBlank private String line1;
//        private String line2;
//        @NotBlank private String city;
//        @NotBlank private String state;
//        @NotBlank private String postalCode;
//        @NotBlank private String country;
//    }
//
//    // ─── 3. Education ────────────────────────────────────────────────────────
//
//    @Data @NoArgsConstructor @AllArgsConstructor @Builder
//    public static class UpdateEducationRequest {
//        @NotBlank private String highestQualification;
//        private String specialization;
//        private String university;
//        private Integer passedOutYear;
//    }
//
//    // ─── 4. Career ───────────────────────────────────────────────────────────
//
//    @Data @NoArgsConstructor @AllArgsConstructor @Builder
//    public static class UpdateCareerRequest {
//        private List<String> skills;
//        private String certifications;
//        private String linkedInUrl;
//        private String resumeUrl;
//    }
//
//    // ─── 5. Emergency Contacts ───────────────────────────────────────────────
//
//    @Data @NoArgsConstructor @AllArgsConstructor @Builder
//    public static class UpdateEmergencyContactsRequest {
//        @NotEmpty
//        private List<UserEmergencyContactDto> emergencyContacts;
//    }
//
//    // ─── 6. Patch Profile (employee edits own allowed section) ───────────────
//
//    @Data @NoArgsConstructor @AllArgsConstructor
//    public static class PatchProfileRequest {
//        // PERSONAL section
//        private String phone;
//        private String maritalStatus;
//
//        // ADDRESS section
//        private UserAddressDto address;
//
//        // EMERGENCY section
//        private List<UserEmergencyContactDto> emergencyContacts;
//
//        // CAREER section
//        private UserCareerDto careerDetails;
//
//        // EDUCATION section
//        private UserEducationDto educationalDetails;
//    }
//}