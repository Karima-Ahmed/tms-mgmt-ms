package com.isoft.tms.service.mapper;


import com.isoft.tms.domain.*;
import com.isoft.tms.service.dto.WorkingDaysDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkingDays} and its DTO {@link WorkingDaysDTO}.
 */
@Mapper(componentModel = "spring", uses = {CenterWorkingDaysMapper.class})
public interface WorkingDaysMapper extends EntityMapper<WorkingDaysDTO, WorkingDays> {

    @Mapping(source = "centerWorkingDays.id", target = "centerWorkingDaysId")
    WorkingDaysDTO toDto(WorkingDays workingDays);

    @Mapping(source = "centerWorkingDaysId", target = "centerWorkingDays")
    WorkingDays toEntity(WorkingDaysDTO workingDaysDTO);

    default WorkingDays fromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkingDays workingDays = new WorkingDays();
        workingDays.setId(id);
        return workingDays;
    }
}
