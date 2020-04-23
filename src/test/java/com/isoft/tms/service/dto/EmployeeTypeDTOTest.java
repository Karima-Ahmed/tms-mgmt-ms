package com.isoft.tms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.tms.web.rest.TestUtil;

public class EmployeeTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeTypeDTO.class);
        EmployeeTypeDTO employeeTypeDTO1 = new EmployeeTypeDTO();
        employeeTypeDTO1.setId(1L);
        EmployeeTypeDTO employeeTypeDTO2 = new EmployeeTypeDTO();
        assertThat(employeeTypeDTO1).isNotEqualTo(employeeTypeDTO2);
        employeeTypeDTO2.setId(employeeTypeDTO1.getId());
        assertThat(employeeTypeDTO1).isEqualTo(employeeTypeDTO2);
        employeeTypeDTO2.setId(2L);
        assertThat(employeeTypeDTO1).isNotEqualTo(employeeTypeDTO2);
        employeeTypeDTO1.setId(null);
        assertThat(employeeTypeDTO1).isNotEqualTo(employeeTypeDTO2);
    }
}
