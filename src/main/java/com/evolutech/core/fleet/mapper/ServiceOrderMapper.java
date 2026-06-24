package com.evolutech.core.fleet.mapper;

import com.evolutech.core.fleet.model.dto.request.ServiceOrderRequestDTO;
import com.evolutech.core.fleet.model.dto.response.ServiceOrderResponseDTO;
import com.evolutech.core.fleet.model.entity.ServiceOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceOrderMapper {

    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "approvedBy", ignore = true)
    @Mapping(target = "approvedAt", ignore = true)
    ServiceOrderEntity toEntity(ServiceOrderRequestDTO request);

    @Mapping(target = "vehicleId", expression = "java(serviceOrderEntity.getVehicle() != null ? serviceOrderEntity.getVehicle().getId() : null)")
    @Mapping(target = "status", expression = "java(serviceOrderEntity.getStatus() != null ? serviceOrderEntity.getStatus().name() : null)")
    ServiceOrderResponseDTO toResponseDTO(ServiceOrderEntity serviceOrderEntity);

    List<ServiceOrderResponseDTO> toResponseDTOList(List<ServiceOrderEntity> serviceOrderEntities);
}
