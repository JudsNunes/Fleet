package com.evolutech.core.fleet.repository;

import com.evolutech.core.fleet.model.entity.VehicleEntity;
import com.evolutech.core.fleet.model.utils.enums.VehicleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    Optional<VehicleEntity> findByPlate(String plate);

    @Query("SELECT v FROM VehicleEntity v WHERE v.deletedAt IS NULL AND v.plate = :plate")
    Optional<VehicleEntity> findByPlateAndNotDeleted(@Param("plate") String plate);

    @Query("SELECT v FROM VehicleEntity v WHERE v.deletedAt IS NULL ORDER BY v.createdAt DESC")
    Page<VehicleEntity> findAllActive(Pageable pageable);

    @Query("SELECT v FROM VehicleEntity v WHERE v.deletedAt IS NULL AND v.status = :status ORDER BY v.createdAt DESC")
    Page<VehicleEntity> findByStatusAndNotDeleted(@Param("status") VehicleStatus status, Pageable pageable);

    @Query("SELECT v FROM VehicleEntity v WHERE v.deletedAt IS NULL")
    long countActive();

}
