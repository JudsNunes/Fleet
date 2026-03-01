package com.evolutech.fleet.repository;

import com.evolutech.fleet.model.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByIdAndPlate(Long id, String plate);

}
