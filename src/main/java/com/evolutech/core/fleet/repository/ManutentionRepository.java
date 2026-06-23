package com.evolutech.core.fleet.repository;

import com.evolutech.core.fleet.model.entity.ManutentionEntity;
import com.evolutech.core.fleet.model.utils.enums.ManutentionDoneStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ManutentionRepository extends JpaRepository<ManutentionEntity, Long> {

    @Query("SELECT m FROM ManutentionEntity m WHERE m.deletedAt IS NULL AND m.vehicle.id = :vehicleId ORDER BY m.manutentionDate DESC")
    Page<ManutentionEntity> findByVehicleIdAndNotDeleted(@Param("vehicleId") Long vehicleId, Pageable pageable);

    @Query("SELECT m FROM ManutentionEntity m WHERE m.deletedAt IS NULL AND m.done = :done ORDER BY m.manutentionDate DESC")
    Page<ManutentionEntity> findByDoneAndNotDeleted(@Param("done") ManutentionDoneStatus done, Pageable pageable);

    @Query("SELECT m FROM ManutentionEntity m WHERE m.deletedAt IS NULL AND m.vehicle.id = :vehicleId AND m.done = :done ORDER BY m.manutentionDate DESC")
    Page<ManutentionEntity> findByVehicleIdAndDoneAndNotDeleted(@Param("vehicleId") Long vehicleId, @Param("done") ManutentionDoneStatus done, Pageable pageable);

    @Query("SELECT m FROM ManutentionEntity m WHERE m.deletedAt IS NULL AND m.vehicle.id = :vehicleId AND m.manutentionDate BETWEEN :startDate AND :endDate ORDER BY m.manutentionDate DESC")
    Page<ManutentionEntity> findByVehicleIdAndDateRangeAndNotDeleted(@Param("vehicleId") Long vehicleId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);

    @Query("SELECT m FROM ManutentionEntity m WHERE m.deletedAt IS NULL ORDER BY m.manutentionDate DESC")
    Page<ManutentionEntity> findAllActive(Pageable pageable);

    @Query("SELECT COUNT(m) FROM ManutentionEntity m WHERE m.deletedAt IS NULL AND m.vehicle.id = :vehicleId")
    long countByVehicleIdAndNotDeleted(@Param("vehicleId") Long vehicleId);

}
