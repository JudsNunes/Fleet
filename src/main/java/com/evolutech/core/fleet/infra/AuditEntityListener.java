package com.evolutech.core.fleet.infra;

import com.evolutech.core.fleet.model.entity.AuditLogEntity;
import com.evolutech.core.fleet.model.utils.enums.AuditAction;
import com.evolutech.core.fleet.repository.AuditLogRepository;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditEntityListener {

    private final AuditLogRepository auditLogRepository;

    @PostPersist
    public void onCreated(Object entity) {
        if (isAuditable(entity)) {
            logAudit(entity, AuditAction.CREATE, null, null, null);
        }
    }

    @PostUpdate
    public void onUpdated(Object entity) {
        if (isAuditable(entity)) {
            logAudit(entity, AuditAction.UPDATE, null, null, null);
        }
    }

    @PreRemove
    public void onDeleted(Object entity) {
        if (isAuditable(entity)) {
            logAudit(entity, AuditAction.DELETE, null, null, null);
        }
    }

    private boolean isAuditable(Object entity) {
        String className = entity.getClass().getSimpleName();
        return className.equals("VehicleEntity") || className.equals("MaintenanceEntity");
    }

    private void logAudit(Object entity, AuditAction action, String fieldName, String oldValue, String newValue) {
        try {
            AuditLogEntity auditLog = new AuditLogEntity();
            auditLog.setEntityType(entity.getClass().getSimpleName());
            auditLog.setEntityId(getEntityId(entity));
            auditLog.setAction(action);
            auditLog.setFieldName(fieldName);
            auditLog.setOldValue(oldValue);
            auditLog.setNewValue(newValue);
            auditLog.setTimestamp(LocalDateTime.now());

            auditLogRepository.save(auditLog);
        } catch (Exception e) {
            log.error("Failed to create audit log for entity: {}", entity.getClass().getSimpleName(), e);
        }
    }

    private String getEntityId(Object entity) {
        try {
            var method = entity.getClass().getMethod("getId");
            Object id = method.invoke(entity);
            return id != null ? id.toString() : null;
        } catch (Exception e) {
            return null;
        }
    }
}
