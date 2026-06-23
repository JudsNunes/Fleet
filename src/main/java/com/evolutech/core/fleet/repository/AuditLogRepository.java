package com.evolutech.core.fleet.repository;

import com.evolutech.core.fleet.model.entity.AuditLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLogEntity, String> {

    @Query("SELECT a FROM AuditLogEntity a WHERE a.entityType = :entityType AND a.entityId = :entityId ORDER BY a.timestamp DESC")
    Page<AuditLogEntity> findByEntityTypeAndEntityId(@Param("entityType") String entityType, @Param("entityId") String entityId, Pageable pageable);

    @Query("SELECT a FROM AuditLogEntity a WHERE a.entityType = :entityType ORDER BY a.timestamp DESC")
    Page<AuditLogEntity> findByEntityType(@Param("entityType") String entityType, Pageable pageable);

    @Query("SELECT a FROM AuditLogEntity a WHERE a.userId = :userId ORDER BY a.timestamp DESC")
    Page<AuditLogEntity> findByUserId(@Param("userId") String userId, Pageable pageable);

    @Query("SELECT a FROM AuditLogEntity a ORDER BY a.timestamp DESC")
    Page<AuditLogEntity> findAllOrderByTimestampDesc(Pageable pageable);
}
