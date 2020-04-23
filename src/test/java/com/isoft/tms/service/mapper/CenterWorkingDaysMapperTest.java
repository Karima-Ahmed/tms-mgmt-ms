package com.isoft.tms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CenterWorkingDaysMapperTest {

    private CenterWorkingDaysMapper centerWorkingDaysMapper;

    @BeforeEach
    public void setUp() {
        centerWorkingDaysMapper = new CenterWorkingDaysMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(centerWorkingDaysMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(centerWorkingDaysMapper.fromId(null)).isNull();
    }
}
