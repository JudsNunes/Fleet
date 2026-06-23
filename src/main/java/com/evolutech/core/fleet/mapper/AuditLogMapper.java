package com.evolutech.core.fleet.mapper;

import com.evolutech.core.fleet.model.dto.response.AuditLogResponseDTO;
import com.evolutech.core.fleet.model.entity.AuditLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {

    @Mapping(target = "action", expression = "java(entity.getAction() != null ? entity.getAction().name() : null)")
    AuditLogResponseDTO toResponseDTO(AuditLogEntity entity);

    List<AuditLogResponseDTO> toResponseDTOList(List<AuditLogEntity> entities);
}
