package com.isoft.tms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CenterMapperTest {

    private CenterMapper centerMapper;

    @BeforeEach
    public void setUp() {
        centerMapper = new CenterMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(centerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(centerMapper.fromId(null)).isNull();
    }
}
