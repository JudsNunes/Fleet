package com.evolutech.fleet.service.impl;

import com.evolutech.fleet.entity.Vehicle;
import com.evolutech.fleet.service.vehicleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class vehicleServiceImpl implements vehicleService {

        @Override
        public Vehicle saveByEntity(Vehicle vehicle) {
            return null;
        }

        @Override
        public Vehicle findByIdAndReturnEntity(Long id) {
            return null;
        }

        @Override
        public List<Vehicle> findAllAndReturnListOfEntity(Vehicle vehicle) {
            return null;
        }

        @Override
        public Vehicle updateByEntity(Vehicle vehicle) {
            return null;
        }

        @Override
        public Vehicle deleteByEntity(Vehicle vehicle) {
            return null;
        }

        @Override
        public List<Vehicle> findByIdAndPlate(Long id, String plate) {
            return null;
        }

}
