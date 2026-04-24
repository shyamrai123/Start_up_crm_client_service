//package com.example.Start_up_crm_client_service.dto;
//
//import com.example.Start_up_crm_client_service.entity.PermissionStatus;
//import com.example.Start_up_crm_client_service.entity.ProfileSection;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
//import lombok.Data;
//import java.time.LocalDateTime;
//
//// ─────────────────────────────────────────────
//// Employee sends this to REQUEST edit permission
//// ─────────────────────────────────────────────
//public class EditPermissionDtos {
//
//    @Data
//    public static class RequestEditPermissionDto {
//
//        @NotNull(message = "Section is required")
//        private ProfileSection section;
//
//        @NotBlank(message = "Reason is required")
//        @Size(min = 10, max = 500, message = "Reason must be between 10 and 500 characters")
//        private String reason;
//    }
//
//    // ─────────────────────────────────────────────
//    // HR sends this to REVIEW (approve/reject)
//    // ─────────────────────────────────────────────
//    @Data
//    public static class ReviewPermissionDto {
//
//        @NotNull(message = "Status is required (APPROVED or REJECTED)")
//        private PermissionStatus status; // Only APPROVED or REJECTED allowed
//
//        @Size(max = 500, message = "Note must be under 500 characters")
//        private String hrNote;
//
//        // Optional: how many hours the permission is valid (default 24h)
//        private Integer validForHours;
//    }
//
//    // ─────────────────────────────────────────────
//    // Response DTO
//    // ─────────────────────────────────────────────
//    @Data
//    public static class EditPermissionResponseDto {
//
//        private Long id;
//        private Long employeeId;
//        private String employeeName;
//        private ProfileSection section;
//        private PermissionStatus status;
//        private String reason;
//        private String hrNote;
//        private String reviewedBy;
//        private LocalDateTime requestedAt;
//        private LocalDateTime reviewedAt;
//        private LocalDateTime expiresAt;
//        private boolean expired;
//    }
//
//    // ─────────────────────────────────────────────
//    // Section lock status for frontend
//    // ─────────────────────────────────────────────
//    @Data
//    public static class SectionLockStatusDto {
//
//        private ProfileSection section;
//        private boolean filled;       // Has employee filled this section?
//        private boolean locked;       // Is it currently locked?
//        private boolean canRequest;   // Can employee request permission?
//        private PermissionStatus pendingStatus; // null if no request
//        private LocalDateTime expiresAt;        // When permission expires
//    }
//}