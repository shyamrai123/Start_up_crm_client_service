//package com.example.Start_up_crm_client_service.service;
//
//import com.example.Start_up_crm_client_service.dto.EditPermissionDtos.*;
//import com.example.Start_up_crm_client_service.entity.ProfileSection;
//
//import java.util.List;
//
//public interface EditPermissionService {
//
//    // ── EMPLOYEE ──────────────────────────────────────────────────
//
//    // Employee requests permission to edit a section
//    EditPermissionResponseDto requestPermission(Long employeeId, RequestEditPermissionDto dto);
//
//    // Employee checks lock status of all their sections
//    List<SectionLockStatusDto> getSectionLockStatus(Long employeeId);
//
//    // Employee views their own permission requests
//    List<EditPermissionResponseDto> getMyRequests(Long employeeId);
//
//    // ── HR ────────────────────────────────────────────────────────
//
//    // HR views all PENDING requests for their company
//    List<EditPermissionResponseDto> getPendingRequests(String clientCode);
//
//    // HR views all requests for their company (all statuses)
//    List<EditPermissionResponseDto> getAllRequests(String clientCode);
//
//    // HR approves or rejects a request
//    EditPermissionResponseDto reviewRequest(Long requestId, String hrEmail,
//                                            String clientCode, ReviewPermissionDto dto);
//
//    // ── INTERNAL (used by EmployeeProfileService) ─────────────────
//
//    // Check if employee has permission to edit a section
//    boolean hasEditPermission(Long employeeId, ProfileSection section);
//
//    // Mark permission as USED after employee successfully edits
//    void markPermissionUsed(Long employeeId, ProfileSection section);
//}