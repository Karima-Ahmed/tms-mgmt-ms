package com.isoft.tms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.tms.web.rest.TestUtil;

public class CenterWorkingDaysDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CenterWorkingDaysDTO.class);
        CenterWorkingDaysDTO centerWorkingDaysDTO1 = new CenterWorkingDaysDTO();
        centerWorkingDaysDTO1.setId(1L);
        CenterWorkingDaysDTO centerWorkingDaysDTO2 = new CenterWorkingDaysDTO();
        assertThat(centerWorkingDaysDTO1).isNotEqualTo(centerWorkingDaysDTO2);
        centerWorkingDaysDTO2.setId(centerWorkingDaysDTO1.getId());
        assertThat(centerWorkingDaysDTO1).isEqualTo(centerWorkingDaysDTO2);
        centerWorkingDaysDTO2.setId(2L);
        assertThat(centerWorkingDaysDTO1).isNotEqualTo(centerWorkingDaysDTO2);
        centerWorkingDaysDTO1.setId(null);
        assertThat(centerWorkingDaysDTO1).isNotEqualTo(centerWorkingDaysDTO2);
    }
}
