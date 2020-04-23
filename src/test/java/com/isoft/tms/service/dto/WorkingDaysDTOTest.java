package com.isoft.tms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.tms.web.rest.TestUtil;

public class WorkingDaysDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkingDaysDTO.class);
        WorkingDaysDTO workingDaysDTO1 = new WorkingDaysDTO();
        workingDaysDTO1.setId(1L);
        WorkingDaysDTO workingDaysDTO2 = new WorkingDaysDTO();
        assertThat(workingDaysDTO1).isNotEqualTo(workingDaysDTO2);
        workingDaysDTO2.setId(workingDaysDTO1.getId());
        assertThat(workingDaysDTO1).isEqualTo(workingDaysDTO2);
        workingDaysDTO2.setId(2L);
        assertThat(workingDaysDTO1).isNotEqualTo(workingDaysDTO2);
        workingDaysDTO1.setId(null);
        assertThat(workingDaysDTO1).isNotEqualTo(workingDaysDTO2);
    }
}
