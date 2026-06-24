package com.evolutech.core.fleet.repository;

import com.evolutech.core.fleet.model.entity.TripEntity;
import com.evolutech.core.fleet.model.utils.enums.TripStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<TripEntity, String> {

    @Query("SELECT t FROM TripEntity t WHERE t.deletedAt IS NULL ORDER BY t.departureDate DESC")
    Page<TripEntity> findAllActive(Pageable pageable);

    @Query("SELECT t FROM TripEntity t WHERE t.deletedAt IS NULL AND t.vehicle.id = :vehicleId ORDER BY t.departureDate DESC")
    Page<TripEntity> findByVehicleIdAndNotDeleted(@Param("vehicleId") String vehicleId, Pageable pageable);

    @Query("SELECT t FROM TripEntity t WHERE t.deletedAt IS NULL AND t.driver.id = :driverId ORDER BY t.departureDate DESC")
    Page<TripEntity> findByDriverIdAndNotDeleted(@Param("driverId") String driverId, Pageable pageable);

    @Query("SELECT t FROM TripEntity t WHERE t.deletedAt IS NULL AND t.status = :status ORDER BY t.departureDate DESC")
    Page<TripEntity> findByStatusAndNotDeleted(@Param("status") TripStatus status, Pageable pageable);

    @Query("SELECT t FROM TripEntity t WHERE t.deletedAt IS NULL AND t.status = 'IN_PROGRESS' ORDER BY t.departureDate DESC")
    List<TripEntity> findActiveTrips();

    @Query("SELECT t FROM TripEntity t WHERE t.deletedAt IS NULL AND t.vehicle.id = :vehicleId AND t.status = 'IN_PROGRESS'")
    Optional<TripEntity> findActiveTripByVehicle(@Param("vehicleId") String vehicleId);

    @Query("SELECT t FROM TripEntity t WHERE t.deletedAt IS NULL " +
           "AND (:vehicleId IS NULL OR t.vehicle.id = :vehicleId) " +
           "AND (:driverId IS NULL OR t.driver.id = :driverId) " +
           "AND (:status IS NULL OR t.status = :status) " +
           "ORDER BY t.departureDate DESC")
    Page<TripEntity> findByFilters(
            @Param("vehicleId") String vehicleId,
            @Param("driverId") String driverId,
            @Param("status") TripStatus status,
            Pageable pageable);
}
