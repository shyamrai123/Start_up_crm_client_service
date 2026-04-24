//package com.example.Start_up_crm_client_service.service;
//
//import com.example.Start_up_crm_client_service.dto.EditPermissionDtos.*;
//import com.example.Start_up_crm_client_service.entity.*;
//import com.example.Start_up_crm_client_service.repository.EditPermissionRequestRepository;
//import com.example.Start_up_crm_client_service.repository.EmployeeRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class EditPermissionServiceImpl implements EditPermissionService {
//
//    private final EditPermissionRequestRepository permissionRepo;
//    private final EmployeeRepository employeeRepo;
//    private final EmployeeProfileServiceImpl profileService;
//
//    // ── EMPLOYEE ──────────────────────────────────────────────────
//
//    @Override
//    @Transactional
//    public EditPermissionResponseDto requestPermission(Long employeeId,
//                                                       RequestEditPermissionDto dto) {
//        Employee employee = employeeRepo.findById(employeeId)
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//
//        if (!isSectionFilled(employeeId, dto.getSection())) {
//            throw new RuntimeException(
//                    "Section " + dto.getSection() + " is not filled yet. Fill it first.");
//        }
//
//        if (permissionRepo.existsByEmployee_IdAndSectionAndStatus(
//                employeeId, dto.getSection(), PermissionStatus.PENDING)) {
//            throw new RuntimeException(
//                    "You already have a pending request for " + dto.getSection()
//                            + " section. Please wait for HR to review it.");
//        }
//
//        if (hasEditPermission(employeeId, dto.getSection())) {
//            throw new RuntimeException(
//                    "You already have an active permission for " + dto.getSection()
//                            + " section. Please use it before requesting again.");
//        }
//
//        EditPermissionRequest request = EditPermissionRequest.builder()
//                .employee(employee)
//                .section(dto.getSection())
//                .reason(dto.getReason())
//                .status(PermissionStatus.PENDING)
//                .build();
//
//        return toDto(permissionRepo.save(request));
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<SectionLockStatusDto> getSectionLockStatus(Long employeeId) {
//        return Arrays.stream(ProfileSection.values())
//                .map(section -> buildLockStatus(employeeId, section))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<EditPermissionResponseDto> getMyRequests(Long employeeId) {
//        return permissionRepo.findByEmployee_IdOrderByRequestedAtDesc(employeeId)
//                .stream()
//                .map(this::toDto)
//                .collect(Collectors.toList());
//    }
//
//    // ── HR ────────────────────────────────────────────────────────
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<EditPermissionResponseDto> getPendingRequests(String clientCode) {
//        return permissionRepo
//                .findByEmployee_ClientCodeAndStatusOrderByRequestedAtDesc(
//                        clientCode, PermissionStatus.PENDING)
//                .stream()
//                .map(this::toDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<EditPermissionResponseDto> getAllRequests(String clientCode) {
//        return permissionRepo
//                .findByEmployee_ClientCodeOrderByRequestedAtDesc(clientCode)
//                .stream()
//                .map(this::toDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    @Transactional
//    public EditPermissionResponseDto reviewRequest(Long requestId, String hrEmail,
//                                                   String clientCode,
//                                                   ReviewPermissionDto dto) {
//        if (dto.getStatus() != PermissionStatus.APPROVED
//                && dto.getStatus() != PermissionStatus.REJECTED) {
//            throw new RuntimeException("Status must be APPROVED or REJECTED");
//        }
//
//        EditPermissionRequest request = permissionRepo.findById(requestId)
//                .orElseThrow(() -> new RuntimeException("Permission request not found"));
//
//        if (!request.getEmployee().getClientCode().equals(clientCode)) {
//            throw new RuntimeException("Access denied: This request belongs to a different company");
//        }
//
//        if (request.getStatus() != PermissionStatus.PENDING) {
//            throw new RuntimeException(
//                    "Request is already " + request.getStatus() + " and cannot be reviewed again");
//        }
//
//        request.setStatus(dto.getStatus());
//        request.setHrNote(dto.getHrNote());
//        request.setReviewedBy(hrEmail);
//        request.setReviewedAt(LocalDateTime.now());
//
//        if (dto.getStatus() == PermissionStatus.APPROVED) {
//            int hours = (dto.getValidForHours() != null && dto.getValidForHours() > 0)
//                    ? dto.getValidForHours() : 24;
//            request.setExpiresAt(LocalDateTime.now().plusHours(hours));
//        }
//
//        return toDto(permissionRepo.save(request));
//    }
//
//    // ── INTERNAL ──────────────────────────────────────────────────
//
//    @Override
//    @Transactional(readOnly = true)
//    public boolean hasEditPermission(Long employeeId, ProfileSection section) {
//        return permissionRepo.findActiveApproval(employeeId, section)
//                .map(r -> !r.isExpired())
//                .orElse(false);
//    }
//
//    @Override
//    @Transactional
//    public void markPermissionUsed(Long employeeId, ProfileSection section) {
//        permissionRepo.findActiveApproval(employeeId, section)
//                .ifPresent(request -> {
//                    request.setStatus(PermissionStatus.USED);
//                    permissionRepo.save(request);
//                });
//    }
//
//    // ── PRIVATE HELPERS ───────────────────────────────────────────
//
//    // Called inside @Transactional methods — session is always open here
//    private SectionLockStatusDto buildLockStatus(Long employeeId, ProfileSection section) {
//        SectionLockStatusDto dto = new SectionLockStatusDto();
//        dto.setSection(section);
//
//        boolean filled = isSectionFilled(employeeId, section);
//        dto.setFilled(filled);
//
//        if (!filled) {
//            dto.setLocked(false);
//            dto.setCanRequest(false);
//            return dto;
//        }
//
//        Optional<EditPermissionRequest> activeApproval =
//                permissionRepo.findActiveApproval(employeeId, section);
//
//        if (activeApproval.isPresent()) {
//            dto.setLocked(false);
//            dto.setCanRequest(false);
//            dto.setPendingStatus(PermissionStatus.APPROVED);
//            dto.setExpiresAt(activeApproval.get().getExpiresAt());
//            return dto;
//        }
//
//        boolean hasPending = permissionRepo.existsByEmployee_IdAndSectionAndStatus(
//                employeeId, section, PermissionStatus.PENDING);
//
//        dto.setLocked(true);
//        dto.setCanRequest(!hasPending);
//        dto.setPendingStatus(hasPending ? PermissionStatus.PENDING : null);
//        return dto;
//    }
//
//    private boolean isSectionFilled(Long employeeId, ProfileSection section) {
//        return switch (section) {
//            case PERSONAL   -> profileService.isPersonalDetailsFilled(employeeId);
//            case EDUCATION  -> profileService.isEducationFilled(employeeId);
//            case CAREER     -> profileService.isCareerFilled(employeeId);
//            case EMERGENCY  -> profileService.isEmergencyFilled(employeeId);
//            case LEAVE      -> profileService.isLeaveFilled(employeeId);
//            case STATUS     -> profileService.isStatusFilled(employeeId);
//        };
//    }
//
//    // Session is guaranteed open — called only inside @Transactional methods
//    private EditPermissionResponseDto toDto(EditPermissionRequest r) {
//        EditPermissionResponseDto dto = new EditPermissionResponseDto();
//        dto.setId(r.getId());
//        dto.setEmployeeId(r.getEmployee().getId());
//        dto.setEmployeeName(r.getEmployee().getFullName()); // ✅ safe — session open
//        dto.setSection(r.getSection());
//        dto.setStatus(r.getStatus());
//        dto.setReason(r.getReason());
//        dto.setHrNote(r.getHrNote());
//        dto.setReviewedBy(r.getReviewedBy());
//        dto.setRequestedAt(r.getRequestedAt());
//        dto.setReviewedAt(r.getReviewedAt());
//        dto.setExpiresAt(r.getExpiresAt());
//        dto.setExpired(r.isExpired());
//        return dto;
//    }
//}