package com.evolutech.core.fleet.service.impl;

import com.evolutech.core.fleet.mapper.AuditLogMapper;
import com.evolutech.core.fleet.model.dto.response.AuditLogResponseDTO;
import com.evolutech.core.fleet.model.entity.AuditLogEntity;
import com.evolutech.core.fleet.model.utils.enums.AuditAction;
import com.evolutech.core.fleet.repository.AuditLogRepository;
import com.evolutech.core.fleet.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    @Override
    @Transactional
    public void log(String entityType, String entityId, AuditAction action, String fieldName, String oldValue, String newValue, String userId, String ipAddress) {
        log.info("Audit log: {} {} on {}:{} by {}", action, entityType, entityId, fieldName, userId);

        AuditLogEntity auditLog = new AuditLogEntity();
        auditLog.setEntityType(entityType);
        auditLog.setEntityId(entityId);
        auditLog.setAction(action);
        auditLog.setFieldName(fieldName);
        auditLog.setOldValue(oldValue);
        auditLog.setNewValue(newValue);
        auditLog.setUserId(userId);
        auditLog.setTimestamp(LocalDateTime.now());
        auditLog.setIpAddress(ipAddress);

        auditLogRepository.save(auditLog);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuditLogResponseDTO> findByEntityTypeAndEntityId(String entityType, String entityId, Pageable pageable) {
        log.debug("Finding audit logs for {} with id: {}", entityType, entityId);
        return auditLogRepository.findByEntityTypeAndEntityId(entityType, entityId, pageable)
                .map(auditLogMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuditLogResponseDTO> findByEntityType(String entityType, Pageable pageable) {
        log.debug("Finding audit logs for entity type: {}", entityType);
        return auditLogRepository.findByEntityType(entityType, pageable)
                .map(auditLogMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuditLogResponseDTO> findByUserId(String userId, Pageable pageable) {
        log.debug("Finding audit logs for user: {}", userId);
        return auditLogRepository.findByUserId(userId, pageable)
                .map(auditLogMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuditLogResponseDTO> findAll(Pageable pageable) {
        log.debug("Finding all audit logs");
        return auditLogRepository.findAllOrderByTimestampDesc(pageable)
                .map(auditLogMapper::toResponseDTO);
    }
}
