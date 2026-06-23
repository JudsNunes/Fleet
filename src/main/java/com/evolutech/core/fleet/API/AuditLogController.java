package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.mapper.ApiMapper;
import com.evolutech.core.fleet.service.AuditLogService;
import com.evolutech.fleet.api.AuditLogsApi;
import com.evolutech.fleet.api.model.AuditLogPageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuditLogController implements AuditLogsApi {

    private final AuditLogService auditLogService;
    private final ApiMapper apiMapper;

    @Override
    public ResponseEntity<AuditLogPageDTO> getAllAuditLogs(Integer page, Integer size) {
        log.info("Fetching all audit logs");
        var pageable = PageRequest.of(page, size);
        var result = auditLogService.findAll(pageable);
        return ResponseEntity.ok(apiMapper.toAuditLogPageApi(result));
    }

    @Override
    public ResponseEntity<AuditLogPageDTO> getAuditLogsByEntity(String entityType, String entityId, Integer page, Integer size) {
        log.info("Fetching audit logs for {} with id: {}", entityType, entityId);
        var pageable = PageRequest.of(page, size);
        var result = auditLogService.findByEntityTypeAndEntityId(entityType, entityId, pageable);
        return ResponseEntity.ok(apiMapper.toAuditLogPageApi(result));
    }

    @Override
    public ResponseEntity<AuditLogPageDTO> getAuditLogsByEntityType(String entityType, Integer page, Integer size) {
        log.info("Fetching audit logs for entity type: {}", entityType);
        var pageable = PageRequest.of(page, size);
        var result = auditLogService.findByEntityType(entityType, pageable);
        return ResponseEntity.ok(apiMapper.toAuditLogPageApi(result));
    }

    @Override
    public ResponseEntity<AuditLogPageDTO> getAuditLogsByUser(String userId, Integer page, Integer size) {
        log.info("Fetching audit logs for user: {}", userId);
        var pageable = PageRequest.of(page, size);
        var result = auditLogService.findByUserId(userId, pageable);
        return ResponseEntity.ok(apiMapper.toAuditLogPageApi(result));
    }
}
