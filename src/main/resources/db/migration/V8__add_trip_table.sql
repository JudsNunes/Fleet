-- ==============================================================
-- Fleet Management System - Trip Table Migration
-- Version: 8.0
-- Date: 2026-06-23
-- Description: Create trip table for trip management
-- ==============================================================

SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;
SET sql_mode = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE TABLE IF NOT EXISTS trip (
    id CHAR(36) NOT NULL COMMENT 'Primary Key - UUID',
    vehicle_id CHAR(36) NOT NULL COMMENT 'Reference to vehicle table',
    driver_id CHAR(36) NOT NULL COMMENT 'Reference to driver table',
    description VARCHAR(200) NOT NULL COMMENT 'Trip description',
    origin VARCHAR(150) NOT NULL COMMENT 'Origin location',
    destination VARCHAR(150) NOT NULL COMMENT 'Destination location',
    planned_distance_km DOUBLE COMMENT 'Planned distance in km',
    actual_distance_km DOUBLE COMMENT 'Actual distance in km',
    departure_date TIMESTAMP NOT NULL COMMENT 'Departure date and time',
    arrival_date TIMESTAMP NULL COMMENT 'Arrival date and time',
    start_mileage DOUBLE COMMENT 'Odometer at departure',
    end_mileage DOUBLE COMMENT 'Odometer at arrival',
    status VARCHAR(50) DEFAULT 'PLANNED' NOT NULL COMMENT 'Trip status (PLANNED, IN_PROGRESS, COMPLETED, CANCELLED)',
    route_deviation BOOLEAN DEFAULT FALSE COMMENT 'Route deviation flag',
    deviation_justification VARCHAR(500) COMMENT 'Justification for route deviation',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation timestamp (UTC)',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record last update timestamp (UTC)',
    deleted_at TIMESTAMP NULL COMMENT 'Soft-delete timestamp - NULL means active record',

    PRIMARY KEY (id),
    KEY idx_trip_vehicle_id (vehicle_id),
    KEY idx_trip_driver_id (driver_id),
    KEY idx_trip_status (status),
    KEY idx_trip_departure_date (departure_date),
    KEY idx_trip_deleted_at (deleted_at),
    KEY idx_trip_created_at (created_at),
    CONSTRAINT fk_trip_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicle(id),
    CONSTRAINT fk_trip_driver FOREIGN KEY (driver_id) REFERENCES driver(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
