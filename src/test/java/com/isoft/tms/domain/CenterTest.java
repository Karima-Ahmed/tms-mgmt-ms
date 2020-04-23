package com.isoft.tms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.tms.web.rest.TestUtil;

public class CenterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Center.class);
        Center center1 = new Center();
        center1.setId(1L);
        Center center2 = new Center();
        center2.setId(center1.getId());
        assertThat(center1).isEqualTo(center2);
        center2.setId(2L);
        assertThat(center1).isNotEqualTo(center2);
        center1.setId(null);
        assertThat(center1).isNotEqualTo(center2);
    }
}
