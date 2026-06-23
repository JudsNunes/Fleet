package com.evolutech.core.fleet.repository;

import com.evolutech.core.fleet.model.entity.MaintenanceEntity;
import com.evolutech.core.fleet.model.utils.enums.MaintenanceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MaintenanceRepository extends JpaRepository<MaintenanceEntity, String> {

    @Query("SELECT m FROM MaintenanceEntity m WHERE m.deletedAt IS NULL AND m.vehicle.id = :vehicleId ORDER BY m.maintenanceDate DESC")
    Page<MaintenanceEntity> findByVehicleIdAndNotDeleted(@Param("vehicleId") String vehicleId, Pageable pageable);

    @Query("SELECT m FROM MaintenanceEntity m WHERE m.deletedAt IS NULL AND m.maintenanceStatus = :status ORDER BY m.maintenanceDate DESC")
    Page<MaintenanceEntity> findByStatusAndNotDeleted(@Param("status") MaintenanceStatus status, Pageable pageable);

    @Query("SELECT m FROM MaintenanceEntity m WHERE m.deletedAt IS NULL AND m.vehicle.id = :vehicleId AND m.maintenanceStatus = :status ORDER BY m.maintenanceDate DESC")
    Page<MaintenanceEntity> findByVehicleIdAndStatusAndNotDeleted(@Param("vehicleId") String vehicleId, @Param("status") MaintenanceStatus status, Pageable pageable);

    @Query("SELECT m FROM MaintenanceEntity m WHERE m.deletedAt IS NULL AND m.vehicle.id = :vehicleId AND m.maintenanceDate BETWEEN :startDate AND :endDate ORDER BY m.maintenanceDate DESC")
    Page<MaintenanceEntity> findByVehicleIdAndDateRangeAndNotDeleted(@Param("vehicleId") String vehicleId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);

    @Query("SELECT m FROM MaintenanceEntity m WHERE m.deletedAt IS NULL ORDER BY m.maintenanceDate DESC")
    Page<MaintenanceEntity> findAllActive(Pageable pageable);

    @Query("SELECT COUNT(m) FROM MaintenanceEntity m WHERE m.deletedAt IS NULL AND m.vehicle.id = :vehicleId")
    long countByVehicleIdAndNotDeleted(@Param("vehicleId") String vehicleId);
}
