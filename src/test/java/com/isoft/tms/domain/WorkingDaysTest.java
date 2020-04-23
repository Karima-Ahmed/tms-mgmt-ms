package com.isoft.tms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.tms.web.rest.TestUtil;

public class WorkingDaysTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkingDays.class);
        WorkingDays workingDays1 = new WorkingDays();
        workingDays1.setId(1L);
        WorkingDays workingDays2 = new WorkingDays();
        workingDays2.setId(workingDays1.getId());
        assertThat(workingDays1).isEqualTo(workingDays2);
        workingDays2.setId(2L);
        assertThat(workingDays1).isNotEqualTo(workingDays2);
        workingDays1.setId(null);
        assertThat(workingDays1).isNotEqualTo(workingDays2);
    }
}
