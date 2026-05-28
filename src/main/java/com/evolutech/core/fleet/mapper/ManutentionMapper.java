package com.evolutech.core.fleet.mapper;

import com.evolutech.core.fleet.api.model.ManutentionRequestDTO;
import com.evolutech.core.fleet.api.model.ManutentionResponseDTO;
import com.evolutech.core.fleet.model.entity.Manutention;
import com.evolutech.core.fleet.model.entity.Vehicle;
import com.evolutech.core.fleet.repository.VehicleRepository;
import org.springframework.stereotype.Component;

@Component
public class ManutentionMapper {

    private final VehicleRepository vehicleRepository;

    public ManutentionMapper(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Manutention toEntity(ManutentionRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));

        return Manutention.builder()
                .id(dto.getId())
                .manutentionDate(dto.getManutentionDate())
                .description(dto.getDescription())
                .type(dto.getType())
                .cost(dto.getCost())
                .mileage(dto.getMileage())
                .nextMileage(dto.getNextMileage())
                .done(dto.isDone())
                .vehicle(vehicle)
                .build();
    }

    public ManutentionResponseDTO toDto(Manutention entity) {
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
                .vehicleId(entity.getVehicle().getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public Manutention updateEntity(ManutentionRequestDTO dto, Manutention entity) {
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
            Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                    .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
            entity.setVehicle(vehicle);
        }

        return entity;
    }
}

