package com.isoft.tms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.tms.web.rest.TestUtil;

public class EmployeeVacationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeVacation.class);
        EmployeeVacation employeeVacation1 = new EmployeeVacation();
        employeeVacation1.setId(1L);
        EmployeeVacation employeeVacation2 = new EmployeeVacation();
        employeeVacation2.setId(employeeVacation1.getId());
        assertThat(employeeVacation1).isEqualTo(employeeVacation2);
        employeeVacation2.setId(2L);
        assertThat(employeeVacation1).isNotEqualTo(employeeVacation2);
        employeeVacation1.setId(null);
        assertThat(employeeVacation1).isNotEqualTo(employeeVacation2);
    }
}
