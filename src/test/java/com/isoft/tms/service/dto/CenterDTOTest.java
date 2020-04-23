package com.isoft.tms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.tms.web.rest.TestUtil;

public class CenterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CenterDTO.class);
        CenterDTO centerDTO1 = new CenterDTO();
        centerDTO1.setId(1L);
        CenterDTO centerDTO2 = new CenterDTO();
        assertThat(centerDTO1).isNotEqualTo(centerDTO2);
        centerDTO2.setId(centerDTO1.getId());
        assertThat(centerDTO1).isEqualTo(centerDTO2);
        centerDTO2.setId(2L);
        assertThat(centerDTO1).isNotEqualTo(centerDTO2);
        centerDTO1.setId(null);
        assertThat(centerDTO1).isNotEqualTo(centerDTO2);
    }
}
