//package com.example.Start_up_crm_client_service.repository;
//
//import com.example.Start_up_crm_client_service.entity.EditPermissionRequest;
//import com.example.Start_up_crm_client_service.entity.PermissionStatus;
//import com.example.Start_up_crm_client_service.entity.ProfileSection;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface EditPermissionRequestRepository
//        extends JpaRepository<EditPermissionRequest, Long> {
//
//    // ── Employee queries ──────────────────────────────────────────
//
//    @Query("SELECT r FROM EditPermissionRequest r JOIN FETCH r.employee " +
//            "WHERE r.employee.id = :employeeId ORDER BY r.requestedAt DESC")
//    List<EditPermissionRequest> findByEmployee_IdOrderByRequestedAtDesc(
//            @Param("employeeId") Long employeeId);
//
//    boolean existsByEmployee_IdAndSectionAndStatus(
//            Long employeeId, ProfileSection section, PermissionStatus status);
////
//    // ── HR queries ────────────────────────────────────────────────
//
//    @Query("SELECT r FROM EditPermissionRequest r JOIN FETCH r.employee " +
//            "WHERE r.employee.clientCode = :clientCode AND r.status = :status " +
//            "ORDER BY r.requestedAt DESC")
//    List<EditPermissionRequest> findByEmployee_ClientCodeAndStatusOrderByRequestedAtDesc(
//            @Param("clientCode") String clientCode,
//            @Param("status") PermissionStatus status);
//
//    @Query("SELECT r FROM EditPermissionRequest r JOIN FETCH r.employee " +
//            "WHERE r.employee.clientCode = :clientCode ORDER BY r.requestedAt DESC")
//    List<EditPermissionRequest> findByEmployee_ClientCodeOrderByRequestedAtDesc(
//            @Param("clientCode") String clientCode);
//
//    // ── Active approval check ─────────────────────────────────────
//
//    @Query("SELECT r FROM EditPermissionRequest r JOIN FETCH r.employee " +
//            "WHERE r.employee.id = :employeeId " +
//            "AND r.section = :section " +
//            "AND r.status = 'APPROVED' " +
//            "AND (r.expiresAt IS NULL OR r.expiresAt > CURRENT_TIMESTAMP)")
//    Optional<EditPermissionRequest> findActiveApproval(
//            @Param("employeeId") Long employeeId,
//            @Param("section") ProfileSection section);
//}