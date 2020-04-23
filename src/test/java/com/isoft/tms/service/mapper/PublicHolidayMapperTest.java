package com.isoft.tms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PublicHolidayMapperTest {

    private PublicHolidayMapper publicHolidayMapper;

    @BeforeEach
    public void setUp() {
        publicHolidayMapper = new PublicHolidayMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(publicHolidayMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(publicHolidayMapper.fromId(null)).isNull();
    }
}
