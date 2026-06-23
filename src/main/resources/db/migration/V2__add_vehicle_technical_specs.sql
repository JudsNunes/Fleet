-- ==============================================================
-- Fleet Management System - Vehicle Technical Specs Migration
-- Version: 2.0
-- Date: 2026-06-22
-- Description: Add technical specification fields to vehicle table
-- ==============================================================

SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;
SET sql_mode = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- Add new columns to vehicle table
ALTER TABLE vehicle
    ADD COLUMN chassis VARCHAR(17) NOT NULL COMMENT 'Vehicle chassis number (VIN)' AFTER mileage,
    ADD COLUMN renavam VARCHAR(11) NOT NULL COMMENT 'National Vehicle Registry (RENAVAM)' AFTER chassis,
    ADD COLUMN fuel_type VARCHAR(50) NOT NULL COMMENT 'Fuel type (GASOLINE, DIESEL, ETHANOL, FLEX, ELECTRIC, HYBRID)' AFTER renavam,
    ADD COLUMN cargo_capacity_kg DOUBLE NULL COMMENT 'Cargo capacity in kilograms' AFTER fuel_type,
    ADD COLUMN passenger_capacity INT NULL COMMENT 'Passenger capacity' AFTER cargo_capacity_kg,
    ADD COLUMN engine_type VARCHAR(50) NULL COMMENT 'Engine type (e.g., 1.0, 1.8, 2.0, EV)' AFTER passenger_capacity;

-- Add unique constraints
ALTER TABLE vehicle
    ADD UNIQUE KEY uk_chassis (chassis),
    ADD UNIQUE KEY uk_renavam (renavam);

-- Add index for fuel_type
ALTER TABLE vehicle
    ADD INDEX idx_fuel_type (fuel_type);

-- Update existing vehicles with default values (for migration safety)
-- Note: In production, these should be updated manually or via data migration script
UPDATE vehicle SET
    chassis = CONCAT('UNKNOWN', LPAD(RIGHT(id, 8), 8, '0')),
    renavam = CONCAT('0000000', LPAD(RIGHT(id, 4), 4, '0')),
    fuel_type = 'GASOLINE'
WHERE chassis IS NULL OR renavam IS NULL OR fuel_type IS NULL;
