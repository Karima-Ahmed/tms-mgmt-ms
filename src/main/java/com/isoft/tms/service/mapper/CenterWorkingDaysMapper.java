package com.isoft.tms.service.mapper;


import com.isoft.tms.domain.*;
import com.isoft.tms.service.dto.CenterWorkingDaysDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CenterWorkingDays} and its DTO {@link CenterWorkingDaysDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CenterWorkingDaysMapper extends EntityMapper<CenterWorkingDaysDTO, CenterWorkingDays> {


    @Mapping(target = "workingDays", ignore = true)
    @Mapping(target = "removeWorkingDays", ignore = true)
    CenterWorkingDays toEntity(CenterWorkingDaysDTO centerWorkingDaysDTO);

    default CenterWorkingDays fromId(Long id) {
        if (id == null) {
            return null;
        }
        CenterWorkingDays centerWorkingDays = new CenterWorkingDays();
        centerWorkingDays.setId(id);
        return centerWorkingDays;
    }
}
