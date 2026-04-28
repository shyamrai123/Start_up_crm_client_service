//package com.example.Start_up_crm_client_service.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "edit_permission_requests",
//        uniqueConstraints = {
//                // One active request per employee per section at a time
//                @UniqueConstraint(columnNames = {"employee_id", "section", "status"})
//        })
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class EditPermissionRequest {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // Which employee is requesting
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "employee_id", nullable = false)
//    private Employee employee;
//
//    // Which section they want to edit: PERSONAL, EDUCATION, CAREER, EMERGENCY, LEAVE, STATUS
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private ProfileSection section;
//
//    // PENDING → HR reviews → APPROVED or REJECTED
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    @Builder.Default
//    private PermissionStatus status = PermissionStatus.PENDING;
//
//    @Column(length = 500)
//    private String reason; // Employee's reason for requesting edit
//
//    @Column(length = 500)
//    private String hrNote; // HR's note when approving/rejecting
//
//    // Who approved/rejected (HR email)
//    private String reviewedBy;
//
//    @Column(nullable = false, updatable = false)
//    private LocalDateTime requestedAt;
//
//    private LocalDateTime reviewedAt;
//
//    // Once approved, permission expires after this time (nullable = no expiry)
//    private LocalDateTime expiresAt;
//
//    @PrePersist
//    protected void onCreate() {
//        this.requestedAt = LocalDateTime.now();
//    }
//
//    public boolean isExpired() {
//        return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
//    }
//
//    public boolean isActiveApproval() {
//        return status == PermissionStatus.APPROVED && !isExpired();
//    }
//}