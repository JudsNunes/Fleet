package com.evolutech.core.fleet.repository;

import com.evolutech.core.fleet.model.entity.VehicleAssignmentEntity;
import com.evolutech.core.fleet.model.utils.enums.AssignmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleAssignmentRepository extends JpaRepository<VehicleAssignmentEntity, String> {

    @Query("SELECT va FROM VehicleAssignmentEntity va WHERE va.deletedAt IS NULL ORDER BY va.startDate DESC")
    Page<VehicleAssignmentEntity> findAllActive(Pageable pageable);

    @Query("SELECT va FROM VehicleAssignmentEntity va WHERE va.deletedAt IS NULL AND va.vehicle.id = :vehicleId ORDER BY va.startDate DESC")
    Page<VehicleAssignmentEntity> findByVehicleIdAndNotDeleted(@Param("vehicleId") String vehicleId, Pageable pageable);

    @Query("SELECT va FROM VehicleAssignmentEntity va WHERE va.deletedAt IS NULL AND va.driver.id = :driverId ORDER BY va.startDate DESC")
    Page<VehicleAssignmentEntity> findByDriverIdAndNotDeleted(@Param("driverId") String driverId, Pageable pageable);

    @Query("SELECT va FROM VehicleAssignmentEntity va WHERE va.deletedAt IS NULL AND va.status = :status ORDER BY va.startDate DESC")
    Page<VehicleAssignmentEntity> findByStatusAndNotDeleted(@Param("status") AssignmentStatus status, Pageable pageable);

    @Query("SELECT va FROM VehicleAssignmentEntity va WHERE va.deletedAt IS NULL AND va.status = 'ACTIVE' ORDER BY va.startDate DESC")
    List<VehicleAssignmentEntity> findActiveAssignments();

    @Query("SELECT va FROM VehicleAssignmentEntity va WHERE va.deletedAt IS NULL AND va.vehicle.id = :vehicleId AND va.status = 'ACTIVE'")
    Optional<VehicleAssignmentEntity> findActiveByVehicle(@Param("vehicleId") String vehicleId);

    @Query("SELECT va FROM VehicleAssignmentEntity va WHERE va.deletedAt IS NULL AND va.driver.id = :driverId AND va.status = 'ACTIVE'")
    Optional<VehicleAssignmentEntity> findActiveByDriver(@Param("driverId") String driverId);

    @Query("SELECT va FROM VehicleAssignmentEntity va WHERE va.deletedAt IS NULL AND va.vehicle.id = :vehicleId AND va.status = 'ACTIVE' AND va.startDate <= :endDate AND (va.endDate IS NULL OR va.endDate >= :startDate)")
    List<VehicleAssignmentEntity> findOverlappingByVehicle(@Param("vehicleId") String vehicleId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT va FROM VehicleAssignmentEntity va WHERE va.deletedAt IS NULL AND va.driver.id = :driverId AND va.status = 'ACTIVE' AND va.startDate <= :endDate AND (va.endDate IS NULL OR va.endDate >= :startDate)")
    List<VehicleAssignmentEntity> findOverlappingByDriver(@Param("driverId") String driverId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
