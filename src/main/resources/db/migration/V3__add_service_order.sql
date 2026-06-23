-- ==============================================================
-- Fleet Management System - Service Order Migration
-- Version: 3.0
-- Date: 2026-06-22
-- Description: Create service_order table with warranty support
-- ==============================================================

SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;
SET sql_mode = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- Create service_order table
CREATE TABLE IF NOT EXISTS service_order (
    id CHAR(36) NOT NULL COMMENT 'Primary Key - UUID',
    vehicle_id CHAR(36) NOT NULL COMMENT 'Reference to vehicle table',
    description VARCHAR(500) NOT NULL COMMENT 'Service order description',
    status VARCHAR(50) DEFAULT 'PENDING' NOT NULL COMMENT 'Status (PENDING, APPROVED, REJECTED, COMPLETED)',
    approved_by VARCHAR(100) COMMENT 'User who approved the service order',
    approved_at TIMESTAMP NULL COMMENT 'Approval timestamp',
    warranty_expiry_date TIMESTAMP NULL COMMENT 'Warranty expiration date',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation timestamp (UTC)',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record last update timestamp (UTC)',
    deleted_at TIMESTAMP NULL COMMENT 'Soft-delete timestamp - NULL means active record',

    PRIMARY KEY (id),
    KEY idx_so_vehicle_id (vehicle_id),
    KEY idx_so_status (status),
    KEY idx_so_created_at (created_at),
    KEY idx_so_deleted_at (deleted_at),
    KEY idx_so_warranty_expiry (warranty_expiry_date),
    CONSTRAINT fk_so_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicle(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Add service_order_id to maintenance table
ALTER TABLE maintenance
    ADD COLUMN service_order_id CHAR(36) NULL COMMENT 'Reference to service_order table' AFTER vehicle_id,
    ADD COLUMN cost_center_id VARCHAR(100) NULL COMMENT 'Cost center ID' AFTER service_order_id,
    ADD COLUMN project_id VARCHAR(100) NULL COMMENT 'Project ID' AFTER cost_center_id;

ALTER TABLE maintenance
    ADD KEY idx_maintenance_service_order (service_order_id),
    ADD KEY idx_maintenance_cost_center (cost_center_id),
    ADD KEY idx_maintenance_project (project_id),
    ADD CONSTRAINT fk_maintenance_service_order FOREIGN KEY (service_order_id) REFERENCES service_order(id);
