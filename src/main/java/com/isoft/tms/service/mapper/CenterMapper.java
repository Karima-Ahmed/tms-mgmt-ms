package com.isoft.tms.service.mapper;


import com.isoft.tms.domain.*;
import com.isoft.tms.service.dto.CenterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Center} and its DTO {@link CenterDTO}.
 */
@Mapper(componentModel = "spring", uses = {CenterWorkingDaysMapper.class})
public interface CenterMapper extends EntityMapper<CenterDTO, Center> {

    @Mapping(source = "centerWorkingDays.id", target = "centerWorkingDaysId")
    CenterDTO toDto(Center center);

    @Mapping(source = "centerWorkingDaysId", target = "centerWorkingDays")
    Center toEntity(CenterDTO centerDTO);

    default Center fromId(Long id) {
        if (id == null) {
            return null;
        }
        Center center = new Center();
        center.setId(id);
        return center;
    }
}
