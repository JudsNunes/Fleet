-- ==============================================================
-- Fleet Management System - Driver Table Migration
-- Version: 7.0
-- Date: 2026-06-23
-- Description: Create driver table for motorista management
-- ==============================================================

SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;
SET sql_mode = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE TABLE IF NOT EXISTS driver (
    id CHAR(36) NOT NULL COMMENT 'Primary Key - UUID',
    name VARCHAR(150) NOT NULL COMMENT 'Full name',
    cpf VARCHAR(14) NOT NULL COMMENT 'CPF (XXX.XXX.XXX-XX)',
    cnh_number VARCHAR(11) NOT NULL COMMENT 'Driver license number',
    cnh_category VARCHAR(5) NOT NULL COMMENT 'License category (A, B, C, D, E, AB, AC, AD, AE)',
    cnh_expiry_date DATE NOT NULL COMMENT 'License expiry date',
    cnh_status VARCHAR(50) DEFAULT 'ACTIVE' NOT NULL COMMENT 'License status (ACTIVE, EXPIRED, SUSPENDED)',
    phone VARCHAR(15) COMMENT 'Phone/WhatsApp',
    email VARCHAR(100) COMMENT 'Email',
    birth_date DATE COMMENT 'Date of birth',
    address VARCHAR(200) COMMENT 'Full address',
    status VARCHAR(50) DEFAULT 'ACTIVE' NOT NULL COMMENT 'Driver status (ACTIVE, INACTIVE, SUSPENDED)',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation timestamp (UTC)',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record last update timestamp (UTC)',
    deleted_at TIMESTAMP NULL COMMENT 'Soft-delete timestamp - NULL means active record',

    PRIMARY KEY (id),
    UNIQUE KEY uk_driver_cpf (cpf),
    UNIQUE KEY uk_driver_cnh (cnh_number),
    KEY idx_driver_cpf (cpf),
    KEY idx_driver_cnh_status (cnh_status),
    KEY idx_driver_status (status),
    KEY idx_driver_deleted_at (deleted_at),
    KEY idx_driver_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
