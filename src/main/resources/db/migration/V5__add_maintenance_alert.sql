-- ==============================================================
-- Fleet Management System - Maintenance Alert Migration
-- Version: 5.0
-- Date: 2026-06-22
-- Description: Create maintenance_alert table for preventive alerts
-- ==============================================================

SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;
SET sql_mode = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- Create maintenance_alert table
CREATE TABLE IF NOT EXISTS maintenance_alert (
    id CHAR(36) NOT NULL COMMENT 'Primary Key - UUID',
    vehicle_id CHAR(36) NOT NULL COMMENT 'Reference to vehicle table',
    maintenance_id CHAR(36) NOT NULL COMMENT 'Reference to maintenance table',
    message VARCHAR(200) NOT NULL COMMENT 'Alert message',
    is_active BOOLEAN NOT NULL DEFAULT TRUE COMMENT 'Is alert active',
    alert_type VARCHAR(50) NOT NULL COMMENT 'Alert type (MILEAGE_THRESHOLD, DATE_THRESHOLD)',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation timestamp (UTC)',
    resolved_at TIMESTAMP NULL COMMENT 'When the alert was resolved',

    PRIMARY KEY (id),
    KEY idx_alert_vehicle_id (vehicle_id),
    KEY idx_alert_maintenance_id (maintenance_id),
    KEY idx_alert_active (is_active),
    CONSTRAINT fk_alert_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicle(id),
    CONSTRAINT fk_alert_maintenance FOREIGN KEY (maintenance_id) REFERENCES maintenance(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
