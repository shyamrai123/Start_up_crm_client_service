//package com.example.Start_up_crm_client_service.controller;
//
//import com.example.Start_up_crm_client_service.dto.ClientHrResponse;
//import com.example.Start_up_crm_client_service.dto.EditPermissionDtos.*;
//import com.example.Start_up_crm_client_service.dto.HrEmployeeDtos.PatchProfileRequest;
//import com.example.Start_up_crm_client_service.entity.Employee;
//import com.example.Start_up_crm_client_service.entity.ProfileSection;
//import com.example.Start_up_crm_client_service.repository.EmployeeRepository;
//import com.example.Start_up_crm_client_service.service.ClientHrService;
//import com.example.Start_up_crm_client_service.service.EditPermissionService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * Permission flow:
// *
// * EMPLOYEE (no login — identified by employeeId in path):
// *   POST   /api/v1/permissions/employee/{employeeId}/request          → request permission
// *   GET    /api/v1/permissions/employee/{employeeId}/sections          → see section lock status
// *   GET    /api/v1/permissions/employee/{employeeId}/requests          → see my requests
// *   PATCH  /api/v1/permissions/employee/{employeeId}/profile           → edit profile (if permitted)
// *
// * HR (authenticated via JWT):
// *   GET    /api/v1/permissions/hr/pending                              → pending requests
// *   GET    /api/v1/permissions/hr/all                                  → all requests
// *   PUT    /api/v1/permissions/hr/{requestId}/review                   → approve / reject
// *   GET    /api/v1/permissions/hr/employee/{employeeId}/sections       → view employee sections
// *
// * NOTE: Employee has NO JWT. Endpoints are secured differently:
// *   - HR endpoints: @PreAuthorize("hasAuthority('ROLE_ORG')")
// *   - Employee endpoints: open or secured by a shared secret / internal call
// *     (adjust security config to permit /api/v1/permissions/employee/**)
// */
//@RestController
//@RequestMapping("/api/v1/permissions")
//@RequiredArgsConstructor
//public class EditPermissionController {
//
//    private final EditPermissionService permissionService;
////    private final EmployeeProfileServiceImpl profileService;
//    private final EmployeeRepository employeeRepository;
//    private final ClientHrService clientHrService;
//
//    // ══════════════════════════════════════════════════════════════
//    // EMPLOYEE ENDPOINTS  (no JWT — employee identified by path ID)
//    // ══════════════════════════════════════════════════════════════
//
//    /**
//     * Employee requests permission to edit a section.
//     * POST /api/v1/permissions/employee/{employeeId}/request
//     */
//    @PostMapping("/employee/{employeeId}/request")
//    public ResponseEntity<EditPermissionResponseDto> requestPermission(
//            @PathVariable Long employeeId,
//            @Valid @RequestBody RequestEditPermissionDto dto) {
//
//        return ResponseEntity.ok(permissionService.requestPermission(employeeId, dto));
//    }
//
//    /**
//     * Employee views lock/unlock status of all their sections.
//     * GET /api/v1/permissions/employee/{employeeId}/sections
//     */
//    @GetMapping("/employee/{employeeId}/sections")
//    public ResponseEntity<List<SectionLockStatusDto>> getMySectionStatus(
//            @PathVariable Long employeeId) {
//
//        return ResponseEntity.ok(permissionService.getSectionLockStatus(employeeId));
//    }
//
//    /**
//     * Employee views their own permission request history.
//     * GET /api/v1/permissions/employee/{employeeId}/requests
//     */
//    @GetMapping("/employee/{employeeId}/requests")
//    public ResponseEntity<List<EditPermissionResponseDto>> getMyRequests(
//            @PathVariable Long employeeId) {
//
//        return ResponseEntity.ok(permissionService.getMyRequests(employeeId));
//    }
//
//    /**
//     * Employee edits their own profile — only permitted sections are applied.
//     * PATCH /api/v1/permissions/employee/{employeeId}/profile
//     *
//     * The service checks permission per section before applying changes.
//     */
//    @PatchMapping("/employee/{employeeId}/profile")
//    public ResponseEntity<?> patchOwnProfile(
//            @PathVariable Long employeeId,
//            @RequestBody PatchProfileRequest req) {
//
//        // Validate section permissions before patching
//        validateAndPatch(employeeId, req);
//        return ResponseEntity.ok(profileService.toProfileResponse(
//                employeeRepository.findById(employeeId)
//                        .orElseThrow(() -> new RuntimeException("Employee not found"))));
//    }
//
//    // ══════════════════════════════════════════════════════════════
//    // HR ENDPOINTS  (JWT required, ROLE_ORG)
//    // ══════════════════════════════════════════════════════════════
//
//    /**
//     * HR views all PENDING permission requests for their company.
//     * GET /api/v1/permissions/hr/pending
//     */
//    @GetMapping("/hr/pending")
//    @PreAuthorize("hasAuthority('ROLE_ORG')")
//    public ResponseEntity<List<EditPermissionResponseDto>> getPendingRequests(
//            Authentication authentication) {
//
//        String clientCode = getClientCode(authentication.getName());
//        return ResponseEntity.ok(permissionService.getPendingRequests(clientCode));
//    }
//
//    /**
//     * HR views all permission requests for their company.
//     * GET /api/v1/permissions/hr/all
//     */
//    @GetMapping("/hr/all")
//    @PreAuthorize("hasAuthority('ROLE_ORG')")
//    public ResponseEntity<List<EditPermissionResponseDto>> getAllRequests(
//            Authentication authentication) {
//
//        String clientCode = getClientCode(authentication.getName());
//        return ResponseEntity.ok(permissionService.getAllRequests(clientCode));
//    }
//
//    /**
//     * HR approves or rejects a permission request.
//     * PUT /api/v1/permissions/hr/{requestId}/review
//     */
//    @PutMapping("/hr/{requestId}/review")
//    @PreAuthorize("hasAuthority('ROLE_ORG')")
//    public ResponseEntity<EditPermissionResponseDto> reviewRequest(
//            @PathVariable Long requestId,
//            @Valid @RequestBody ReviewPermissionDto dto,
//            Authentication authentication) {
//
//        String hrEmail = authentication.getName();
//        String clientCode = getClientCode(hrEmail);
//        return ResponseEntity.ok(
//                permissionService.reviewRequest(requestId, hrEmail, clientCode, dto));
//    }
//
//    /**
//     * HR views section lock status for a specific employee.
//     * GET /api/v1/permissions/hr/employee/{employeeId}/sections
//     */
//    @GetMapping("/hr/employee/{employeeId}/sections")
//    @PreAuthorize("hasAuthority('ROLE_ORG')")
//    public ResponseEntity<List<SectionLockStatusDto>> getEmployeeSections(
//            @PathVariable Long employeeId,
//            Authentication authentication) {
//
//        String clientCode = getClientCode(authentication.getName());
//
//        Employee emp = employeeRepository.findById(employeeId)
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//
//        if (!emp.getClientCode().equals(clientCode)) {
//            return ResponseEntity.status(403).build();
//        }
//
//        return ResponseEntity.ok(permissionService.getSectionLockStatus(employeeId));
//    }
//
//    // ─────────────────────────────────────────────────────────────
//    // PRIVATE HELPERS
//    // ─────────────────────────────────────────────────────────────
//
//    @SuppressWarnings("unchecked")
//    private String getClientCode(String hrEmail) {
//        ClientHrResponse<Map<String, String>> response = clientHrService.getHrByEmail(hrEmail);
//        if (!response.isSuccess() || response.getData() == null) {
//            throw new RuntimeException("HR not found in AuthService for: " + hrEmail);
//        }
//        return ((Map<String, String>) response.getData()).get("clientCode");
//    }
//
//    /**
//     * For each section present in the patch request, verify the employee
//     * has an active (approved, non-expired) permission before applying.
//     * After applying, mark the permission as USED.
//     */
//    private void validateAndPatch(Long employeeId, PatchProfileRequest req) {
//
//        if (req.getPhone() != null || req.getMaritalStatus() != null) {
//            assertPermission(employeeId, ProfileSection.PERSONAL);
//        }
//        if (req.getAddress() != null) {
//            assertPermission(employeeId, ProfileSection.PERSONAL); // ADDRESS maps to PERSONAL section
//        }
//        if (req.getEmergencyContacts() != null) {
//            assertPermission(employeeId, ProfileSection.EMERGENCY);
//        }
//        if (req.getCareerDetails() != null) {
//            assertPermission(employeeId, ProfileSection.CAREER);
//        }
//        if (req.getEducationalDetails() != null) {
//            assertPermission(employeeId, ProfileSection.EDUCATION);
//        }
//
//        // Apply patch
//        profileService.patchProfile(employeeId, req);
//
//        // Mark permissions used
//        if (req.getPhone() != null || req.getMaritalStatus() != null || req.getAddress() != null) {
//            permissionService.markPermissionUsed(employeeId, ProfileSection.PERSONAL);
//        }
//        if (req.getEmergencyContacts() != null) {
//            permissionService.markPermissionUsed(employeeId, ProfileSection.EMERGENCY);
//        }
//        if (req.getCareerDetails() != null) {
//            permissionService.markPermissionUsed(employeeId, ProfileSection.CAREER);
//        }
//        if (req.getEducationalDetails() != null) {
//            permissionService.markPermissionUsed(employeeId, ProfileSection.EDUCATION);
//        }
//    }
//
//    private void assertPermission(Long employeeId, ProfileSection section) {
//        if (!permissionService.hasEditPermission(employeeId, section)) {
//            throw new RuntimeException(
//                    "No active permission for section: " + section
//                            + ". Please request permission from HR.");
//        }
//    }
//}