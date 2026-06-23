# Fleet Management Database Schema

## Document Info
- **Version**: 1.0  
- **Date**: 2026-06-20  
- **Environment**: Production Ready  
- **Database**: MySQL 8.0+  
- **Charset**: utf8mb4  
- **Engine**: InnoDB

---

## Table of Contents
1. [Overview](#overview)
2. [Entity Relationship Diagram](#entity-relationship-diagram)
3. [Table Specifications](#table-specifications)
4. [Relationships & Constraints](#relationships--constraints)
5. [Indexes & Performance](#indexes--performance)
6. [Soft Delete Strategy](#soft-delete-strategy)
7. [Audit Trail](#audit-trail)
8. [Enumerations](#enumerations)
9. [Database Views](#database-views)
10. [Migration History](#migration-history)

---

## Overview

The Fleet Management system uses a **normalized relational schema** with two core entities:

- **Vehicle** (`vehicle` table): Master data for fleet vehicles
- **Maintenance** (`manutention` table): Maintenance records linked to vehicles

The schema follows **production best practices** including:
- ✅ Auto-increment `BIGINT` primary keys for horizontal scalability
- ✅ Soft-delete implementation with `deleted_at` timestamp
- ✅ Comprehensive audit trail (`created_at`, `updated_at`, `deleted_at`)
- ✅ Enum fields stored as `VARCHAR` with application-level validation
- ✅ Foreign key constraints with `ON DELETE RESTRICT` to prevent orphaned records
- ✅ Strategic indexing for common query patterns
- ✅ Views for active record queries (excluding soft-deleted)

---

## Entity Relationship Diagram

```
┌─────────────────────────────────────┐
│          VEHICLE (1)                │
├─────────────────────────────────────┤
│ PK: id (BIGINT, AUTO_INCREMENT)     │
│    plate (VARCHAR(10), UNIQUE)      │
│    brand (VARCHAR(100))             │
│    model (VARCHAR(100))             │
│    year (INT)                       │
│    color (VARCHAR(50))              │
│    mileage (DOUBLE)                 │
│    status (VARCHAR(50), ENUM)       │
│    created_at (TIMESTAMP)           │
│    updated_at (TIMESTAMP)           │
│    deleted_at (TIMESTAMP, NULL)     │
└─────────────────────────────────────┘
          │
          │ ONE-TO-MANY
          │
          │ FK: vehicle_id
          │ ON DELETE: RESTRICT
          │
          └──────────────────────────────────┐
                                            │
┌──────────────────────────────────────────┘
│
│  ┌─────────────────────────────────────┐
│  │     MANUTENTION (*)                 │
│  ├─────────────────────────────────────┤
│  │ PK: id (BIGINT, AUTO_INCREMENT)     │
│  │ FK: vehicle_id (BIGINT)             │
│  │     manutention_date (DATE)         │
│  │     description (TEXT)              │
│  │     type (VARCHAR(50), ENUM)        │
│  │     cost (DOUBLE)                   │
│  │     mileage (DOUBLE)                │
│  │     next_mileage (DOUBLE)           │
│  │     done (VARCHAR(50), ENUM)        │
│  │     created_at (TIMESTAMP)          │
│  │     updated_at (TIMESTAMP)          │
│  │     deleted_at (TIMESTAMP, NULL)    │
│  └─────────────────────────────────────┘
```

**Relationship Type**: `1:N` (One-to-Many)
- One Vehicle can have many Maintenance records
- Each Maintenance record belongs to exactly one Vehicle
- Relationship cardinality: `(1) : (0..N)`

---

## Table Specifications

### Table: `vehicle`

**Purpose**: Store vehicle master data with audit and soft-delete capability

**DDL**:
```sql
CREATE TABLE vehicle (
    id BIGINT NOT NULL AUTO_INCREMENT,
    plate VARCHAR(10) NOT NULL UNIQUE,
    model VARCHAR(100) NOT NULL,
    brand VARCHAR(100) NOT NULL,
    year INT NOT NULL,
    color VARCHAR(50),
    mileage DOUBLE DEFAULT 0.0,
    status VARCHAR(50) DEFAULT 'ACTIVE' NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_plate (plate),
    KEY idx_status (status),
    KEY idx_deleted_at (deleted_at),
    KEY idx_created_at (created_at),
    CONSTRAINT chk_year CHECK (year >= 1900 AND year <= YEAR(CURDATE()) + 1),
    CONSTRAINT chk_mileage CHECK (mileage >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Column Descriptions**:

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | BIGINT | PK, AUTO_INCREMENT | Primary key; auto-incremented long integer |
| `plate` | VARCHAR(10) | UNIQUE, NOT NULL | Vehicle registration plate (Mercosul/old BR format) |
| `brand` | VARCHAR(100) | NOT NULL | Manufacturer name (e.g., Toyota, Honda, Volkswagen) |
| `model` | VARCHAR(100) | NOT NULL | Vehicle model (e.g., Corolla, Civic) |
| `year` | INT | NOT NULL, CHECK | Production year; range [1900, current+1] |
| `color` | VARCHAR(50) | NULL | Vehicle color (optional) |
| `mileage` | DOUBLE | DEFAULT 0.0, CHECK | Current odometer reading in km; must be ≥ 0 |
| `status` | VARCHAR(50) | ENUM, DEFAULT 'ACTIVE' | Vehicle status: ACTIVE, INACTIVE, MAINTENANCE, DECOMMISSIONED |
| `created_at` | TIMESTAMP | NOT NULL, DEFAULT NOW | Record creation timestamp (UTC); immutable |
| `updated_at` | TIMESTAMP | NOT NULL, DEFAULT NOW, ON UPDATE | Last modification timestamp; auto-updated |
| `deleted_at` | TIMESTAMP | NULL | Soft-delete marker; NULL = active; set on deletion |

**Constraints**:
- `chk_year`: Ensures year is within valid range
- `chk_mileage`: Ensures non-negative mileage
- `uk_plate`: Unique plate ensures no duplicate registrations

---

### Table: `manutention`

**Purpose**: Store maintenance records with soft-delete and audit trail

**DDL**:
```sql
CREATE TABLE manutention (
    id BIGINT NOT NULL AUTO_INCREMENT,
    vehicle_id BIGINT NOT NULL,
    manutention_date DATE NOT NULL,
    description TEXT NOT NULL,
    type VARCHAR(50) DEFAULT 'MANUTENTION' NOT NULL,
    cost DOUBLE NOT NULL,
    mileage DOUBLE NOT NULL,
    next_mileage DOUBLE NOT NULL,
    done VARCHAR(50) DEFAULT 'PENDING' NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
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
        ON UPDATE CASCADE,
    CONSTRAINT chk_cost CHECK (cost > 0),
    CONSTRAINT chk_mileage_positive CHECK (mileage >= 0),
    CONSTRAINT chk_next_mileage CHECK (next_mileage > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Column Descriptions**:

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | BIGINT | PK, AUTO_INCREMENT | Primary key; auto-incremented long integer |
| `vehicle_id` | BIGINT | NOT NULL, FK | Foreign key → vehicle(id); identifies parent vehicle |
| `manutention_date` | DATE | NOT NULL | Date when maintenance was performed |
| `description` | TEXT | NOT NULL | Detailed description of maintenance work |
| `type` | VARCHAR(50) | ENUM, DEFAULT 'MANUTENTION' | Maintenance type: MANUTENTION, FUEL, TOLL, PARKING, OTHER |
| `cost` | DOUBLE | NOT NULL, CHECK | Maintenance cost in currency units; must be > 0 |
| `mileage` | DOUBLE | NOT NULL, CHECK | Vehicle odometer reading at maintenance; must be ≥ 0 |
| `next_mileage` | DOUBLE | NOT NULL, CHECK | Recommended next maintenance mileage; must be > 0 |
| `done` | VARCHAR(50) | ENUM, DEFAULT 'PENDING' | Status: PENDING, IN_PROGRESS, COMPLETED |
| `created_at` | TIMESTAMP | NOT NULL, DEFAULT NOW | Record creation timestamp (UTC); immutable |
| `updated_at` | TIMESTAMP | NOT NULL, DEFAULT NOW, ON UPDATE | Last modification timestamp; auto-updated |
| `deleted_at` | TIMESTAMP | NULL | Soft-delete marker; NULL = active; set on deletion |

**Constraints**:
- `fk_vehicle_id`: References vehicle(id) with `ON DELETE RESTRICT` (prevent orphans) and `ON UPDATE CASCADE`
- `chk_cost`: Ensures cost is positive
- `chk_mileage_positive`: Ensures non-negative mileage reading
- `chk_next_mileage`: Ensures next_mileage is positive

---

## Relationships & Constraints

### Foreign Key Relationship

**Vehicle → Maintenance** (`1:N`)

```sql
CONSTRAINT fk_vehicle_id FOREIGN KEY (vehicle_id) 
    REFERENCES vehicle(id) 
    ON DELETE RESTRICT 
    ON UPDATE CASCADE
```

**Cardinality**:
- A Vehicle can have `0...n` Maintenance records
- A Maintenance record must belong to exactly `1` Vehicle

**Referential Integrity**:
- ✅ **CREATE**: Maintenance can be inserted only if referenced vehicle exists
- ✅ **UPDATE**: Changing vehicle.id automatically cascades to maintenance.vehicle_id
- ✅ **DELETE**: Deleting a vehicle is **RESTRICTED** if maintenance records exist (prevents orphans)

**Cascade Rules**:
- `ON DELETE RESTRICT`: Cannot delete a vehicle if maintenance records reference it → must delete maintenance first (or soft-delete both)
- `ON UPDATE CASCADE`: If vehicle.id is modified, maintenance.vehicle_id automatically updates
- Note: Soft-delete is handled at application layer; hard deletes are restricted by database constraint

---

## Indexes & Performance

### Vehicle Table Indexes

| Index Name | Columns | Type | Purpose |
|------------|---------|------|---------|
| `PRIMARY` | `id` | UNIQUE | Primary key lookup (default) |
| `uk_plate` | `plate` | UNIQUE | Find vehicle by registration plate |
| `idx_status` | `status` | Normal | Filter by vehicle status (ACTIVE/INACTIVE/etc.) |
| `idx_deleted_at` | `deleted_at` | Normal | Identify soft-deleted records; supports `WHERE deleted_at IS NULL` queries |
| `idx_created_at` | `created_at` | Normal | Sort/find by creation date |
| `idx_vehicle_status_not_deleted` | `status, deleted_at` | Composite | Combined filter for active vehicles by status (if available) |

### Maintenance Table Indexes

| Index Name | Columns | Type | Purpose |
|------------|---------|------|---------|
| `PRIMARY` | `id` | UNIQUE | Primary key lookup (default) |
| `idx_vehicle_id` | `vehicle_id` | Normal | Find all maintenance by vehicle |
| `idx_manutention_date` | `manutention_date` | Normal | Find/sort by maintenance date |
| `idx_done_status` | `done` | Normal | Filter by completion status (PENDING/COMPLETED/etc.) |
| `idx_vehicle_id_done` | `vehicle_id, done` | Composite | Find maintenance for a vehicle with specific status |
| `idx_created_at` | `created_at` | Normal | Sort/find by creation date |
| `idx_deleted_at` | `deleted_at` | Normal | Support soft-delete filtering |
| `idx_vehicle_id_manutention_date` | `vehicle_id, manutention_date DESC` | Composite (optional) | Most common query: all maintenance for a vehicle sorted by date |
| `idx_maintenance_pending` | `done, deleted_at` | Composite (optional) | Find pending maintenance records |

**Index Selectivity**: Indexes are most efficient when:
- Join conditions use `vehicle_id`
- WHERE clauses filter by `done` status
- Combined filters on `(vehicle_id, done)` benefit from composite index

---

## Soft Delete Strategy

### Implementation

**Soft-delete markers**:
- **`deleted_at`**: `TIMESTAMP NULL`
- **`NULL` value**: Record is active
- **`TIMESTAMP` value**: Record was deleted at specified UTC time

### Query Patterns

**Active record queries** (include only non-deleted):
```sql
SELECT * FROM vehicle WHERE deleted_at IS NULL;
SELECT * FROM maintenance WHERE deleted_at IS NULL AND vehicle_id = 42;
```

**All record queries** (including deleted, for audit):
```sql
SELECT * FROM vehicle;  -- WARNING: includes soft-deleted
SELECT * FROM vehicle WHERE deleted_at IS NOT NULL;  -- deleted only
```

### Views for Convenience

Three views are provided to simplify active record queries:

```sql
CREATE VIEW v_vehicle AS
SELECT * FROM vehicle WHERE deleted_at IS NULL;

CREATE VIEW v_manutention AS
SELECT * FROM maintenance WHERE deleted_at IS NULL;

CREATE VIEW v_vehicle_manutention AS
SELECT v.*, m.* FROM v_vehicle v
LEFT JOIN v_manutention m ON v.id = m.vehicle_id
ORDER BY v.id, m.manutention_date DESC;
```

### Application Layer Support

- **Delete operation**: Set `deleted_at = NOW()` (not hard delete)
- **Read operations**: Repository queries include `WHERE deleted_at IS NULL` by default
- **Undelete**: Set `deleted_at = NULL` if needed (optional feature)
- **Hard delete**: Admins only via stored procedure (if required)

---

## Audit Trail

Each table maintains comprehensive audit information:

### Timestamps

| Field | Type | Set | Updates | Purpose |
|-------|------|-----|---------|---------|
| `created_at` | TIMESTAMP | INSERT (auto) | NEVER | Record creation time (UTC); immutable |
| `updated_at` | TIMESTAMP | INSERT (auto) | Every UPDATE | Last modification time (UTC); auto-managed by `ON UPDATE CURRENT_TIMESTAMP` |
| `deleted_at` | TIMESTAMP NULL | Never (default NULL) | On soft-delete | Soft-delete time (UTC); used to mark deletions |

### Audit Log Table (Optional)

For comprehensive change history, an optional `audit_log` table records who changed what:

```sql
CREATE TABLE audit_log (
    id BIGINT NOT NULL AUTO_INCREMENT,
    table_name VARCHAR(100) NOT NULL,
    operation VARCHAR(10) NOT NULL,  -- INSERT, UPDATE, DELETE
    record_id BIGINT NOT NULL,
    changed_by VARCHAR(255) DEFAULT 'system',
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    old_values JSON,
    new_values JSON,
    PRIMARY KEY (id),
    KEY idx_table_record (table_name, record_id),
    KEY idx_changed_at (changed_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

---

## Enumerations

### VehicleStatus (Stored as STRING in `vehicle.status`)

```enum
ACTIVE              -- Vehicle is operational
INACTIVE            -- Vehicle is temporarily out of service (maintenance, inspection)
MAINTENANCE         -- Vehicle is currently undergoing maintenance
DECOMMISSIONED      -- Vehicle is permanently removed from fleet
```

### ManutentionDoneStatus (Stored as STRING in `manutention.done`)

```enum
PENDING             -- Maintenance is scheduled but not started
IN_PROGRESS         -- Maintenance is currently being performed
COMPLETED           -- Maintenance work is finished
```

### TypeCost (Stored as STRING in `manutention.type`)

```enum
MANUTENTION         -- Regular maintenance (oil change, filters, etc.)
FUEL                -- Fuel cost
TOLL                -- Toll/highway fees
PARKING             -- Parking fees
OTHER               -- Miscellaneous costs
```

### Enum Storage Strategy

- **Storage Format**: `VARCHAR(50)` with `ENUM` type stored as **string name** (not ordinal)
- **Advantages**: 
  - ✅ Readable in SQL queries
  - ✅ Robust to enum reordering
  - ✅ Easy migration if enum values change
- **Application Mapping**: 
  - JPA uses `@Enumerated(EnumType.STRING)`
  - JSON payloads represent enums as strings: `"done": "COMPLETED"`
  - Java side: enums map 1:1 with database strings

---

## Database Views

Three helper views simplify queries to exclude soft-deleted records:

### 1. `v_vehicle` — Active Vehicles Only

```sql
CREATE OR REPLACE VIEW v_vehicle AS
SELECT * FROM vehicle 
WHERE deleted_at IS NULL;
```

**Usage**: Find all active vehicles
```sql
SELECT * FROM v_vehicle WHERE status = 'ACTIVE';
```

### 2. `v_manutention` — Active Maintenance Records Only

```sql
CREATE OR REPLACE VIEW v_manutention AS
SELECT * FROM manutention 
WHERE deleted_at IS NULL;
```

**Usage**: Find pending maintenance
```sql
SELECT * FROM v_manutention WHERE done = 'PENDING';
```

### 3. `v_vehicle_manutention` — Combined View

```sql
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
```

**Usage**: Per-vehicle maintenance summary
```sql
SELECT * FROM v_vehicle_manutention WHERE vehicle_id = 1;
```

---

## Stored Procedures

### 1. `sp_soft_delete_vehicle(p_vehicle_id)`

Safely soft-delete a vehicle and optionally its maintenance records.

```sql
CALL sp_soft_delete_vehicle(5);  -- Soft-delete vehicle id=5
```

**Behavior**:
- Sets `deleted_at = NOW()` for the vehicle
- Returns error if vehicle not found or already deleted
- Does NOT delete related maintenance records (application handles cascade softly)

### 2. `sp_soft_delete_manutention(p_manutention_id)`

Soft-delete a single maintenance record.

```sql
CALL sp_soft_delete_manutention(12);  -- Soft-delete maintenance id=12
```

---

## Migration History

| Version | Date | Description |
|---------|------|-------------|
| `V1__init_fleet_schema.sql` | 2026-06-20 | Initial schema: vehicle and manutention tables, indexes, views, procedures |
| (Future) | TBD | Vehicle documents / VIN tracking |
| (Future) | TBD | Driver assignments to vehicles |
| (Future) | TBD | Trip/route records |
| (Future) | TBD | Expense tracking (fuel, tolls) |

---

## Data Integrity Rules

### Invariants

1. **Primary Key Uniqueness**: `vehicle.id` and `manutention.id` are globally unique
2. **Plate Uniqueness**: `vehicle.plate` is globally unique (never duplicated)
3. **Referential Integrity**: Every `manutention.vehicle_id` must reference a valid `vehicle.id`
4. **Temporal Ordering**: `created_at ≤ updated_at` always true (enforced by schema)
5. **Soft-Delete Marker**: `deleted_at` is either NULL (active) or a valid TIMESTAMP (deleted)
6. **Positive Amounts**: `manutention.cost > 0` and `manutention.next_mileage > 0`
7. **Non-Negative Mileage**: `vehicle.mileage ≥ 0` and `manutention.mileage ≥ 0`
8. **Valid Year**: `vehicle.year` is between 1900 and current year + 1

### Cascading Constraints

| Action | Vehicle | Maintenance |
|--------|---------|-------------|
| **Create** | OK (if valid) | OK (if referenced vehicle exists) |
| **Update** | Changes cascade to FK refs | OK (if vehicle_id valid) |
| **Delete (Hard)** | RESTRICT if maintenance exists | OK (cascades deletion allowed) |
| **Delete (Soft)** | OK (sets deleted_at) | OK (sets deleted_at independently) |
| **Restore** | Set deleted_at = NULL | Set deleted_at = NULL |

---

## Performance Guidelines

### Query Optimization Tips

1. **Always filter by `deleted_at IS NULL`** in WHERE clauses
2. **Use views** (`v_vehicle`, `v_manutention`) for cleaner active-record queries
3. **Leverage composite indexes** for common filter combinations:
   - `(vehicle_id, done)`
   - `(vehicle_id, manutention_date DESC)`
4. **Pagination**: Use LIMIT + OFFSET for large result sets
5. **Avoid N+1 queries**: Use JOIN instead of nested loops at application layer

### Estimated Row Counts (Benchmarks)

| Table | Typical Rows | Growth | Action |
|-------|--------------|--------|--------|
| `vehicle` | 100–10K | Monthly | Monitor indexes; add partitioning if > 1M |
| `manutention` | 1K–100K | Daily | Add partitioning by manutention_date if > 10M rows |

---

## Backup & Recovery

### Recommended Backup Strategy

- **Full backup**: Daily (or per org policy)
- **Incremental backup**: Every 6 hours
- **Transaction log**: Continuous (WAL for point-in-time recovery)
- **RPO (Recovery Point Objective)**: 1 hour
- **RTO (Recovery Time Objective)**: 4 hours

### Soft-Delete Recovery

If a record is accidentally soft-deleted:

```sql
UPDATE vehicle SET deleted_at = NULL WHERE id = 5;
UPDATE manutention SET deleted_at = NULL WHERE id = 42;
```

---

## Related Files

- [Entity Classes](../../src/main/java/com/evolutech/core/fleet/model/entity/)
- [Repositories](../../src/main/java/com/evolutech/core/fleet/repository/)
- [Service Layer](../../src/main/java/com/evolutech/core/fleet/service/)
- [Flyway Migration](../../src/main/resources/db/migration/V1__init_fleet_schema.sql)
- [Architecture Document](../../ARQUITETURA_FLEET.md)

---

## Support & Maintenance

- **DBA Contact**: [Database Administrator]
- **Architecture**: [Software Architect]
- **Last Updated**: 2026-06-20
- **Review Schedule**: Quarterly

