package com.evolutech.core.fleet.repository;

import com.evolutech.core.fleet.model.entity.MaintenanceAlertEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceAlertRepository extends JpaRepository<MaintenanceAlertEntity, String> {

    @Query("SELECT a FROM MaintenanceAlertEntity a WHERE a.isActive = true ORDER BY a.createdAt DESC")
    List<MaintenanceAlertEntity> findAllActive();

    @Query("SELECT a FROM MaintenanceAlertEntity a WHERE a.isActive = true AND a.vehicle.id = :vehicleId ORDER BY a.createdAt DESC")
    List<MaintenanceAlertEntity> findActiveByVehicleId(@Param("vehicleId") String vehicleId);

    @Query("SELECT a FROM MaintenanceAlertEntity a WHERE a.isActive = true ORDER BY a.createdAt DESC")
    Page<MaintenanceAlertEntity> findAllActivePaged(Pageable pageable);
}
