-- ==============================================================
-- Fleet Management System - Audit Log Migration
-- Version: 4.0
-- Date: 2026-06-22
-- Description: Create audit_log table for tracking changes
-- ==============================================================

SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;
SET sql_mode = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- Create audit_log table
CREATE TABLE IF NOT EXISTS audit_log (
    id CHAR(36) NOT NULL COMMENT 'Primary Key - UUID',
    entity_type VARCHAR(50) NOT NULL COMMENT 'Entity class name (VehicleEntity, MaintenanceEntity)',
    entity_id VARCHAR(36) NOT NULL COMMENT 'Entity ID',
    action VARCHAR(50) NOT NULL COMMENT 'Action (CREATE, UPDATE, DELETE, STATUS_CHANGE)',
    field_name VARCHAR(100) COMMENT 'Field that was changed',
    old_value TEXT COMMENT 'Previous value',
    new_value TEXT COMMENT 'New value',
    user_id VARCHAR(100) COMMENT 'User who performed the action',
    timestamp TIMESTAMP NOT NULL COMMENT 'When the action occurred',
    ip_address VARCHAR(100) COMMENT 'IP address of the user',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation timestamp (UTC)',

    PRIMARY KEY (id),
    KEY idx_audit_entity_type (entity_type),
    KEY idx_audit_entity_id (entity_id),
    KEY idx_audit_action (action),
    KEY idx_audit_timestamp (timestamp),
    KEY idx_audit_user_id (user_id),
    KEY idx_audit_entity_composite (entity_type, entity_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
