package com.isoft.tms.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.isoft.tms.domain.EmployeeVacation} entity.
 */
@ApiModel(description = "EmployeeVacation (employee_vacation) entity.")
public class EmployeeVacationDTO implements Serializable {
    
    private Long id;

    @NotNull
    private LocalDate dateFrom;

    @NotNull
    private LocalDate dateTo;

    private Instant timeFrom;

    private Instant timeTo;

    private Integer status;

    private Integer type;


    private Long employeeId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public Instant getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Instant timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Instant getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Instant timeTo) {
        this.timeTo = timeTo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeVacationDTO employeeVacationDTO = (EmployeeVacationDTO) o;
        if (employeeVacationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeVacationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeVacationDTO{" +
            "id=" + getId() +
            ", dateFrom='" + getDateFrom() + "'" +
            ", dateTo='" + getDateTo() + "'" +
            ", timeFrom='" + getTimeFrom() + "'" +
            ", timeTo='" + getTimeTo() + "'" +
            ", status=" + getStatus() +
            ", type=" + getType() +
            ", employeeId=" + getEmployeeId() +
            "}";
    }
}
