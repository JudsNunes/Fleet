package com.evolutech.core.fleet.mapper;

import org.mapstruct.Mapper;

import com.evolutech.core.fleet.model.dto.request.ManutentionRequestDTO;
import com.evolutech.core.fleet.model.dto.response.ManutentionResponseDTO;
import com.evolutech.core.fleet.model.entity.ManutentionEntity;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ManutentionMapper {

    ManutentionResponseDTO toResponseDTO(ManutentionEntity manutention);

    ManutentionEntity toEntity(ManutentionRequestDTO manutentionRequestDTO);

    List<ManutentionResponseDTO> toResponseDTOList(List<ManutentionEntity> manutentionEntities);

    
}

