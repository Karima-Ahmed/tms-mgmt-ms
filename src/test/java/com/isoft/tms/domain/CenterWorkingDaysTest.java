package com.isoft.tms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.tms.web.rest.TestUtil;

public class CenterWorkingDaysTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CenterWorkingDays.class);
        CenterWorkingDays centerWorkingDays1 = new CenterWorkingDays();
        centerWorkingDays1.setId(1L);
        CenterWorkingDays centerWorkingDays2 = new CenterWorkingDays();
        centerWorkingDays2.setId(centerWorkingDays1.getId());
        assertThat(centerWorkingDays1).isEqualTo(centerWorkingDays2);
        centerWorkingDays2.setId(2L);
        assertThat(centerWorkingDays1).isNotEqualTo(centerWorkingDays2);
        centerWorkingDays1.setId(null);
        assertThat(centerWorkingDays1).isNotEqualTo(centerWorkingDays2);
    }
}
