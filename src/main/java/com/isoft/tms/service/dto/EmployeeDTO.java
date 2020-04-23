package com.isoft.tms.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.isoft.tms.domain.enumeration.Language;

/**
 * A DTO for the {@link com.isoft.tms.domain.Employee} entity.
 */
@ApiModel(description = "Employee (employee) entity.")
public class EmployeeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String fullNameEn;

    @NotNull
    private String fullNameAr;

    private Long userId;

    @NotNull
    private String email;

    @NotNull
    private String mobileNo;

    private Integer status;

    private Language langKey;


    private Long departmentId;

    private Long centerId;

    private Long employeeTypeId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullNameEn() {
        return fullNameEn;
    }

    public void setFullNameEn(String fullNameEn) {
        this.fullNameEn = fullNameEn;
    }

    public String getFullNameAr() {
        return fullNameAr;
    }

    public void setFullNameAr(String fullNameAr) {
        this.fullNameAr = fullNameAr;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Language getLangKey() {
        return langKey;
    }

    public void setLangKey(Language langKey) {
        this.langKey = langKey;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getCenterId() {
        return centerId;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }

    public Long getEmployeeTypeId() {
        return employeeTypeId;
    }

    public void setEmployeeTypeId(Long employeeTypeId) {
        this.employeeTypeId = employeeTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeDTO employeeDTO = (EmployeeDTO) o;
        if (employeeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
            "id=" + getId() +
            ", fullNameEn='" + getFullNameEn() + "'" +
            ", fullNameAr='" + getFullNameAr() + "'" +
            ", userId=" + getUserId() +
            ", email='" + getEmail() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", status=" + getStatus() +
            ", langKey='" + getLangKey() + "'" +
            ", departmentId=" + getDepartmentId() +
            ", centerId=" + getCenterId() +
            ", employeeTypeId=" + getEmployeeTypeId() +
            "}";
    }
}
