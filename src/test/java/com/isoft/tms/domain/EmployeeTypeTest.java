package com.isoft.tms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.tms.web.rest.TestUtil;

public class EmployeeTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeType.class);
        EmployeeType employeeType1 = new EmployeeType();
        employeeType1.setId(1L);
        EmployeeType employeeType2 = new EmployeeType();
        employeeType2.setId(employeeType1.getId());
        assertThat(employeeType1).isEqualTo(employeeType2);
        employeeType2.setId(2L);
        assertThat(employeeType1).isNotEqualTo(employeeType2);
        employeeType1.setId(null);
        assertThat(employeeType1).isNotEqualTo(employeeType2);
    }
}
