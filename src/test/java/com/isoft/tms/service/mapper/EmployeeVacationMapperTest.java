package com.isoft.tms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeVacationMapperTest {

    private EmployeeVacationMapper employeeVacationMapper;

    @BeforeEach
    public void setUp() {
        employeeVacationMapper = new EmployeeVacationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(employeeVacationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(employeeVacationMapper.fromId(null)).isNull();
    }
}
