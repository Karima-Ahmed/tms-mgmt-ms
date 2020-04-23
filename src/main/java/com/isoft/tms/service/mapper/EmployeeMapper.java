package com.isoft.tms.service.mapper;


import com.isoft.tms.domain.*;
import com.isoft.tms.service.dto.EmployeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring", uses = {DepartmentMapper.class, CenterMapper.class, EmployeeTypeMapper.class})
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "center.id", target = "centerId")
    @Mapping(source = "employeeType.id", target = "employeeTypeId")
    EmployeeDTO toDto(Employee employee);

    @Mapping(target = "employeeVacations", ignore = true)
    @Mapping(target = "removeEmployeeVacations", ignore = true)
    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "centerId", target = "center")
    @Mapping(source = "employeeTypeId", target = "employeeType")
    Employee toEntity(EmployeeDTO employeeDTO);

    default Employee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}
