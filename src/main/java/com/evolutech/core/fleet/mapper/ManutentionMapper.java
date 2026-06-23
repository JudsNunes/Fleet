package com.evolutech.core.fleet.mapper;

import com.evolutech.fleet.api.model.MaintenanceRequestDTO;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;

import com.evolutech.core.fleet.model.dto.request.ManutentionRequestDTO;
import com.evolutech.core.fleet.model.dto.response.ManutentionResponseDTO;
import com.evolutech.core.fleet.model.entity.ManutentionEntity;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ManutentionMapper {

    ManutentionResponseDTO toResponseDTO(ManutentionEntity manutention);

    ManutentionEntity toEntity(@Valid MaintenanceRequestDTO manutentionRequestDTO);

    List<ManutentionResponseDTO> toResponseDTOList(List<ManutentionEntity> manutentionEntities);


    ManutentionEntity updateEntity(ManutentionRequestDTO request, ManutentionEntity entity);
}

