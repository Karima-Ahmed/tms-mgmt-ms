package com.isoft.tms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeTypeMapperTest {

    private EmployeeTypeMapper employeeTypeMapper;

    @BeforeEach
    public void setUp() {
        employeeTypeMapper = new EmployeeTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(employeeTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(employeeTypeMapper.fromId(null)).isNull();
    }
}
