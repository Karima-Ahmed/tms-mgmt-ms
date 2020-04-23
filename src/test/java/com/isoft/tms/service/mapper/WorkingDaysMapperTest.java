package com.isoft.tms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WorkingDaysMapperTest {

    private WorkingDaysMapper workingDaysMapper;

    @BeforeEach
    public void setUp() {
        workingDaysMapper = new WorkingDaysMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(workingDaysMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(workingDaysMapper.fromId(null)).isNull();
    }
}
