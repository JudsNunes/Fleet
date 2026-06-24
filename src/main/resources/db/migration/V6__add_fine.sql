-- ==============================================================
-- Fleet Management System - Fine Migration
-- Version: 6.0
-- Date: 2026-06-22
-- Description: Create fine table for traffic fines
-- ==============================================================

SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;
SET sql_mode = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- Create fine table
CREATE TABLE IF NOT EXISTS fine (
    id CHAR(36) NOT NULL COMMENT 'Primary Key - UUID',
    vehicle_id CHAR(36) NOT NULL COMMENT 'Reference to vehicle table',
    driver_cpf VARCHAR(14) NOT NULL COMMENT 'Driver CPF (XXX.XXX.XXX-XX)',
    description TEXT NOT NULL COMMENT 'Fine description',
    amount DOUBLE NOT NULL COMMENT 'Fine amount',
    infraction_date DATE NOT NULL COMMENT 'Date of infraction',
    points INT COMMENT 'Points for the infraction',
    status VARCHAR(50) DEFAULT 'PENDING' NOT NULL COMMENT 'Status (PENDING, PAID, DISPUTED)',
    cost_center_id VARCHAR(100) COMMENT 'Cost center ID',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation timestamp (UTC)',

    PRIMARY KEY (id),
    KEY idx_fine_vehicle_id (vehicle_id),
    KEY idx_fine_driver_cpf (driver_cpf),
    KEY idx_fine_status (status),
    KEY idx_fine_infraction_date (infraction_date),
    KEY idx_fine_created_at (created_at),
    CONSTRAINT fk_fine_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicle(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
