package com.isoft.tms.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.isoft.tms.domain.WorkingDays} entity.
 */
@ApiModel(description = "WorkingDays (working_days) entity.")
public class WorkingDaysDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nameEn;

    @NotNull
    private String nameAr;

    private Boolean status;


    private Long centerWorkingDaysId;
    
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

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getCenterWorkingDaysId() {
        return centerWorkingDaysId;
    }

    public void setCenterWorkingDaysId(Long centerWorkingDaysId) {
        this.centerWorkingDaysId = centerWorkingDaysId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkingDaysDTO workingDaysDTO = (WorkingDaysDTO) o;
        if (workingDaysDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workingDaysDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkingDaysDTO{" +
            "id=" + getId() +
            ", nameEn='" + getNameEn() + "'" +
            ", nameAr='" + getNameAr() + "'" +
            ", status='" + isStatus() + "'" +
            ", centerWorkingDaysId=" + getCenterWorkingDaysId() +
            "}";
    }
}
