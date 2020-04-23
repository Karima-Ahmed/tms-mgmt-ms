package com.isoft.tms.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.isoft.tms.domain.EmployeeType} entity.
 */
@ApiModel(description = "EmployeeType (employee_Type) entity.")
public class EmployeeTypeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nameEn;

    @NotNull
    private String nameAr;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeTypeDTO employeeTypeDTO = (EmployeeTypeDTO) o;
        if (employeeTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeTypeDTO{" +
            "id=" + getId() +
            ", nameEn='" + getNameEn() + "'" +
            ", nameAr='" + getNameAr() + "'" +
            "}";
    }
}
