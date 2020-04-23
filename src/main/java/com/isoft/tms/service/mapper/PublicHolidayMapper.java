package com.isoft.tms.service.mapper;


import com.isoft.tms.domain.*;
import com.isoft.tms.service.dto.PublicHolidayDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PublicHoliday} and its DTO {@link PublicHolidayDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PublicHolidayMapper extends EntityMapper<PublicHolidayDTO, PublicHoliday> {



    default PublicHoliday fromId(Long id) {
        if (id == null) {
            return null;
        }
        PublicHoliday publicHoliday = new PublicHoliday();
        publicHoliday.setId(id);
        return publicHoliday;
    }
}
