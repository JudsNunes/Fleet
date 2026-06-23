-- ==============================================================
-- Fleet Management System - Initial Schema Migration
-- Version: 1.0
-- Date: 2026-06-20
-- ==============================================================

-- Set encoding and strict SQL mode
SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;
SET sql_mode = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- ==============================================================
-- Enum Definitions (as reference for application layer)
-- ==============================================================
-- VehicleStatus: ACTIVE, INACTIVE, MAINTENANCE, DECOMMISSIONED
-- ManutentionDoneStatus: PENDING, IN_PROGRESS, COMPLETED
-- TypeCost: MANUTENTION, FUEL, TOLL, PARKING, OTHER

-- ==============================================================
-- Table: vehicle (TBG_VEHICLE)
-- Purpose: Store vehicle information with audit and soft-delete
-- ==============================================================
CREATE TABLE IF NOT EXISTS vehicle (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary Key - Auto-increment BIGINT for scalability',
    plate VARCHAR(10) NOT NULL UNIQUE COMMENT 'Vehicle registration plate - Mercosul or old BR format',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Vehicles fleet master data with audit trail and soft-delete capability';

-- ==============================================================
-- Table: manutention (TBG_MANUTENTION)
-- Purpose: Store vehicle maintenance records
-- ==============================================================
CREATE TABLE IF NOT EXISTS manutention (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary Key - Auto-increment BIGINT',
    vehicle_id BIGINT NOT NULL COMMENT 'Foreign Key to vehicle table',
    manutention_date DATE NOT NULL COMMENT 'Date when maintenance was performed',
    description TEXT NOT NULL COMMENT 'Detailed description of maintenance work',
    type VARCHAR(50) DEFAULT 'MANUTENTION' NOT NULL COMMENT 'Maintenance type (MANUTENTION, FUEL, TOLL, PARKING, OTHER)',
    cost DOUBLE NOT NULL COMMENT 'Maintenance cost in currency (positive value)',
    mileage DOUBLE NOT NULL COMMENT 'Vehicle odometer reading at maintenance',
    next_mileage DOUBLE NOT NULL COMMENT 'Recommended next maintenance mileage',
    done VARCHAR(50) DEFAULT 'PENDING' NOT NULL COMMENT 'Status (PENDING, IN_PROGRESS, COMPLETED)',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation timestamp (UTC)',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record last update timestamp (UTC)',
    deleted_at TIMESTAMP NULL COMMENT 'Soft-delete timestamp - NULL means active record',
    
    PRIMARY KEY (id),
    KEY idx_vehicle_id (vehicle_id),
    KEY idx_manutention_date (manutention_date),
    KEY idx_done_status (done),
    KEY idx_vehicle_id_done (vehicle_id, done),
    KEY idx_created_at (created_at),
    KEY idx_deleted_at (deleted_at),
    CONSTRAINT fk_vehicle_id FOREIGN KEY (vehicle_id) 
        REFERENCES vehicle(id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE 
        COMMENT 'Reference to vehicle - cascade updates, restrict deletes to maintain referential integrity',
    CONSTRAINT chk_cost CHECK (cost > 0),
    CONSTRAINT chk_mileage_positive CHECK (mileage >= 0),
    CONSTRAINT chk_next_mileage CHECK (next_mileage > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Vehicle maintenance records with soft-delete and audit trail';

-- ==============================================================
-- Indexes for Performance Optimization
-- ==============================================================
-- Additional indexes for common queries
CREATE INDEX idx_vehicle_status_not_deleted ON vehicle(status, deleted_at);
CREATE INDEX idx_manutention_vehicle_date ON manutention(vehicle_id, manutention_date DESC);
CREATE INDEX idx_manutention_pending ON manutention(done, deleted_at) WHERE done = 'PENDING';

-- ==============================================================
-- Views for Active Records Only (Business Logic)
-- ==============================================================
-- These views ensure soft-deleted records are excluded by default
CREATE OR REPLACE VIEW v_vehicle AS
SELECT * FROM vehicle 
WHERE deleted_at IS NULL;

CREATE OR REPLACE VIEW v_manutention AS
SELECT * FROM manutention 
WHERE deleted_at IS NULL;

CREATE OR REPLACE VIEW v_vehicle_manutention AS
SELECT 
    v.id as vehicle_id,
    v.plate,
    v.brand,
    v.model,
    v.year,
    v.mileage as vehicle_mileage,
    v.status as vehicle_status,
    m.id as manutention_id,
    m.manutention_date,
    m.description,
    m.type,
    m.cost,
    m.mileage as manutention_mileage,
    m.next_mileage,
    m.done,
    m.created_at
FROM v_vehicle v
LEFT JOIN v_manutention m ON v.id = m.vehicle_id
ORDER BY v.id, m.manutention_date DESC;

-- ==============================================================
-- Procedures for Soft Delete Operations (Optional - Wrapper)
-- ==============================================================
DELIMITER //

CREATE PROCEDURE sp_soft_delete_vehicle(IN p_vehicle_id BIGINT)
BEGIN
    DECLARE v_exists INT;
    
    SELECT COUNT(*) INTO v_exists FROM vehicle WHERE id = p_vehicle_id AND deleted_at IS NULL;
    
    IF v_exists = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Vehicle not found or already deleted';
    ELSE
        UPDATE vehicle SET deleted_at = NOW() WHERE id = p_vehicle_id;
    END IF;
END //

CREATE PROCEDURE sp_soft_delete_manutention(IN p_manutention_id BIGINT)
BEGIN
    DECLARE v_exists INT;
    
    SELECT COUNT(*) INTO v_exists FROM manutention WHERE id = p_manutention_id AND deleted_at IS NULL;
    
    IF v_exists = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Maintenance record not found or already deleted';
    ELSE
        UPDATE manutention SET deleted_at = NOW() WHERE id = p_manutention_id;
    END IF;
END //

DELIMITER ;

-- ==============================================================
-- Audit Trail: Insert Triggers (Optional - for audit logging)
-- ==============================================================
-- Create audit tables if comprehensive change tracking is needed
CREATE TABLE IF NOT EXISTS audit_log (
    id BIGINT NOT NULL AUTO_INCREMENT,
    table_name VARCHAR(100) NOT NULL,
    operation VARCHAR(10) NOT NULL,
    record_id BIGINT NOT NULL,
    changed_by VARCHAR(255) DEFAULT 'system',
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    old_values JSON,
    new_values JSON,
    PRIMARY KEY (id),
    KEY idx_table_record (table_name, record_id),
    KEY idx_changed_at (changed_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Audit trail for all data changes';

-- ==============================================================
-- Application Comments
-- ==============================================================
-- Soft Delete Strategy:
--   - Records are marked deleted via deleted_at TIMESTAMP
--   - Views (v_vehicle, v_manutention) filter deleted records
--   - Application layer should use these views for SELECT queries
--   - Hard deletes are restricted via ON DELETE RESTRICT ForeignKey
--   
-- Audit Fields:
--   - created_at: Immutable, set on insert via DEFAULT CURRENT_TIMESTAMP
--   - updated_at: Updated on every record modification via ON UPDATE CURRENT_TIMESTAMP
--   - deleted_at: Set only once on soft-delete; never updated after
--   
-- Enum Storage:
--   - All enum fields stored as VARCHAR with allowed values
--   - Application enforces enum constraints via @Enumerated(EnumType.STRING)
--   - Enums: VehicleStatus (ACTIVE, INACTIVE, MAINTENANCE, DECOMMISSIONED)
--           ManutentionDoneStatus (PENDING, IN_PROGRESS, COMPLETED)
--           TypeCost (MANUTENTION, FUEL, TOLL, PARKING, OTHER)
--   
-- Performance:
--   - Composite indexes on frequently filtered columns (vehicle_id + done)
--   - Separate index on soft-delete marker for efficient filtering
--   - CASCADE UPDATE on FK ensures referential integrity without duplicating keys
--   
-- References:
--   - Architecture Doc: arquitetura_frota_veicular_acea20c9.plan.md
--   - Entity Mappers: VehicleMapper, ManutentionMapper (MapStruct)
--   - Service Layer: VehicleService, ManutentionService
