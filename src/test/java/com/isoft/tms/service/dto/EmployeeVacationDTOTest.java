package com.isoft.tms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.tms.web.rest.TestUtil;

public class EmployeeVacationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeVacationDTO.class);
        EmployeeVacationDTO employeeVacationDTO1 = new EmployeeVacationDTO();
        employeeVacationDTO1.setId(1L);
        EmployeeVacationDTO employeeVacationDTO2 = new EmployeeVacationDTO();
        assertThat(employeeVacationDTO1).isNotEqualTo(employeeVacationDTO2);
        employeeVacationDTO2.setId(employeeVacationDTO1.getId());
        assertThat(employeeVacationDTO1).isEqualTo(employeeVacationDTO2);
        employeeVacationDTO2.setId(2L);
        assertThat(employeeVacationDTO1).isNotEqualTo(employeeVacationDTO2);
        employeeVacationDTO1.setId(null);
        assertThat(employeeVacationDTO1).isNotEqualTo(employeeVacationDTO2);
    }
}
