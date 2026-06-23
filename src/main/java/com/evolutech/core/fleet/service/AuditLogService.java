package com.evolutech.core.fleet.service;

import com.evolutech.core.fleet.model.dto.response.AuditLogResponseDTO;
import com.evolutech.core.fleet.model.entity.AuditLogEntity;
import com.evolutech.core.fleet.model.utils.enums.AuditAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditLogService {

    void log(String entityType, String entityId, AuditAction action, String fieldName, String oldValue, String newValue, String userId, String ipAddress);

    Page<AuditLogResponseDTO> findByEntityTypeAndEntityId(String entityType, String entityId, Pageable pageable);

    Page<AuditLogResponseDTO> findByEntityType(String entityType, Pageable pageable);

    Page<AuditLogResponseDTO> findByUserId(String userId, Pageable pageable);

    Page<AuditLogResponseDTO> findAll(Pageable pageable);
}
