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
public interface VehicleRepository extends JpaRepository<VehicleEntity, String> {

    Optional<VehicleEntity> findByPlate(String plate);

    @Query("SELECT v FROM VehicleEntity v WHERE v.deletedAt IS NULL AND v.plate = :plate")
    Optional<VehicleEntity> findByPlateAndNotDeleted(@Param("plate") String plate);

    @Query("SELECT v FROM VehicleEntity v WHERE v.deletedAt IS NULL AND v.chassis = :chassis")
    Optional<VehicleEntity> findByChassisAndNotDeleted(@Param("chassis") String chassis);

    @Query("SELECT v FROM VehicleEntity v WHERE v.deletedAt IS NULL AND v.renavam = :renavam")
    Optional<VehicleEntity> findByRenavamAndNotDeleted(@Param("renavam") String renavam);

    @Query("SELECT v FROM VehicleEntity v WHERE v.deletedAt IS NULL ORDER BY v.plate ASC")
    Page<VehicleEntity> findAllActive(Pageable pageable);

    @Query("SELECT v FROM VehicleEntity v WHERE v.deletedAt IS NULL AND v.status = :status ORDER BY v.plate ASC")
    Page<VehicleEntity> findByStatusAndNotDeleted(@Param("status") VehicleStatus status, Pageable pageable);

    @Query("SELECT v FROM VehicleEntity v WHERE v.deletedAt IS NULL " +
           "AND (:plate IS NULL OR v.plate LIKE CONCAT('%', :plate, '%')) " +
           "AND (:brand IS NULL OR v.brand LIKE CONCAT('%', :brand, '%')) " +
           "AND (:status IS NULL OR v.status = :status) " +
           "ORDER BY v.plate ASC")
    Page<VehicleEntity> findByFilters(
            @Param("plate") String plate,
            @Param("brand") String brand,
            @Param("status") VehicleStatus status,
            Pageable pageable);

    @Query("SELECT v FROM VehicleEntity v WHERE v.deletedAt IS NULL")
    long countActive();
}
