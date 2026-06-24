-- ==============================================================
-- Fleet Management System - Vehicle Assignment Table Migration
-- Version: 9.0
-- Date: 2026-06-23
-- Description: Create vehicle_assignment table for driver-vehicle linking
-- ==============================================================

SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;
SET sql_mode = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE TABLE IF NOT EXISTS vehicle_assignment (
    id CHAR(36) NOT NULL COMMENT 'Primary Key - UUID',
    vehicle_id CHAR(36) NOT NULL COMMENT 'Reference to vehicle table',
    driver_id CHAR(36) NOT NULL COMMENT 'Reference to driver table',
    start_date DATE NOT NULL COMMENT 'Assignment start date',
    end_date DATE NULL COMMENT 'Assignment end date (NULL = active)',
    status VARCHAR(50) DEFAULT 'ACTIVE' NOT NULL COMMENT 'Status (ACTIVE, ENDED, CANCELLED)',
    assigned_by VARCHAR(100) NOT NULL COMMENT 'Who made the assignment',
    notes VARCHAR(500) COMMENT 'Notes about the assignment',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation timestamp (UTC)',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record last update timestamp (UTC)',
    deleted_at TIMESTAMP NULL COMMENT 'Soft-delete timestamp - NULL means active record',

    PRIMARY KEY (id),
    KEY idx_va_vehicle_id (vehicle_id),
    KEY idx_va_driver_id (driver_id),
    KEY idx_va_status (status),
    KEY idx_va_start_date (start_date),
    KEY idx_va_deleted_at (deleted_at),
    KEY idx_va_created_at (created_at),
    CONSTRAINT fk_va_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicle(id),
    CONSTRAINT fk_va_driver FOREIGN KEY (driver_id) REFERENCES driver(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
