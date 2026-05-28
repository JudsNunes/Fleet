package com.evolutech.core.fleet.mapper;

import com.evolutech.core.fleet.model.dto.request.ManutentionRequestDTO;
import com.evolutech.core.fleet.model.dto.response.ManutentionResponseDTO;
import com.evolutech.core.fleet.model.entity.ManutentionEntity;
import com.evolutech.core.fleet.model.entity.VehicleEntity;
import com.evolutech.core.fleet.repository.VehicleRepository;
import org.springframework.stereotype.Component;

@Component
public class ManutentionMapper  {

    private final VehicleRepository vehicleRepository;

    public ManutentionMapper(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public ManutentionEntity toEntity(ManutentionRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        VehicleEntity vehicleEntity = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));

        return ManutentionEntity.builder()
                .id(dto.getId())
                .manutentionDate(dto.getManutentionDate())
                .description(dto.getDescription())
                .type(dto.getType())
                .cost(dto.getCost())
                .mileage(dto.getMileage())
                .nextMileage(dto.getNextMileage())
                .done(dto.isDone())
                .vehicle(vehicleEntity)
                .build();
    }

    public ManutentionResponseDTO toDto(ManutentionEntity entity) {
        if (entity == null) {
            return null;
        }

        return ManutentionResponseDTO.builder()
                .id(entity.getId())
                .manutentionDate(entity.getManutentionDate())
                .description(entity.getDescription())
                .type(entity.getType())
                .cost(entity.getCost())
                .mileage(entity.getMileage())
                .nextMileage(entity.getNextMileage())
                .done(entity.isDone())
                .vehicleId(entity.getVehicleEntity().getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public ManutentionEntity updateEntity(ManutentionRequestDTO dto, ManutentionEntity entity) {
        if (dto == null || entity == null) {
            return entity;
        }

        entity.setManutentionDate(dto.getManutentionDate());
        entity.setDescription(dto.getDescription());
        entity.setType(dto.getType());
        entity.setCost(dto.getCost());
        entity.setMileage(dto.getMileage());
        entity.setNextMileage(dto.getNextMileage());
        entity.setDone(dto.isDone());

        if (dto.getVehicleId() != null) {
            VehicleEntity vehicleEntity = vehicleRepository.findById(dto.getVehicleId())
                    .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
            entity.setVehicleEntity(vehicleEntity);
        }

        return entity;
    }
}

