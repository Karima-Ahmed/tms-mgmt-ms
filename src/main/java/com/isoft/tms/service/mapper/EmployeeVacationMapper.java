package com.isoft.tms.service.mapper;


import com.isoft.tms.domain.*;
import com.isoft.tms.service.dto.EmployeeVacationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeVacation} and its DTO {@link EmployeeVacationDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface EmployeeVacationMapper extends EntityMapper<EmployeeVacationDTO, EmployeeVacation> {

    @Mapping(source = "employee.id", target = "employeeId")
    EmployeeVacationDTO toDto(EmployeeVacation employeeVacation);

    @Mapping(source = "employeeId", target = "employee")
    EmployeeVacation toEntity(EmployeeVacationDTO employeeVacationDTO);

    default EmployeeVacation fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmployeeVacation employeeVacation = new EmployeeVacation();
        employeeVacation.setId(id);
        return employeeVacation;
    }
}
