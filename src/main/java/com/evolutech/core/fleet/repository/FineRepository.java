package com.evolutech.core.fleet.repository;

import com.evolutech.core.fleet.model.entity.FineEntity;
import com.evolutech.core.fleet.model.utils.enums.FineStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FineRepository extends JpaRepository<FineEntity, String> {

    @Query("SELECT f FROM FineEntity f WHERE f.driverCpf = :driverCpf ORDER BY f.infractionDate DESC")
    Page<FineEntity> findByDriverCpf(@Param("driverCpf") String driverCpf, Pageable pageable);

    @Query("SELECT f FROM FineEntity f WHERE f.vehicle.id = :vehicleId ORDER BY f.infractionDate DESC")
    Page<FineEntity> findByVehicleId(@Param("vehicleId") String vehicleId, Pageable pageable);

    @Query("SELECT f FROM FineEntity f WHERE f.status = :status ORDER BY f.infractionDate DESC")
    Page<FineEntity> findByStatus(@Param("status") FineStatus status, Pageable pageable);

    @Query("SELECT f FROM FineEntity f ORDER BY f.infractionDate DESC")
    Page<FineEntity> findAllOrderByInfractionDateDesc(Pageable pageable);
}
