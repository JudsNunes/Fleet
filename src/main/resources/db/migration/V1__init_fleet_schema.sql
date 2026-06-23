-- ==============================================================
-- Fleet Management System - Initial Schema Migration
-- Version: 1.0
-- Date: 2026-06-20
-- ==============================================================

SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;
SET sql_mode = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- ==============================================================
-- Table: vehicle
-- ==============================================================
CREATE TABLE IF NOT EXISTS vehicle (
    id CHAR(36) NOT NULL COMMENT 'Primary Key - UUID',
    plate VARCHAR(10) NOT NULL COMMENT 'Vehicle registration plate - Mercosul or old BR format',
    model VARCHAR(100) NOT NULL COMMENT 'Vehicle model name',
    brand VARCHAR(100) NOT NULL COMMENT 'Vehicle brand/manufacturer',
    year INT NOT NULL COMMENT 'Vehicle production year',
    color VARCHAR(50) COMMENT 'Vehicle color',
    mileage DOUBLE DEFAULT 0.0 COMMENT 'Current mileage in km',
    status VARCHAR(50) DEFAULT 'ACTIVE' NOT NULL COMMENT 'Vehicle status (ACTIVE, INACTIVE, MAINTENANCE, DECOMMISSIONED)',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation timestamp (UTC)',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record last update timestamp (UTC)',
    deleted_at TIMESTAMP NULL COMMENT 'Soft-delete timestamp - NULL means active record',

    PRIMARY KEY (id),
    UNIQUE KEY uk_plate (plate),
    KEY idx_status (status),
    KEY idx_deleted_at (deleted_at),
    KEY idx_created_at (created_at),
    CONSTRAINT chk_year CHECK (year >= 1900 AND year <= YEAR(CURDATE()) + 1),
    CONSTRAINT chk_mileage CHECK (mileage >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==============================================================
-- Table: maintenance (renamed from manutention)
-- ==============================================================
CREATE TABLE IF NOT EXISTS maintenance (
    id CHAR(36) NOT NULL COMMENT 'Primary Key - UUID',
    vehicle_id CHAR(36) NOT NULL COMMENT 'Reference to vehicle table',
    maintenance_date DATE NOT NULL COMMENT 'Date when maintenance was performed',
    description TEXT NOT NULL COMMENT 'Detailed description of maintenance work',
    type VARCHAR(50) DEFAULT 'MAINTENANCE' NOT NULL COMMENT 'Maintenance type',
    cost DOUBLE NOT NULL COMMENT 'Maintenance cost in currency',
    mileage DOUBLE NOT NULL COMMENT 'Vehicle odometer reading at maintenance',
    next_mileage DOUBLE NOT NULL COMMENT 'Recommended next maintenance mileage',
    status VARCHAR(50) DEFAULT 'PENDING' NOT NULL COMMENT 'Status (PENDING, IN_PROGRESS, COMPLETED, OVERDUE)',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation timestamp (UTC)',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record last update timestamp (UTC)',
    deleted_at TIMESTAMP NULL COMMENT 'Soft-delete timestamp - NULL means active record',

    PRIMARY KEY (id),
    KEY idx_vehicle_id (vehicle_id),
    KEY idx_maintenance_date (maintenance_date),
    KEY idx_status (status),
    KEY idx_vehicle_id_status (vehicle_id, status),
    KEY idx_created_at (created_at),
    KEY idx_deleted_at (deleted_at),
    CONSTRAINT chk_cost CHECK (cost > 0),
    CONSTRAINT chk_mileage_positive CHECK (mileage >= 0),
    CONSTRAINT chk_next_mileage CHECK (next_mileage > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==============================================================
-- Indexes for Performance
-- ==============================================================
CREATE INDEX idx_vehicle_status_not_deleted ON vehicle(status, deleted_at);
CREATE INDEX idx_maintenance_vehicle_date ON maintenance(vehicle_id, maintenance_date DESC);
CREATE INDEX idx_maintenance_pending ON maintenance(status, deleted_at) WHERE status = 'PENDING';
