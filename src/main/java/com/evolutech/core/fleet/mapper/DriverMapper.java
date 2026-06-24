package com.evolutech.core.fleet.mapper;

import com.evolutech.core.fleet.model.dto.request.DriverRequestDTO;
import com.evolutech.core.fleet.model.dto.response.DriverResponseDTO;
import com.evolutech.core.fleet.model.entity.DriverEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DriverMapper {

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "cnhStatus", ignore = true)
    @Mapping(target = "cnhCategory", expression = "java(com.evolutech.core.fleet.model.utils.enums.DriverLicenseCategory.valueOf(request.getCnhCategory()))")
    DriverEntity toEntity(DriverRequestDTO request);

    @Mapping(target = "cnhCategory", expression = "java(entity.getCnhCategory() != null ? entity.getCnhCategory().name() : null)")
    @Mapping(target = "cnhStatus", expression = "java(entity.getCnhStatus() != null ? entity.getCnhStatus().name() : null)")
    @Mapping(target = "status", expression = "java(entity.getStatus() != null ? entity.getStatus().name() : null)")
    DriverResponseDTO toResponseDTO(DriverEntity entity);

    List<DriverResponseDTO> toResponseDTOList(List<DriverEntity> entities);
}
